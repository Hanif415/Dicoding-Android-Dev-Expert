package como.sekolah.mysettingpreference;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String DEFAULT_VALUE = "Tidak Ada";
    private String NAME;
    private String EMAIL;
    private String AGE;
    private String PHONE;
    private String LOVE ;

    private EditTextPreference namePreference;
    private EditTextPreference emailPreference;
    private EditTextPreference agePreference;
    private EditTextPreference phonePreference;
    private CheckBoxPreference isLoveMuPreference;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummaries();
    }


//    Metode init() digunakan untuk memanggil komponen seperti EditTextPreference dan CheckBoxPreference
//    berdasarkan key yang sudah ditetapkan di preferences.xml.
    private void init() {
        NAME = getResources().getString(R.string.key_name);
        EMAIL = getResources().getString(R.string.key_email);
        AGE = getResources().getString(R.string.key_age);
        PHONE = getResources().getString(R.string.key_phone);
        LOVE = getResources().getString(R.string.key_love);

        namePreference = (EditTextPreference) findPreference(NAME);
        emailPreference = (EditTextPreference) findPreference(EMAIL);
        agePreference = (EditTextPreference) findPreference(AGE);
        phonePreference = (EditTextPreference) findPreference(PHONE);
        isLoveMuPreference = (CheckBoxPreference) findPreference(LOVE);
    }


//    Lalu  metode setSummaries() digunakan untuk merubah summary-nya.
//    Tulis metode ini di onCreatePreferences supaya terpanggil pada saat pertama kali aplikasi dibuka.
    private void setSummaries() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        namePreference.setSummary(sh.getString(NAME, DEFAULT_VALUE));
        emailPreference.setSummary(sh.getString(EMAIL, DEFAULT_VALUE));
        agePreference.setSummary(sh.getString(AGE, DEFAULT_VALUE));
        phonePreference.setSummary(sh.getString(PHONE, DEFAULT_VALUE));
        isLoveMuPreference.setChecked(sh.getBoolean(LOVE, false));
    }
//    Perintah setSummary berguna untuk mengubah value dari EditTextPreference menjadi nilai yang baru sesuai dengan data yang tersimpan.


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
//    Kode di atas digunakan untuk me-register ketika aplikasi dibuka dan me-unregister ketika aplikasi ditutup.
//    Hal ini supaya listener tidak berjalan terus menerus dan menyebabkan memory leak.


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(NAME)) {
            namePreference.setSummary(sharedPreferences.getString(NAME, DEFAULT_VALUE));
        }

        if (key.equals(EMAIL)){
            emailPreference.setSummary(sharedPreferences.getString(EMAIL, DEFAULT_VALUE));
        }

        if (key.equals(AGE)){
            agePreference.setSummary(sharedPreferences.getString(AGE, DEFAULT_VALUE));
        }

        if (key.equals(PHONE)){
            phonePreference.setSummary(sharedPreferences.getString(PHONE, DEFAULT_VALUE));
        }

        if (key.equals(LOVE)){
            isLoveMuPreference.setChecked(sharedPreferences.getBoolean(LOVE, false));
        }

//        Kode di atas digunakan untuk mengecek apakah terjadi perubahan pada data yang tersimpan.
//        Jika terdapat value yang berubah maka akan memanggil listener onSharedPreferenceChanged.
//        Untuk mendapatkan value tersebut, lakukan validasi terlebih dahulu. Dengan mencocokkan key mana yang berubah, hasilnya juga akan berubah.
    }
}
