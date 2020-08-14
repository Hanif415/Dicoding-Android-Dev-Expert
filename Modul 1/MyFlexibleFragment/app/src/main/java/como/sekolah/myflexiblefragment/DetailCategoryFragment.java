package como.sekolah.myflexiblefragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCategoryFragment extends Fragment implements View.OnClickListener{

    TextView tvCategoryName;
    TextView tvCategoryDescription;
    Button btnProfie;
    Button btnShowDialog;

    public static String EXTRA_NAME = "extra_name";
    public static String EXTRA_DESCRIPTION = "extra_descriippsion";
    private String description;

//    menggunakan method getter dan setter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public DetailCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCategoryName = view.findViewById(R.id.tv_category_name);
        tvCategoryDescription = view.findViewById(R.id.tv_category_description);
        btnProfie = view.findViewById(R.id.btn_profile);
        btnProfie.setOnClickListener(this);
        btnShowDialog = view.findViewById(R.id.btn_show_dialog);
        btnShowDialog.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        script di bawah berfungsi menerima data dari CategoryFragment
        String categoryName = getArguments().getString(EXTRA_NAME);

        tvCategoryName.setText(categoryName);
//        get description berfungsi menerima data mengguankan method get
        tvCategoryDescription.setText(getDescription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_profile:
                Intent mIntent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(mIntent);
                break;

            case R.id.btn_show_dialog:
                OptionDialogFragment mOptionDialogFragment = new OptionDialogFragment();

//                menggunakan getChildFragmentManager karen kodisi ini sangat tepat karen ini fragment bersarang;
                FragmentManager mFragmentManager = getChildFragmentManager();
//                baris yang digunakan untuk menampilkan Option dialog ke layar
                mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment.class.getSimpleName());
                break;
        }
    }

//    menampilkan toast ke pengguna
    OptionDialogFragment.OnOptionDialogListener optionDialogListener = new OptionDialogFragment.OnOptionDialogListener(){
        @Override
        public void onOptionChosen(String text) {
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        }
    };
}
