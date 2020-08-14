package como.sekolah.submission4.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindString;
import butterknife.ButterKnife;
import como.sekolah.submission4.R;
import como.sekolah.submission4.notification.daily.DailyReceiver;
import como.sekolah.submission4.notification.upcoming.ReleaseTodayReceiver;

@SuppressWarnings("ALL")
public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        private DailyReceiver dailyReceiver = new DailyReceiver();
        private ReleaseTodayReceiver releaseTodayReceiver = new ReleaseTodayReceiver();;

        @BindString(R.string.key_daily_reminder)
        String reminder_daily;

        @BindString(R.string.key_now_release_reminder)
        String reminder_now_release;

        @BindString(R.string.key_language)
        String language;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            ButterKnife.bind(this, getActivity());

            findPreference(reminder_daily).setOnPreferenceChangeListener(this);
            findPreference(reminder_now_release).setOnPreferenceChangeListener(this);

            Preference preference = findPreference(getString(R.string.key_language));
            preference.setOnPreferenceClickListener(preference1 -> {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return false;
            });
        }

        @Override
        public boolean onPreferenceChange(android.preference.Preference preference, Object o) {
            String key = preference.getKey();
            boolean isActive = (boolean) o;

            if (key.equals(reminder_daily)) {
                if (isActive) {
                    dailyReceiver.setRepeatingAlarm(getActivity(), dailyReceiver.TYPE_REPEATING, "07:00", getString(R.string.notification_alarm_daily_reminder));
                } else {
                    dailyReceiver.CancelAlarm(getActivity(), dailyReceiver.TYPE_REPEATING);
                }
            }

            if (key.equals(reminder_now_release)) {
                if (isActive) {
                    releaseTodayReceiver.setRepeatingAlarm(getActivity(), "08:00");
                } else {
                    releaseTodayReceiver.cancelAlarm(getActivity());
                }
            }

            return true;
        }
    }
}
