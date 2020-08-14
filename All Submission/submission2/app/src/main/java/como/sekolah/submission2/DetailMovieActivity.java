package como.sekolah.submission2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    TextView tvJuduls;
    TextView tvRatings;
    ImageView imgPosters;
    TextView tvSinopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvJuduls = findViewById(R.id.tv_judul);
        imgPosters = findViewById(R.id.img_poster);
        tvRatings = findViewById(R.id.tv_rating);
        tvSinopsis = findViewById(R.id.tv_sinopsis);


        Movie movie = (Movie)getIntent().getParcelableExtra(EXTRA_MOVIE);
        String judul = movie.getJudul();
        int poster = movie.getPoster();
        String rating = movie.getRating();
        String sinopsis = movie.getSinopsis();

        Glide.with(this).load(poster).into(imgPosters);
        tvJuduls.setText(judul);
        tvRatings.setText(rating);
        tvSinopsis.setText(sinopsis);
    }
}
