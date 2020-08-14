package como.sekolah.mymediaplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class MediaService extends Service implements MediaPlayerCallback{
    final String TAG = MediaService.class.getSimpleName();
    private MediaPlayer mMediaPlayer = null;
    private boolean isReady;

    public final static String ACTION_CREATE = "com.dicoding.picodiploma.mysound.mediaservice.create";
    public final static String ACTION_DESTROY = "com.dicoding.picodiploma.mysound.mediaservice.destroy";

    public final static int PLAY = 0;
    public final static int STOP = 1;

//    method 1
    public MediaService() {
    }

//    method 6
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action != null) {
            switch (action) {
                case ACTION_CREATE:
                    if (mMediaPlayer == null) {
                        init();
                    }
                    break;
                case ACTION_DESTROY:
                    if (!mMediaPlayer.isPlaying()) {
                        stopSelf();
                    }
                    break;
                default:
                    break;
            }
        }
        Log.d(TAG, "onStartCommand: ");
        return flags;
    }

//    method 2
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mMessager.getBinder();
    }

//    method 3
    @Override
    public void onPlay() {
        if (!isReady) {
            mMediaPlayer.prepareAsync();
        } else {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                showNotif();
            } else {
                mMediaPlayer.start();
            }
        }
    }

//    method 4
    @Override
    public void onStop() {
        if (mMediaPlayer.isPlaying() || isReady) {
            mMediaPlayer.stop();
            isReady = false;
            stopNotif();
        }
    }

//    method 5
    private void init() {
        mMediaPlayer = new MediaPlayer();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mMediaPlayer.setAudioAttributes(attributes);
        } else {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(R.raw.sound2);
        try {
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isReady = true;
                mMediaPlayer.start();
            }
        });

        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });
    }

//    method 7
    private final Messenger mMessager = new Messenger(new IncomingHandler(this));
    static class IncomingHandler extends Handler {

        private WeakReference<MediaPlayerCallback> mediaPlayerCallbackWeakReference;

        IncomingHandler(MediaPlayerCallback playerCallback) {
            this.mediaPlayerCallbackWeakReference = new WeakReference<>(playerCallback);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case PLAY:
                    mediaPlayerCallbackWeakReference.get().onPlay();
                    break;
                case STOP:
                    mediaPlayerCallbackWeakReference.get().onStop();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

//    method 8
    void showNotif() {
        Intent notificationInent = new Intent(this, MainActivity.class);
        notificationInent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationInent,0);

        String CHANNEL_DEFAULT_IMPORTANCE = "Channel_Test";
        int ONGOING_NOTIFICATION_ID = 1;

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                .setContentTitle("TES1")
                .setContentText("TES2")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setTicker("TES3")
                .build();

        createChannel(CHANNEL_DEFAULT_IMPORTANCE);

        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

//    method 9
    void createChannel(String CHANNEL_ID) {
        NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Battery", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(false);
            channel.setSound(null, null);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

//    method 10
    void stopNotif() {
        stopForeground(false);
    }
}
