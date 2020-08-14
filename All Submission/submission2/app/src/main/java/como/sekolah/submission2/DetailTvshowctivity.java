package como.sekolah.submission2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailTvshowctivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOW = "extra_tvshow";

    TextView tvJuduls;
    TextView tvRatings;
    ImageView imgPosters;
    TextView tvSinopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshowctivity);

        tvJuduls = findViewById(R.id.tv_judul);
        imgPosters = findViewById(R.id.img_poster);
        tvRatings = findViewById(R.id.tv_rating);
        tvSinopsis = findViewById(R.id.tv_sinopsis);

        TvShow tvShow = (TvShow)getIntent().getParcelableExtra(EXTRA_TVSHOW);
        String judul = tvShow.getJudul();
        String rating = tvShow.getRating();
        int poster = tvShow.getPoster();
        String sinopsis = tvShow.getSinopsis();

        tvJuduls.setText(judul);
        tvRatings.setText(rating);
        tvSinopsis.setText(sinopsis);
        Glide.with(this).load(poster).into(imgPosters);


    }
}
