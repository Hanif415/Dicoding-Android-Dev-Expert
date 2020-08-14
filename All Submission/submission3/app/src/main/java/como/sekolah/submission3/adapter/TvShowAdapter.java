package como.sekolah.submission3.adapter;

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

import como.sekolah.submission3.DetailTvshowActivity;
import como.sekolah.submission3.R;
import como.sekolah.submission3.model.TvShows;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    ArrayList<TvShows> mData = new ArrayList<>();
    public void setData(ArrayList<TvShows> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tvshow_item, viewGroup, false);
        return new TvShowViewHolder(mView);
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

        TextView tvTvshowJudul, tvTvshowDescription;
        ImageView imgPoster;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTvshowJudul = itemView.findViewById(R.id.tv_tvshow_judul);
            tvTvshowDescription = itemView.findViewById(R.id.tv_tvshow_description);
            imgPoster = itemView.findViewById(R.id.img_tvshow_poster);

            itemView.setOnClickListener(this);
        }

        void bind(TvShows tvShows){
            String url_poster = "https://image.tmdb.org/t/p/w185/" + tvShows.getPoster();

            tvTvshowJudul.setText(tvShows.getTitle());
            tvTvshowDescription.setText(tvShows.getOverview());

            Glide.with(itemView.getContext())
                    .load(url_poster)
                    .into(imgPoster);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            TvShows tvShows = mData.get(position);

            tvShows.setTitle(tvShows.getTitle());
            tvShows.setOverview(tvShows.getOverview());

            Intent intent = new Intent(itemView.getContext(), DetailTvshowActivity.class);
            intent.putExtra(DetailTvshowActivity.EXTRA_TVSHOW, tvShows);
            itemView.getContext().startActivity(intent);
        }
    }
}
