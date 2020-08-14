package como.sekolah.mytablayout;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

//    Constractor
    public SectionsPagerAdapter(@NonNull FragmentManager fm, Context context) {
        /*Mulai API 27 ke atas, Anda perlu menambahkan behaviour pada parameter kedua,
        fungsinya yaitu supaya hanya fragment yang ditampilkan saja yang akan masuk
        ke lifecycle RESUMED, sedangkan yang lainnya masuk ke STARTED. */
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = context;
    }

//    Tab title method array
    @StringRes
    private final int[] TAB_TITLE = new int[] {
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
    };

//    fragment tab switching method
    @NonNull
    @Override
    public Fragment getItem(int position) {
        /*Pada kode di bawah, parameter yang ada di dalam newInstance digunakan untuk mengirim data posisi*/
        Fragment fragment = HomeFragment.newInstance(position + 1);
        return fragment;
    }

//  named the tab method
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLE[position]);
    }

//   number of tab
    @Override
    public int getCount() {
        return 3;
    }
}
