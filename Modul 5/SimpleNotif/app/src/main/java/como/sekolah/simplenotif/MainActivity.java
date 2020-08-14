package como.sekolah.simplenotif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID = 1;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "dicoding channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendNotification(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp))
                .setContentTitle(getResources().getString(R.string.content_title))
                .setContentText(getResources().getString(R.string.content_text))
                .setSubText(getResources().getString(R.string.subtext))
                .setAutoCancel(true);
        /*
        Small Icon : Ikon ini yang akan muncul pada status bar (wajib ada).
        Large Icon : Ikon ini yang akan muncul di sebelah kiri dari text notifikasi.
        Content Intent : Intent ini sebagai action jika notifikasi ditekan.
        Content Title : Judul dari notifikasi (wajib ada).
        Content Text : Text yang akan muncul di bawah judul notifikasi (wajib ada).
        Subtext : Text ini yang akan muncul di bawah content text.
        Auto Cancel : Digunakan untuk menghapus notifikasi setelah ditekan.
        */

        //Untuk android Oreo ke atas perlu menambahkan notification channel

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
