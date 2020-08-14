package como.sekolah.submission4.ui.favorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import como.sekolah.submission4.MainActivity;
import como.sekolah.submission4.R;
import como.sekolah.submission4.db.TvShowHelper;
import como.sekolah.submission4.entity.MovieFav;
import como.sekolah.submission4.entity.TvFav;

public class DetailFavTvActivity extends AppCompatActivity {

    public static final String EXTRA_FAV_TV = "extra_tv";
    TextView tvTitle, tvOverview, tvDate, tvRate;
    ImageView imgPoster;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fav_tv);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.tv_detail_title);
        }

        tvTitle = findViewById(R.id.tv_title_detail_favtv);
        tvOverview = findViewById(R.id.tv_overview_detail_favtv);
        tvDate = findViewById(R.id.tv_date_detail_favtv);
        tvRate = findViewById(R.id.tv_rate_detail_favtv);
        imgPoster = findViewById(R.id.img_detail_favtv);
        progressBar = findViewById(R.id.progressDetailFavTV);

        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TvFav tvFav = getIntent().getParcelableExtra(EXTRA_FAV_TV);
                        String url_poster = "https://image.tmdb.org/t/p/w185/" + tvFav.getPoster();
                        tvTitle.setText(tvFav.getTitle());
                        tvOverview.setText(tvFav.getOverview());
                        tvDate.setText(tvFav.getFirst_air_date());
                        tvRate.setText(tvFav.getVote_average());
                        Glide.with(DetailFavTvActivity.this)
                                .load(url_poster)
                                .into(imgPoster);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        showAlertDialog();
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        int dialogTitle = R.string.dialog_delete_title;
        int dialogMessage =  R.string.dialog_delete_message;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TvFav tvFav = getIntent().getParcelableExtra(EXTRA_FAV_TV);
                        String title = tvFav.getTitle();
                        long resultDel = TvShowHelper.deleteById(String.valueOf(tvFav.getId()));
                        if (resultDel > 0) {
                            Snackbar.make(findViewById(R.id.action_fav), title + " " + getString(R.string.notification_del), Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailFavTvActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
