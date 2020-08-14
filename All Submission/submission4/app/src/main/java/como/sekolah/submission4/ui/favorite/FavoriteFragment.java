package como.sekolah.submission4.ui.favorite;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import como.sekolah.submission4.R;
import como.sekolah.submission4.adapter.FavoriteAdapter;

public class FavoriteFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View view;

    public FavoriteFragment() {
    }

    @SuppressWarnings("deprecation")
    private class sliderAdapter extends FragmentPagerAdapter {
        String movie = getString(R.string.title_movie);
        String tv = getString(R.string.title_tvshow);
        final String tabs[] = {movie, tv};

        public sliderAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FavMovieFragment();
                case 1:
                    return new FavTvFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));

        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}