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
import como.sekolah.favoriteapp.entity.FavoriteTVShow;

import static como.sekolah.favoriteapp.db.DatabaseTVShowContract.TvShowColumns.CONTENT_URI;

public class DetailFavoriteTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_FAV_TV = "extra_tv";
    TextView tvTitle, tvOverview, tvDateRelease, tvVoteAverage;
    ImageView imgPoster;
    ProgressBar progressBar;
    Uri uriWithid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite_tv_show);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.tv_detail_title);
        }

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvDateRelease = findViewById(R.id.tv_date_release);
        tvVoteAverage = findViewById(R.id.tv_vote_average);
        imgPoster = findViewById(R.id.img_poster);
        progressBar = findViewById(R.id.progressbar);

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
                        FavoriteTVShow favoriteTVShow = getIntent().getParcelableExtra(EXTRA_FAV_TV);
                        String url_poster = "https://image.tmdb.org/t/p/w185/" + favoriteTVShow.getPoster();
                        tvTitle.setText(favoriteTVShow.getTitle());
                        tvOverview.setText(favoriteTVShow.getOverview());
                        tvDateRelease.setText(favoriteTVShow.getFirst_air_date());
                        tvVoteAverage.setText(favoriteTVShow.getVote_average());
                        Glide.with(DetailFavoriteTvShowActivity.this)
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
                        FavoriteTVShow favoriteTVShow = getIntent().getParcelableExtra(EXTRA_FAV_TV);
                        String title = favoriteTVShow.getTitle();
                        uriWithid = Uri.parse(CONTENT_URI + "/" + favoriteTVShow.getId());
                        getContentResolver().delete(uriWithid, null, null);
                        Toast.makeText(DetailFavoriteTvShowActivity.this, title + " berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailFavoriteTvShowActivity.this, MainActivity.class);
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
