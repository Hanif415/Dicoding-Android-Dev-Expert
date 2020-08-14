package como.sekolah.submission4.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import como.sekolah.submission4.ui.tvshow.DetailTvActivity;
import como.sekolah.submission4.R;
import como.sekolah.submission4.entity.TvShows;

public class tvShowAdapter extends RecyclerView.Adapter<tvShowAdapter.TvShowViewHolder> {

    ArrayList<TvShows> mData = new ArrayList<>();
    public void setData(ArrayList<TvShows> dataTvShow) {
        mData.clear();
        mData.addAll(dataTvShow);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tvshow_item, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder tvShowViewHolder, int position) {
        tvShowViewHolder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTvshowJudul, tvTvshowDescription, tvTvDate, tvTvRate;
        ImageView imgPoster;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTvshowJudul = itemView.findViewById(R.id.tv_show_judul);
            tvTvshowDescription = itemView.findViewById(R.id.tv_show_description);
            imgPoster = itemView.findViewById(R.id.img_tv_poster);
            tvTvDate = itemView.findViewById(R.id.tv_date_release_tv);
            tvTvRate = itemView.findViewById(R.id.tv_rating_tv);

            itemView.setOnClickListener(this);
        }

        void bind(TvShows tvShows){
            String url_poster = "https://image.tmdb.org/t/p/w185/" + tvShows.getPoster();

            tvTvshowJudul.setText(tvShows.getTitle());
            tvTvshowDescription.setText(tvShows.getOverview());
            tvTvDate.setText(tvShows.getRelease_date());
            tvTvRate.setText(tvShows.getVote_average());
            Glide.with(itemView.getContext())
                    .load(url_poster)
                    .into(imgPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            TvShows tvShows = mData.get(position);

            tvShows.setTitle(tvShows.getTitle());
            tvShows.setOverview(tvShows.getOverview());
            tvShows.setRelease_date(tvShows.getRelease_date());
            tvShows.setVote_average(tvShows.getVote_average());

            Intent intent = new Intent(itemView.getContext(), DetailTvActivity.class);
            intent.putExtra(DetailTvActivity.EXTRA_TV, tvShows);
            itemView.getContext().startActivity(intent);

        }
    }
}
