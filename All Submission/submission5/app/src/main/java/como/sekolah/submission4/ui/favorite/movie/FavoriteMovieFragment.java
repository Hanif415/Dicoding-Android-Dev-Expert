package como.sekolah.submission4.ui.favorite.movie;

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
import como.sekolah.submission4.adapter.FavoriteMovieAdapter;
import como.sekolah.submission4.db.DatabaseMovieContract;
import como.sekolah.submission4.entity.FavoriteMovie;
import como.sekolah.submission4.helper.MovieMappingHelper;

public class FavoriteMovieFragment extends Fragment implements LoadMoviesCallback{

    private ProgressBar progressbar;
    private FavoriteMovieAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressbar = view.findViewById(R.id.progressbar);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new FavoriteMovieAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this.getActivity());
        this.getActivity().getContentResolver().registerContentObserver(DatabaseMovieContract.MovieColumns.CONTENT_URI, true, myObserver);


        if (savedInstanceState == null) {
            new LoadMoviesAsync(this.getActivity(), this).execute();
        } else {
            ArrayList<FavoriteMovie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovie(list);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMoviesAsync(this.getActivity(), this).execute();
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
    public void postExecute(ArrayList<FavoriteMovie> favoriteMovie) {
        progressbar.setVisibility(View.INVISIBLE);
        if (favoriteMovie.size() > 0) {
            adapter.setListMovie(favoriteMovie);
        } else {
            adapter.setListMovie(new ArrayList<FavoriteMovie>());
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<FavoriteMovie>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(Context context, LoadMoviesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<FavoriteMovie> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseMovieContract.MovieColumns.CONTENT_URI, null,null, null, null);
            return MovieMappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteMovie> favoriteMovie) {
            super.onPostExecute(favoriteMovie);
            weakCallback.get().postExecute(favoriteMovie);
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
//            new LoadMoviesAsync(context, (LoadMoviesCallback) context).execute();
        }
    }
}

interface LoadMoviesCallback{
    void preExecute();
    void postExecute(ArrayList<FavoriteMovie> favoriteMovie);
}
