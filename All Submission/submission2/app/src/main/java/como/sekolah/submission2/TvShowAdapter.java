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

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.Holder> {

    private ArrayList<TvShow> listTvShow;
    private Context context;
    private OnItemClickCallback onItemClickCallback;


    public void setListTvShow(ArrayList<TvShow> listTvShow) {
        this.listTvShow = listTvShow;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public TvShowAdapter(ArrayList<TvShow> listTvShow) {
        this.listTvShow = listTvShow;
    }

    public TvShowAdapter(Context context) {
        this.context = context;
        listTvShow = new ArrayList<>();
    }






    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_tvshow, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        TvShow tvShow = listTvShow.get(position);

        Glide.with(holder.itemView.getContext())
                .load(tvShow.getPoster())
                .apply(new RequestOptions().override(500, 500))
                .into(holder.imgPoster);

        holder.tvJudul.setText(tvShow.getJudul());
        holder.tvRating.setText(tvShow.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTvShow.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
        TextView tvJudul, tvRating;

        Holder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_tvshow);
            tvJudul = itemView.findViewById(R.id.tv_judul_tvshow);
            tvRating = itemView.findViewById(R.id.tv_rating_tvshow);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(TvShow data);
    }
}
