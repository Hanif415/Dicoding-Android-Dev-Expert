package como.sekolah.mytablayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initialisation of SectionPagerAdapter
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
//        get view pager from layout
        ViewPager viewPager = findViewById(R.id.view_pager);
//        set viewpager adapter
        viewPager.setAdapter(sectionsPagerAdapter);
//        get tabLayout from layout
        TabLayout tabs = findViewById(R.id.tabs);
//        set tabLayout to viewpager
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
    }
}
