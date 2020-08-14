package como.sekolah.submission4.ui.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import como.sekolah.submission4.R;
import como.sekolah.submission4.adapter.MovieAdapter;
import como.sekolah.submission4.entity.Movies;

public class MovieFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private MovieViewModel moviesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        movieAdapter = new MovieAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(movieAdapter);

        moviesViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        moviesViewModel.getMovie().observe(this, getMovie);
        moviesViewModel.setMovie("EXTRA_MOVIE");

        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);

        return view;
    }

    private Observer<ArrayList<Movies>> getMovie = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movies) {
            if (movies != null){
                movieAdapter.setDataMovie(movies);
            }
            showLoading(false);
        }
    };

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}