package como.sekolah.submission3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import como.sekolah.submission3.model.TvShows;

public class DetailTvshowActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    TextView tvTitle, tvOverview;
    ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);

        tvTitle = findViewById(R.id.tvtitle_detail_tv);
        tvOverview = findViewById(R.id.tvoverview_detail_tv);
        imgPoster = findViewById(R.id.imgposter_detailtv);


        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TvShows tvShows = getIntent().getParcelableExtra(EXTRA_TVSHOW);

                        String url_poster = "https://image.tmdb.org/t/p/w185/"+ tvShows.getPoster();

                        tvTitle.setText(tvShows.getTitle());
                        tvOverview.setText(tvShows.getOverview());

                        Glide.with(DetailTvshowActivity.this)
                                .load(url_poster)
                                .into(imgPoster);
                    }
                });
            }
        }).start();
    }
}
