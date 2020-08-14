package como.sekolah.submission2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder> {

    private ArrayList<Movie> listMovie;
    private OnItemClickCallback onItemClickCallback;
    private Context context;


    public MovieAdapter(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public MovieAdapter(Context context) {
        this.context = context;
        listMovie = new ArrayList<>();
    }








    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        Movie movie = listMovie.get(position);

        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .apply(new RequestOptions().override(500, 500))
                .into(holder.imgPoster);

        holder.tvJudul.setText(movie.getJudul());
        holder.tvRating.setText(movie.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onItemClickCallback.onItemClicked(listMovie.get(holder.getAdapterPosition()));
        }
    });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }



    class Holder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvJudul, tvRating;

        Holder(View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_movie);
            tvJudul = itemView.findViewById(R.id.tv_judul_movie);
            tvRating = itemView.findViewById(R.id.tv_rating);

        }
    }

//  Interface
    public interface OnItemClickCallback{
        void onItemClicked(Movie data);
    }
}
