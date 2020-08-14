package como.sekolah.submission4.notification.daily;

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
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

import como.sekolah.submission4.MainActivity;
import como.sekolah.submission4.R;

public class DailyReceiver extends BroadcastReceiver {

    public static final String TYPE_ONE_TIME = "OneTimeRepeating";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_MESSEGE = "messege";
    public static final String EXTRA_TYPE = "type";

    private final int NOTIF_ID_ONETIME = 100;
    private final int NOTIF_ID_REPEATING = 101;

    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "my channel";

    public DailyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String messege = intent.getStringExtra(EXTRA_MESSEGE);

        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;

        String title = context.getResources().getString(R.string.app_name);

        showNotification(context, title, messege, notifId);
    }

    private void showNotification(Context context, String title, String message, int notificationId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.round_stars_black_36dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_NAME.toString());
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        notificationManagerCompat.notify(notificationId, builder.build());
    }

    public void setRepeatingAlarm(Context context, String type, String time, String message){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, DailyReceiver.class);
        intent.putExtra(EXTRA_MESSEGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);

        int requestCode = NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, context.getResources().getString(R.string.label_switch_on), Toast.LENGTH_SHORT).show();
    }

    public void CancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);

        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

        alarmManager.cancel(pendingIntent);

        Toast.makeText(context, context.getResources().getString(R.string.label_switch_off), Toast.LENGTH_SHORT).show();
    }
}
