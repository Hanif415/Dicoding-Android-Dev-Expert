package como.sekolah.submission4.ui.favorite;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
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
import como.sekolah.submission4.adapter.FavMovieAdapter;
import como.sekolah.submission4.db.MovieHelper;
import como.sekolah.submission4.entity.MovieFav;
import como.sekolah.submission4.helper.MappingHelper;

public class FavMovieFragment extends Fragment implements LoadMoviesCallback{

    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private FavMovieAdapter adapter;
    private MovieHelper movieHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_movie, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        rvMovie = view.findViewById(R.id.rv_fav_movie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);
        adapter = new FavMovieAdapter(getActivity());
        rvMovie.setAdapter(adapter);

        movieHelper = MovieHelper.getInstance(getActivity().getApplicationContext());
        movieHelper.open();
        new LoadMoviesAsync(movieHelper, this).execute();

        return view;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
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
    public void postExecute(ArrayList<MovieFav> movieFav) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movieFav.size() > 0) {
            adapter.setListMovie(movieFav);
        } else {
            adapter.setListMovie(new ArrayList<MovieFav>());
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<MovieFav>> {

        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(MovieHelper movieHelper, LoadMoviesCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MovieFav> doInBackground(Void... voids) {
            Cursor dataCursor = weakMovieHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<MovieFav> movieFav) {
            super.onPostExecute(movieFav);

            weakCallback.get().postExecute(movieFav);
        }
    }
}

interface LoadMoviesCallback{
    void preExecute();
    void postExecute(ArrayList<MovieFav> movieFav);
}
