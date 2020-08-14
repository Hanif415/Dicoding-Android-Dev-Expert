package como.sekolah.myflexiblefragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public HomeFragment() {
        // Required empty public constructor
    }

//    berfungsi untu mentransformasikan sebuah user interface(layout berupa file xml) ke dalam objek mengunakan metode inflate
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflater.inflate merupakan objek dari LayoutInflater
            return inflater.inflate(R.layout.fragment_home, container, false);
    }


//    berfungsi untuk inisialisasi view dan jua mengatur actionya(set listener)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnCategory = view.findViewById(R.id.btn_category);
        btnCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_category){
            FragmentManager mFragmentManager = getFragmentManager();
            if (mFragmentManager != null){
                CategoryFragment mCategoryFragment = new CategoryFragment();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame_container, mCategoryFragment, CategoryFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        }
    }
}
