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

import como.sekolah.submission4.ui.tvshow.DetailTvShowActivity;
import como.sekolah.submission4.R;
import como.sekolah.submission4.entity.TVShows;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TvShowViewHolder> {

    ArrayList<TVShows> mData = new ArrayList<>();
    public void setData(ArrayList<TVShows> dataTvShow) {
        mData.clear();
        mData.addAll(dataTvShow);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tv_show_item, viewGroup, false);
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
        final TextView tvTitle, tvOverview, tvReleaseDate, tvVoteAverageRate;
        final ImageView imgPoster;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvReleaseDate = itemView.findViewById(R.id.tv_date_release);
            tvVoteAverageRate = itemView.findViewById(R.id.tv_vote_average);

            itemView.setOnClickListener(this);
        }

        void bind(TVShows tvShows){
            String url_poster = "https://image.tmdb.org/t/p/w185/" + tvShows.getPoster();

            tvTitle.setText(tvShows.getTitle());
            tvOverview.setText(tvShows.getOverview());
            tvReleaseDate.setText(tvShows.getRelease_date());
            tvVoteAverageRate.setText(tvShows.getVote_average());
            Glide.with(itemView.getContext())
                    .load(url_poster)
                    .into(imgPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            TVShows tvShows = mData.get(position);

            tvShows.setTitle(tvShows.getTitle());
            tvShows.setOverview(tvShows.getOverview());
            tvShows.setRelease_date(tvShows.getRelease_date());
            tvShows.setVote_average(tvShows.getVote_average());

            Intent intent = new Intent(itemView.getContext(), DetailTvShowActivity.class);
            intent.putExtra(DetailTvShowActivity.EXTRA_TV, tvShows);
            itemView.getContext().startActivity(intent);

        }
    }
}
