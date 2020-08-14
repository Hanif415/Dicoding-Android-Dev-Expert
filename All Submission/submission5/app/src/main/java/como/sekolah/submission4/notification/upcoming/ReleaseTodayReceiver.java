package como.sekolah.submission4.notification.upcoming;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import como.sekolah.submission4.BuildConfig;
import como.sekolah.submission4.R;
import cz.msebera.android.httpclient.Header;

public class ReleaseTodayReceiver extends BroadcastReceiver {

    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "my channel";
    private int notificationId = 2000;

    @Override
    public void onReceive(Context context, Intent intent) {

        AsyncHttpClient client = new AsyncHttpClient();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String today = dateFormat.format(date);
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+ BuildConfig.API_KEY +"&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        String title = context.getResources().getString(R.string.app_name);
                        String description = movie.getString("title") + " " + context.getResources().getString(R.string.label_description);

                        sendNotification(context, title, description, notificationId);

                        notificationId += 1;
                    }
                } catch (JSONException e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception", error.getMessage());
            }
        });
    }

    private void sendNotification(Context context, String title, String description, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.round_stars_black_36dp)
                .setContentTitle(title)
                .setContentText(description)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uri);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(CHANNEL_ID);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(id, builder.build());
    }

    public void setRepeatingAlarm(Context context, String time) {
        String[] timeArray = time.split(":");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, context.getResources().getString(R.string.label_switch_on), Toast.LENGTH_SHORT).show();
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, ReleaseTodayReceiver.class);
        return PendingIntent.getBroadcast(context, 1011, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
        Toast.makeText(context, context.getResources().getString(R.string.label_switch_off), Toast.LENGTH_SHORT).show();
    }
}
