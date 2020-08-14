package como.sekolah.submission4.ui.favorite.tvshow;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import como.sekolah.submission4.R;
import como.sekolah.submission4.adapter.FavoriteTVShowAdapter;
import como.sekolah.submission4.db.DatabaseTVShowContract;
import como.sekolah.submission4.entity.FavoriteTVShow;
import como.sekolah.submission4.helper.TVShowMappingHelper;

public class FavoriteTvShowFragment extends Fragment implements LoadTvCallback{
    private ProgressBar progressbar;
    private FavoriteTVShowAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressbar = view.findViewById(R.id.progressbar);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new FavoriteTVShowAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this.getActivity());
        this.getActivity().getContentResolver().registerContentObserver(DatabaseTVShowContract.TvShowColumns.CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadTvAsync(this.getActivity(), this).execute();
        } else {
            ArrayList<FavoriteTVShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTv(list);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadTvAsync(this.getActivity(), this).execute();
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressbar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<FavoriteTVShow> favoriteTVShow) {
        progressbar.setVisibility(View.INVISIBLE);
        if (favoriteTVShow.size() > 0) {
            adapter.setListTv(favoriteTVShow);
        } else {
            adapter.setListTv(new ArrayList<FavoriteTVShow>());
        }
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<FavoriteTVShow>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTvCallback> weakCallback;

        LoadTvAsync(Context context, LoadTvCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<FavoriteTVShow> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseTVShowContract.TvShowColumns.CONTENT_URI, null, null, null, null);
            return TVShowMappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteTVShow> favoriteTVShow) {
            super.onPostExecute(favoriteTVShow);
            weakCallback.get().postExecute(favoriteTVShow);
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}

interface LoadTvCallback{
    void preExecute();
    void postExecute(ArrayList<FavoriteTVShow> favoriteTVShow);
}
