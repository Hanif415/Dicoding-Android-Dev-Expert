package como.sekolah.submission3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
//Observer
import androidx.lifecycle.Observer;
//berguna untuk mendeklarasikan sebuah ViewModel ke dalam Actiity atau Fragment
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import como.sekolah.submission3.adapter.MovieAdapter;
import como.sekolah.submission3.R;
import como.sekolah.submission3.model.Movies;
import como.sekolah.submission3.viewModel.MoviesViewModel;

public class MoviesFragment extends Fragment {

    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private MoviesViewModel moviesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new MovieAdapter();
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);


        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
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
                adapter.setData(movies);
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