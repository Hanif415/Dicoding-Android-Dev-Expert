package como.sekolah.submission4.ui.tvshow;

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
import como.sekolah.submission4.adapter.tvShowAdapter;
import como.sekolah.submission4.entity.TvShows;

public class TvshowFragment extends Fragment {

    private tvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvshowViewModel tvShowsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);

        adapter = new tvShowAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.rv_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        tvShowsViewModel = ViewModelProviders.of(this).get(TvshowViewModel.class);
        tvShowsViewModel.getTvShow().observe(this, getTvshow);
        tvShowsViewModel.setTvShow("EXTRA_TVSHOW");

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