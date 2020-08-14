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

import como.sekolah.submission4.ui.movie.DetailMovieActivity;
import como.sekolah.submission4.R;
import como.sekolah.submission4.entity.Movies;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movies> mData = new ArrayList<>();

    public void setDataMovie(ArrayList<Movies> dataMovie) {
        mData.clear();
        mData.addAll(dataMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView tvMovieJudul, tvMovieDescription, tvMovieDate, tvMovieRate;
        final ImageView imgPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieJudul = itemView.findViewById(R.id.tv_movie_judul);
            tvMovieDescription = itemView.findViewById(R.id.tv_movie_description);
            imgPoster = itemView.findViewById(R.id.img_movie_poster);
            tvMovieDate = itemView.findViewById(R.id.tv_date_release_movie);
            tvMovieRate = itemView.findViewById(R.id.tv_rating_movie);

            itemView.setOnClickListener(this);
        }

        void bind(Movies movies){
            String url_poster = "https://image.tmdb.org/t/p/w185/" + movies.getPoster();

            tvMovieJudul.setText(movies.getTitle());
            tvMovieDescription.setText(movies.getOverview());
            tvMovieDate.setText(movies.getRelease_date());
            tvMovieRate.setText(movies.getVote_average());
            Glide.with(itemView.getContext())
                    .load(url_poster)
                    .into(imgPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movies movies = mData.get(position);

            movies.setTitle(movies.getTitle());
            movies.setOverview(movies.getOverview());
            movies.setRelease_date(movies.getRelease_date());
            movies.setVote_average(movies.getVote_average());

            Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movies);
            itemView.getContext().startActivity(intent);
        }
    }
}
