package como.sekolah.submission2.ui.movie;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import como.sekolah.submission2.DetailMovieActivity;
import como.sekolah.submission2.Movie;
import como.sekolah.submission2.MovieAdapter;
import como.sekolah.submission2.R;

public class MovieFragment extends Fragment {

    private ArrayList<Movie> arrayList = new ArrayList<>();

    private String[] dataSinopsis;
    private String[] dataJudul;
    private TypedArray dataPoster;
    private String[] dataRating;

    private MovieAdapter movieAdapter;

    private RecyclerView rvMovies;



    private MovieModel moviewViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        moviewViewModel = ViewModelProviders.of(this).get(MovieModel.class);
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onActivityCreated(savedInstanceState);

//        initialization the Adapter
        movieAdapter = new MovieAdapter(this.getActivity());
//        method
        addItem();

        rvMovies = view.findViewById(R.id.rv_movie);
        rvMovies.setHasFixedSize(true);

//        list.addAll(MoviesData.getListData());
        showRecyclerMovie();


    }



//    add to model
        private void addItem(){
            dataSinopsis =  getResources().getStringArray(R.array.data_sinopsis);
            dataJudul =  getResources().getStringArray(R.array.data_judul);
            dataPoster =  getResources().obtainTypedArray(R.array.data_poster);
            dataRating = getResources().getStringArray(R.array.data_rating);


            for (int i = 0; i < dataJudul.length; i++){
                Movie movie = new Movie();
                movie.setSinopsis(dataSinopsis[i]);
                movie.setJudul(dataJudul[i]);
                movie.setPoster(dataPoster.getResourceId(i, -1));
                movie.setRating(dataRating[i]);
                arrayList.add(movie);
            }
            movieAdapter.setListMovie(arrayList);
    }



//  show the recyclerview to fragment
    private void showRecyclerMovie(){
        rvMovies.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        MovieAdapter movieAdapter = new MovieAdapter(arrayList);
        rvMovies.setAdapter(movieAdapter);

//       click
        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {

                movie.setJudul(movie.getJudul());
                movie.setPoster(movie.getPoster());
                movie.setRating(movie.getRating());
                movie.setSinopsis(movie.getSinopsis());

                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
                startActivity(intent);

            }
        });
    }
}