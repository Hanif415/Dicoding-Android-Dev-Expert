package como.sekolah.submission4.ui.searchview.movie;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import como.sekolah.submission4.R;
import como.sekolah.submission4.adapter.MovieAdapter;
import como.sekolah.submission4.entity.Movies;

public class MovieSearchView extends AppCompatActivity {

    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    public static String EXTRA_MOVIE = "EXTRA_MOVIE";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_view);

        movieAdapter = new MovieAdapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

        String movieTitle = getIntent().getStringExtra(EXTRA_MOVIE);

        SearchMovieModel searchMovieModel = ViewModelProviders.of(this).get(SearchMovieModel.class);
        searchMovieModel.getMovie().observe(this, getMovie);
        searchMovieModel.setMovie("EXTRA_MOVIE", movieTitle);

        progressBar = findViewById(R.id.progressbar);
        showLoading(true);

    }

    private Observer<ArrayList<Movies>> getMovie = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movies) {
            if (movies != null) {
                movieAdapter.setDataMovie(movies);
            } else {
                recyclerView.setVisibility(View.INVISIBLE);

            }
            showLoading(false);
        }
    };

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
