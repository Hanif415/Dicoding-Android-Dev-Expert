package como.sekolah.submission3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import como.sekolah.submission3.R;
import como.sekolah.submission3.model.Movies;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    TextView tvTitle, tvOverview;
    ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvTitle = findViewById(R.id.tvtitle_detail_movie);
        tvOverview = findViewById(R.id.tvoverview_detail_movie);
        imgPoster = findViewById(R.id.imgposter_detailmovie);


        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIE);

                        String url_poster = "https://image.tmdb.org/t/p/w185/" + movies.getPoster();

                        tvTitle.setText(movies.getTitle());
                        tvOverview.setText(movies.getOverview());

                        Glide.with(DetailMovieActivity.this)
                                .load(url_poster)
                                .into(imgPoster);
                    }
                });
            }
        }).start();

    }
}
