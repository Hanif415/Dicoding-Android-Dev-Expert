package como.sekolah.myactionbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager  = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null){
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            search query hint berfungsi untuk memberikan hint kepada pengguna dala bentuk text
            searchView.setQueryHint(getResources().getString(R.string.search_hint));




            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                onQueryTextSubmit akan bekerj ketika tombol submit ditekan
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    return true;
                }

//                onQueryTextChange akan bekerja ketika text nya di ubah
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                MenuFragment menuFragment = new MenuFragment();
                FragmentManager mFragmentManager = getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, menuFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();

                return true;
            case R.id.menu2:
                Intent i = new Intent(this, MenuActivity.class);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }
}
