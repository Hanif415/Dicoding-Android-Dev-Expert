package como.sekolah.submission4.ui.searchview.movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import como.sekolah.submission4.R;

public class SearchMoviePage extends AppCompatActivity implements View.OnClickListener {

    EditText edtSearch;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_page);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.item_title_movie_search);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtSearch = findViewById(R.id.edt_search);
        btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search){
            String title = edtSearch.getText().toString().trim();
            Intent intent = new Intent(SearchMoviePage.this, MovieSearchView.class);
            intent.putExtra(MovieSearchView.EXTRA_MOVIE, title);
            startActivity(intent);
        }
    }
}
