package como.sekolah.favoriteapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import como.sekolah.favoriteapp.R;
import como.sekolah.favoriteapp.entity.FavoriteMovie;
import como.sekolah.favoriteapp.ui.favorite.DetailFavoriteMovieActivity;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavMovieViewHolder> {

    private ArrayList<FavoriteMovie> listMovie = new ArrayList<>();
    private Activity activity;

//    constractor
    public FavoriteMovieAdapter(Activity activity) {
        this.activity = activity;
    }

//    getter
    public ArrayList<FavoriteMovie> getListMovie() {
        return listMovie;
    }

//    setter
    public void setListMovie(ArrayList<FavoriteMovie> listMovie) {

        if (listMovie.size() > 0) {
            this.listMovie.clear();
        }
        this.listMovie.addAll(listMovie);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_movie_item, parent, false);
        return new FavMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieViewHolder holder, int position) {

        holder.tvMovieTitle.setText(listMovie.get(position).getTitle());
        holder.tvMovieDescription.setText(listMovie.get(position).getOverview());
        holder.tvMovieDate.setText(listMovie.get(position).getRelease_date());
        holder.tvMovieRate.setText(listMovie.get(position).getVote_average());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185/" + listMovie.get(position).getPoster())
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class FavMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView tvMovieTitle, tvMovieDescription, tvMovieDate, tvMovieRate;
        final ImageView imgPoster;
        final CardView cvFavMovie;

        FavMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tv_title);
            tvMovieDescription = itemView.findViewById(R.id.tv_overview);
            tvMovieDate = itemView.findViewById(R.id.tv_date_release);
            tvMovieRate = itemView.findViewById(R.id.tv_vote_average);
            imgPoster = itemView.findViewById(R.id.img_poster);
            cvFavMovie = itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            FavoriteMovie favoriteMovie = listMovie.get(position);

            favoriteMovie.setTitle(favoriteMovie.getTitle());
            favoriteMovie.setOverview(favoriteMovie.getOverview());
            favoriteMovie.setVote_average(favoriteMovie.getVote_average());
            favoriteMovie.setRelease_date(favoriteMovie.getRelease_date());

            Intent intent = new Intent(itemView.getContext(), DetailFavoriteMovieActivity.class);
            intent.putExtra(DetailFavoriteMovieActivity.EXTRA_FAV_MOVIE, favoriteMovie);
            itemView.getContext().startActivity(intent);
        }
    }
}
