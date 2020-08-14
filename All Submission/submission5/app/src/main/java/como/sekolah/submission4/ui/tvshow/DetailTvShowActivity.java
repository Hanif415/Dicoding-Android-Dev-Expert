package como.sekolah.submission4.ui.tvshow;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import como.sekolah.submission4.R;
import como.sekolah.submission4.db.DatabaseTVShowContract;
import como.sekolah.submission4.db.TVShowHelper;
import como.sekolah.submission4.entity.TVShows;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_TV = "extra_tv";
    TextView tvTitle, tvOverview, tvDate, tvRate;
    ImageView imgPoster;
    ProgressBar progressbar;

    private TVShowHelper tvShowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.tv_detail_title);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvDate = findViewById(R.id.tv_date_release);
        tvRate = findViewById(R.id.tv_vote_average);
        imgPoster = findViewById(R.id.img_poster);
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);

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
                        TVShows tvShows = getIntent().getParcelableExtra(EXTRA_TV);
                        String url_poster = "https://image.tmdb.org/t/p/w185/"+ tvShows.getPoster();
                        tvTitle.setText(tvShows.getTitle());
                        tvOverview.setText(tvShows.getOverview());
                        tvDate.setText(tvShows.getRelease_date());
                        tvRate.setText(tvShows.getVote_average());
                        Glide.with(DetailTvShowActivity.this)
                                .load(url_poster)
                                .into(imgPoster);
                        progressbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();

        tvShowHelper = TVShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_fav) {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        int dialogTitle = R.string.dialog_add_title;
        int dialogMessage = R.string.dialog_add_message;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TVShows tvShows = getIntent().getParcelableExtra(EXTRA_TV);
                        String url_poster = tvShows.getPoster();
                        int id = tvShows.getId();
                        String title = tvTitle.getText().toString().trim();
                        String overview = tvOverview.getText().toString().trim();
                        String dateRelease = tvDate.getText().toString().trim();
                        String rating = tvRate.getText().toString().trim();

                        ContentValues values = new ContentValues();
                        values.put(DatabaseTVShowContract.TvShowColumns._ID, id);
                        values.put(DatabaseTVShowContract.TvShowColumns.TITLE, title);
                        values.put(DatabaseTVShowContract.TvShowColumns.OVERVIEW, overview);
                        values.put(DatabaseTVShowContract.TvShowColumns.FIRST_AIR_DATE, dateRelease);
                        values.put(DatabaseTVShowContract.TvShowColumns.VOTE_AVERAGE, rating);
                        values.put(DatabaseTVShowContract.TvShowColumns.POSTER, url_poster);
                        long result = tvShowHelper.insert(values);

                        if (result > 0) {
                            Snackbar.make(findViewById(R.id.action_fav), title + " " + getString(R.string.notification_add), Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(findViewById(R.id.action_fav), title + " " + getString(R.string.notification_data_exist), Snackbar.LENGTH_SHORT).show();
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
