package como.sekolah.favoriteapp.adapter;

import android.app.Activity;
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

import como.sekolah.favoriteapp.R;
import como.sekolah.favoriteapp.entity.FavoriteTVShow;
import como.sekolah.favoriteapp.ui.favorite.DetailFavoriteTvShowActivity;

public class FavoriteTVShowAdapter extends RecyclerView.Adapter<FavoriteTVShowAdapter.FavTvViewHolder> {

    private ArrayList<FavoriteTVShow> listTv = new ArrayList<>();
    private Activity activity;

    public FavoriteTVShowAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<FavoriteTVShow> getListTv() {
        return listTv;
    }

    public void setListTv(ArrayList<FavoriteTVShow> listTv) {
        if(listTv.size() > 0) {
            this.listTv.clear();
        }
        this.listTv.addAll(listTv);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_tv_show_item, parent, false);
        return new FavTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavTvViewHolder holder, int position) {
        holder.tvTitle.setText(listTv.get(position).getTitle());
        holder.tvOverview.setText(listTv.get(position).getOverview());
        holder.tvDateRelease.setText(listTv.get(position).getFirst_air_date());
        holder.tvVoteAverage.setText(listTv.get(position).getVote_average());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185/" + listTv.get(position).getPoster())
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    class FavTvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView tvTitle, tvOverview, tvDateRelease, tvVoteAverage;
        final ImageView imgPoster;

        FavTvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvDateRelease = itemView.findViewById(R.id.tv_date_release);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_average);
            imgPoster = itemView.findViewById(R.id.img_poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            FavoriteTVShow favoriteTVShow = listTv.get(position);

            favoriteTVShow.setTitle(favoriteTVShow.getTitle());
            favoriteTVShow.setOverview(favoriteTVShow.getOverview());
            favoriteTVShow.setVote_average(favoriteTVShow.getVote_average());
            favoriteTVShow.setFirst_air_date(favoriteTVShow.getFirst_air_date());

            Intent intent = new Intent(itemView.getContext(), DetailFavoriteTvShowActivity.class);
            intent.putExtra(DetailFavoriteTvShowActivity.EXTRA_FAV_TV, favoriteTVShow);
            itemView.getContext().startActivity(intent);
        }
    }
}
