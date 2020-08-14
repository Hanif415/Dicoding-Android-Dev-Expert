package como.sekolah.submission4.ui.favorite;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import como.sekolah.submission4.R;
import como.sekolah.submission4.adapter.FavTvAdapter;
import como.sekolah.submission4.db.TvShowHelper;
import como.sekolah.submission4.entity.TvFav;
import como.sekolah.submission4.helper.TvMappingHelper;

public class FavTvFragment extends Fragment implements LoadTvCallback{
    private ProgressBar progressBar;
    private FavTvAdapter adapter;
    private TvShowHelper tvShowHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_tv, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new FavTvAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        tvShowHelper = TvShowHelper.getInstance(getActivity().getApplicationContext());
        tvShowHelper.open();
        new LoadTvAsync(tvShowHelper, this).execute();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<TvFav> tvFav) {
        progressBar.setVisibility(View.INVISIBLE);
        if (tvFav.size() > 0) {
            adapter.setListTv(tvFav);
        } else {
            adapter.setListTv(new ArrayList<TvFav>());
        }
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<TvFav>> {

        private final WeakReference<TvShowHelper> weakTvShowHelper;
        private final WeakReference<LoadTvCallback> weakCallback;

        LoadTvAsync(TvShowHelper tvShowHelper, LoadTvCallback callback) {
            weakTvShowHelper = new WeakReference<>(tvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvFav> doInBackground(Void... voids) {
            Cursor dataCursor = weakTvShowHelper.get().queryAll();
            return TvMappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<TvFav> tvFav) {
            super.onPostExecute(tvFav);
            weakCallback.get().postExecute(tvFav);
        }
    }
}

interface LoadTvCallback{
    void preExecute();
    void postExecute(ArrayList<TvFav> tvFav);
}
