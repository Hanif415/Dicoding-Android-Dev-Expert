package como.sekolah.favoriteapp.ui.favorite;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import como.sekolah.favoriteapp.MainActivity;
import como.sekolah.favoriteapp.R;
import como.sekolah.favoriteapp.entity.FavoriteMovie;

import static como.sekolah.favoriteapp.db.DatabaseMovieContract.MovieColumns.CONTENT_URI;

public class DetailFavoriteMovieActivity extends AppCompatActivity {

    public static final String EXTRA_FAV_MOVIE = "extra_movie";
    TextView tvTitle, tvOverview, tvDateRelease, tvVoteAverage;
    ImageView imgPoster;
    ProgressBar progressbar;
    Uri uriWithId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite_movie);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.movie_detail_title);
        }

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvDateRelease = findViewById(R.id.tv_date_release);
        tvVoteAverage = findViewById(R.id.tv_vote_average);
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
                        FavoriteMovie moviesFav = getIntent().getParcelableExtra(EXTRA_FAV_MOVIE);
                        String url_poster = "https://image.tmdb.org/t/p/w185/" + moviesFav.getPoster();
                        tvTitle.setText(moviesFav.getTitle());
                        tvOverview.setText(moviesFav.getOverview());
                        tvDateRelease.setText(moviesFav.getRelease_date());
                        tvVoteAverage.setText(moviesFav.getVote_average());
                        Glide.with(DetailFavoriteMovieActivity.this)
                                .load(url_poster)
                                .into(imgPoster);
                        progressbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
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
                        FavoriteMovie favoriteMovie = getIntent().getParcelableExtra(EXTRA_FAV_MOVIE);
                        String title = favoriteMovie.getTitle();
                        uriWithId = Uri.parse(CONTENT_URI + "/" + favoriteMovie.getId());
                        getContentResolver().delete(uriWithId, null, null);
                        Toast.makeText(DetailFavoriteMovieActivity.this, title + " berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailFavoriteMovieActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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
