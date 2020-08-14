package como.sekolah.myflexiblefragment;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener{


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btndetailCategory = view.findViewById(R.id.btn_detail_category);
        btndetailCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_detail_category){
            DetailCategoryFragment mDetailCategoryFragment = new DetailCategoryFragment();

//            untuk mengirimkan data antar fragment disini menggunaka Bundle
            Bundle mBundle =  new Bundle();
            mBundle.putString(DetailCategoryFragment.EXTRA_NAME, "LifeStyle");
//            script dibawah untuk mengirimkan data
            mDetailCategoryFragment.setArguments(mBundle);

//            untuk bagian deskripsi menggunakan method setter dan getter berbeda dengan categoryName yang menggunakan putString
            String descriptiion = "Kategori Ini Berisi Produk Produk LifeStyle";
            mDetailCategoryFragment.setDescription(descriptiion);

//            script di bawah berfungsi untuk pindah Fragment
            FragmentManager mFragmentmanager = getFragmentManager();
            if (mFragmentmanager != null){
                FragmentTransaction mFragmenttransaction = mFragmentmanager.beginTransaction();
                mFragmenttransaction.replace(R.id.frame_container, mDetailCategoryFragment, DetailCategoryFragment.class.getSimpleName());
                mFragmenttransaction.addToBackStack(null);
                mFragmenttransaction.commit();
            }
        }
    }
}
