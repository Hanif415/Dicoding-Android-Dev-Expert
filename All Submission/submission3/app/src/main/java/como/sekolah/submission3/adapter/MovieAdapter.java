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

import como.sekolah.submission3.R;
import como.sekolah.submission3.DetailMovieActivity;
import como.sekolah.submission3.model.Movies;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movies> mData = new ArrayList<>();
    public void setData(ArrayList<Movies> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }





    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvMovieJudul, tvMovieDescription;;
        ImageView imgPoster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieJudul = itemView.findViewById(R.id.tv_movie_judul);
            tvMovieDescription = itemView.findViewById(R.id.tv_movie_description);
            imgPoster = itemView.findViewById(R.id.img_movie_poster);

            itemView.setOnClickListener(this);
        }

//        function di bawah berfungsi untuk memasukan data yang di ambil dari model kedalam komponen komponen seperti TextView dll.
//        setelah itu dipanggil di method onBindView Holder
        void bind(Movies movies){
            String url_poster ="https://image.tmdb.org/t/p/w185/" + movies.getPoster();

            tvMovieJudul.setText(movies.getTitle());
            tvMovieDescription.setText(movies.getOverview());
            Glide.with(itemView.getContext())
                    .load(url_poster)
                    .into(imgPoster);
        }



//       method yang berfungsi berpindah intent ke DetailMovieActivity dengan membawa data movie yang diambil dari model Movies
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movies movies = mData.get(position);

            movies.setTitle(movies.getTitle());
            movies.setOverview(movies.getOverview());

            Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movies);
            itemView.getContext().startActivity(intent);
        }
    }
}
