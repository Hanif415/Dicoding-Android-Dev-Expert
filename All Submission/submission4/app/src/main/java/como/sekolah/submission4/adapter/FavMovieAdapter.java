package como.sekolah.submission4.adapter;

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

import como.sekolah.submission4.R;
import como.sekolah.submission4.entity.MovieFav;
import como.sekolah.submission4.ui.favorite.DetailFavMovieActivity;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder> {

    private ArrayList<MovieFav> listMovie = new ArrayList<>();
    private Activity activity;

//    constractor
    public FavMovieAdapter(Activity activity) {
        this.activity = activity;
    }

//    getter
    public ArrayList<MovieFav> getListMovie() {
        return listMovie;
    }

//    setter
    public void setListMovie(ArrayList<MovieFav> listMovie) {

        if (listMovie.size() > 0) {
            this.listMovie.clear();
        }
        this.listMovie.addAll(listMovie);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_fav_item, parent, false);
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
            tvMovieTitle = itemView.findViewById(R.id.tv_favmovie_judul);
            tvMovieDescription = itemView.findViewById(R.id.tv_favmovie_description);
            tvMovieDate = itemView.findViewById(R.id.tv_date_release_favmovie);
            tvMovieRate = itemView.findViewById(R.id.tv_rating_favmovie);
            imgPoster = itemView.findViewById(R.id.img_favmovie_poster);
            cvFavMovie = itemView.findViewById(R.id.cv_favmovie_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            MovieFav movieFav = listMovie.get(position);

            movieFav.setTitle(movieFav.getTitle());
            movieFav.setOverview(movieFav.getOverview());
            movieFav.setVote_average(movieFav.getVote_average());
            movieFav.setRelease_date(movieFav.getRelease_date());

            Intent intent = new Intent(itemView.getContext(), DetailFavMovieActivity.class);
            intent.putExtra(DetailFavMovieActivity.EXTRA_FAV_MOVIE, movieFav);
            itemView.getContext().startActivity(intent);
        }
    }
}
