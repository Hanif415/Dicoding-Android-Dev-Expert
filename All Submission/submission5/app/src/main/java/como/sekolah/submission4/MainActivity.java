package como.sekolah.submission4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import como.sekolah.submission4.settings.SettingsActivity;
import como.sekolah.submission4.ui.searchview.movie.SearchMoviePage;
import como.sekolah.submission4.ui.searchview.tvshow.SearchTVShowPage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_movie, R.id.navigation_tvshow, R.id.navigation_favorite)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        contextOfApplication = getContextOfApplication();
    }

    public static Context contextOfApplication;
    public static Context getContextOfApplication(){
        return  contextOfApplication;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_search_movie) {
            Intent intent = new Intent(MainActivity.this, SearchMoviePage.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_search_tvshow) {
            Intent intent = new Intent(MainActivity.this, SearchTVShowPage.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_setings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
