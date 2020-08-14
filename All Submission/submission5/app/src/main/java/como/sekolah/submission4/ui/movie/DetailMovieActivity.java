package como.sekolah.submission4.ui.movie;

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
import como.sekolah.submission4.db.DatabaseMovieContract;
import como.sekolah.submission4.db.MovieHelper;
import como.sekolah.submission4.entity.Movies;

public class DetailMovieActivity extends AppCompatActivity{

    public static final String EXTRA_MOVIE = "extra_movie";
    TextView tvTitle, tvOverview, tvReleaseDate, tvVoteAverage;
    ImageView imgPoster;
    ProgressBar progressbar;
    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.movie_detail_title);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvReleaseDate = findViewById(R.id.tv_date_release);
        tvVoteAverage = findViewById(R.id.tv_vote_average);
        imgPoster = findViewById(R.id.img_poster);
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);

        final Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
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
                        String url_poster = "https://image.tmdb.org/t/p/w185/" + movies.getPoster();
                        tvTitle.setText(movies.getTitle());
                        tvOverview.setText(movies.getOverview());
                        tvReleaseDate.setText(movies.getRelease_date());
                        tvVoteAverage.setText(movies.getVote_average());
                        Glide.with(DetailMovieActivity.this)
                                .load(url_poster)
                                .into(imgPoster);
                        progressbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();
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
                        Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
                        String url_poster = movies.getPoster();
                        int id = movies.getId();
                        String title = tvTitle.getText().toString().trim();
                        String overview = tvOverview.getText().toString().trim();
                        String dateRelease = tvReleaseDate.getText().toString().trim();
                        String rating = tvVoteAverage.getText().toString().trim();

                        ContentValues values = new ContentValues();
                        values.put(DatabaseMovieContract.MovieColumns._ID, id);
                        values.put(DatabaseMovieContract.MovieColumns.TITLE, title);
                        values.put(DatabaseMovieContract.MovieColumns.OVERVIEW, overview);
                        values.put(DatabaseMovieContract.MovieColumns.RELEASE_DATE, dateRelease);
                        values.put(DatabaseMovieContract.MovieColumns.VOTE_AVERAGE, rating);
                        values.put(DatabaseMovieContract.MovieColumns.POSTER, url_poster);

                        long result = movieHelper.insert(values);

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
