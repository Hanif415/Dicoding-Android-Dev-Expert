package como.sekolah.smslistenerapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class DownloadService extends IntentService {

    public static final String TAG = "DownloadService";

    public DownloadService() {
        super("DownloadService");
    }

//    di sini kan menjalan kan intent service yang akan melakukan proses pengunduhan yangpada kenyataannya hanya melakukan sleep saja
//    dan kemudian membroadcast sebuah intentFilter dengan action yang telah di tentukan, ACTION_DOWNLOAD_STATUS
//    ketika proses pengunduhan selesai akan ada membroadcast sebuah event kemudian direspon di minActivity
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Download Service dijalankan");
        if (intent != null){
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent notifyFinishIntent = new Intent(MainActivity.ACTION_DOWNLOAD_STATUS);
            sendBroadcast(notifyFinishIntent);
        }
    }

}
