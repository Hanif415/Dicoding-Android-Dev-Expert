package como.sekolah.submission4.ui.tvshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import como.sekolah.submission4.R;
import como.sekolah.submission4.adapter.TVShowAdapter;
import como.sekolah.submission4.entity.TVShows;

public class TvshowFragment extends Fragment {

    private TVShowAdapter adapter;
    private ProgressBar progressbar;
    private TvshowViewModel tvShowsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TVShowAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        tvShowsViewModel = ViewModelProviders.of(this).get(TvshowViewModel.class);
        tvShowsViewModel.getTvShow().observe(this, getTvshow);
        tvShowsViewModel.setTvShow("EXTRA_TVSHOW");

        progressbar = view.findViewById(R.id.progressbar);
        showLoading(true);
    }

    private Observer<ArrayList<TVShows>> getTvshow = new Observer<ArrayList<TVShows>>() {
        @Override
        public void onChanged(ArrayList<TVShows> tvShows) {
            if (tvShows != null){
                adapter.setData(tvShows);
            }

            showLoading(false);
        }
    };

    private void showLoading(Boolean state){
        if (state){
            progressbar.setVisibility(View.VISIBLE);
        } else {
            progressbar.setVisibility(View.GONE);
        }
    }
}