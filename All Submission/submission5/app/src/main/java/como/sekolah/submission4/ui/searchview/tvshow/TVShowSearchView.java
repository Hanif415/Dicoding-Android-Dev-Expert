package como.sekolah.submission4.ui.searchview.tvshow;

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
import como.sekolah.submission4.adapter.TVShowAdapter;
import como.sekolah.submission4.entity.TVShows;

public class TVShowSearchView extends AppCompatActivity {

    private TVShowAdapter tvShowAdapter;
    private ProgressBar progressBar;
    public static String EXTRA_TVSHOW = "EXTRA_TVSHOW";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_search_view);

        tvShowAdapter = new TVShowAdapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tvShowAdapter);

        String tvShowTitle = getIntent().getStringExtra(EXTRA_TVSHOW);

        SearchTVShowModel searchTVShowModel = ViewModelProviders.of(this).get(SearchTVShowModel.class);
        searchTVShowModel.getTVShow().observe(this, getTvshow);
        searchTVShowModel.setTVShow("EXTRA_TVSHOW", tvShowTitle);

        progressBar = findViewById(R.id.progressbar);
        showLoading(true);
    }

    private Observer<ArrayList<TVShows>> getTvshow = new Observer<ArrayList<TVShows>>() {
        @Override
        public void onChanged(ArrayList<TVShows> tvShows) {
            if (tvShows != null) {
                tvShowAdapter.setData(tvShows);
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
