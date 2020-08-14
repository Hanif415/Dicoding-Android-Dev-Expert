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
import como.sekolah.submission4.entity.TvFav;
import como.sekolah.submission4.ui.favorite.DetailFavTvActivity;

public class FavTvAdapter extends RecyclerView.Adapter<FavTvAdapter.FavTvViewHolder> {

    private ArrayList<TvFav> listTv = new ArrayList<>();
    private Activity activity;

    public FavTvAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<TvFav> getListTv() {
        return listTv;
    }

    public void setListTv(ArrayList<TvFav> listTv) {
        if(listTv.size() > 0) {
            this.listTv.clear();
        }
        this.listTv.addAll(listTv);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_fav_item, parent, false);
        return new FavTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavTvViewHolder holder, int position) {
        holder.tvTitle.setText(listTv.get(position).getTitle());
        holder.tvDescription.setText(listTv.get(position).getOverview());
        holder.tvDate.setText(listTv.get(position).getFirst_air_date());
        holder.tvRate.setText(listTv.get(position).getVote_average());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185/" + listTv.get(position).getPoster())
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    class FavTvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView tvTitle, tvDescription, tvDate, tvRate;
        final ImageView imgPoster;

        FavTvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_show_favjudul);
            tvDescription = itemView.findViewById(R.id.tv_show_favdescription);
            tvDate = itemView.findViewById(R.id.tv_date_release_favtv);
            tvRate = itemView.findViewById(R.id.tv_rating_favtv);
            imgPoster = itemView.findViewById(R.id.img_tv_favposter);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            TvFav tvFav = listTv.get(position);

            tvFav.setTitle(tvFav.getTitle());
            tvFav.setOverview(tvFav.getOverview());
            tvFav.setVote_average(tvFav.getVote_average());
            tvFav.setFirst_air_date(tvFav.getFirst_air_date());

            Intent intent = new Intent(itemView.getContext(), DetailFavTvActivity.class);
            intent.putExtra(DetailFavTvActivity.EXTRA_FAV_TV, tvFav);
            itemView.getContext().startActivity(intent);
        }
    }
}
