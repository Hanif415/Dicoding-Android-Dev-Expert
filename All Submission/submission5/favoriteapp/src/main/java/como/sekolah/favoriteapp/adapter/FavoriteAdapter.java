package como.sekolah.favoriteapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class FavoriteAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mfragments = new ArrayList<>();
    private ArrayList<String> mtitle = new ArrayList<>();

    public FavoriteAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mtitle.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mfragments.add(fragment);
        mtitle.add(title);
    }
}
