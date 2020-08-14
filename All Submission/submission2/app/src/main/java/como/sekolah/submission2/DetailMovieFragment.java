package como.sekolah.submission2;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailMovieFragment extends Fragment {

    public static final String EXTRA_MOVIE = "extra_movie";
    TextView tvJudul;


    public DetailMovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvJudul = view.findViewById(R.id.tv_judul);

        Movie movies = getArguments().getParcelable(EXTRA_MOVIE);
        String judul = movies.getJudul();

        tvJudul.setText(judul);
    }
}
