package como.sekolah.submission3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import como.sekolah.submission3.R;
import como.sekolah.submission3.adapter.TvShowAdapter;
import como.sekolah.submission3.model.TvShows;
import como.sekolah.submission3.viewModel.TvShowsViewModel;

public class TvShowsFragment extends Fragment {

    private TvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvShowsViewModel tvShowsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new TvShowAdapter();

        View view = inflater.inflate(R.layout.fragment_tvshows, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_tvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        tvShowsViewModel = ViewModelProviders.of(this).get(TvShowsViewModel.class);
        tvShowsViewModel.getvshow().observe(this, getTvshow);
        tvShowsViewModel.setTvshow("EXTRA_TVSHOW");

        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);

        return view;
    }

    private Observer<ArrayList<TvShows>> getTvshow = new Observer<ArrayList<TvShows>>() {
        @Override
        public void onChanged(ArrayList<TvShows> tvShows) {
            if (tvShows != null){
                adapter.setData(tvShows);
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