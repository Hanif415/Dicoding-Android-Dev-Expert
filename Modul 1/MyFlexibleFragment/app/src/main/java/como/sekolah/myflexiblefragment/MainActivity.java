package como.sekolah.myflexiblefragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //merupakan antar muka yang brfungsi untuk mengorganisir objek fragment yang terdapat pada sebua activity
        FragmentManager mFragmentManager = getSupportFragmentManager();
        //fragment transaction merupakan api untuk melakukan oprasi pada fragment seperti add(), replace(), dll
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
//        pembuatan objek HomeFragment
        HomeFragment mHomeFragment = new HomeFragment();

        Fragment fragment = mFragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
        if (!(fragment instanceof HomeFragment)){
//            disinilah proses penambahan fragment ke dalam acivity dengan mengguaka metode add();
            mFragmentTransaction.add(R.id.frame_container, mHomeFragment, HomeFragment.class.getSimpleName());
            Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragment.class.getSimpleName());
//            baris di bawah akan meminta mFragment Transaction untuk melakukan pemasangan objek secara asyncronus untuk ditampilkan ke user interface
            mFragmentTransaction.commit();
        }
    }
}
