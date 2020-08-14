package como.sekolah.submission2.ui.tvshow;

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

import como.sekolah.submission2.DetailTvshowctivity;
import como.sekolah.submission2.R;
import como.sekolah.submission2.TvShow;
import como.sekolah.submission2.TvShowAdapter;

public class TvshowFragment extends Fragment {

    private ArrayList<TvShow> arrayList = new ArrayList<>();
    private String[] dataSinopsis;
    private String[] dataJudul;
    private TypedArray dataPoster;
    private String[] dataRating;

    private TvShowAdapter tvShowAdapter;

    private RecyclerView rv_tvShow;



    private TvshowViewModel tvshowViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tvshowViewModel = ViewModelProviders.of(this).get(TvshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tvshow, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        initialization the Adapter
        tvShowAdapter = new TvShowAdapter(this.getActivity());

//        method
        addItem();

//        recyclerView
        rv_tvShow = view.findViewById(R.id.rv_tshow);
        rv_tvShow.setHasFixedSize(true);

//        list.addAll(TvShowData.getListData());
        showRecyclerTvShow();
    }




// add item to model
    private void addItem(){
        dataSinopsis =  getResources().getStringArray(R.array.data_sinopsis2);
        dataJudul =  getResources().getStringArray(R.array.data_judul2);
        dataPoster =  getResources().obtainTypedArray(R.array.data_poster2);
        dataRating = getResources().getStringArray(R.array.data_rating2);

        for (int i = 0;  i < dataJudul.length; i++ ){
            TvShow tvShow = new TvShow();
            tvShow.setJudul(dataJudul[i]);
            tvShow.setSinopsis(dataSinopsis[i]);
            tvShow.setPoster(dataPoster.getResourceId(i, -1));
            tvShow.setRating(dataRating[i]);

            arrayList.add(tvShow);
        }
        tvShowAdapter.setListTvShow(arrayList);
    }

//    untuk menampilkan recyclerview
    private void showRecyclerTvShow(){
        rv_tvShow.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        TvShowAdapter tvShowAdapter = new TvShowAdapter(arrayList);
        rv_tvShow.setAdapter(tvShowAdapter);

        tvShowAdapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow tvshow) {
                tvshow.setJudul(tvshow.getJudul());
                tvshow.setPoster(tvshow.getPoster());
                tvshow.setRating(tvshow.getRating());
                tvshow.setSinopsis(tvshow.getSinopsis());

                Intent intent = new Intent(getActivity(), DetailTvshowctivity.class);
                intent.putExtra(DetailTvshowctivity.EXTRA_TVSHOW, tvshow);
                startActivity(intent);

            }
        });
    }
}