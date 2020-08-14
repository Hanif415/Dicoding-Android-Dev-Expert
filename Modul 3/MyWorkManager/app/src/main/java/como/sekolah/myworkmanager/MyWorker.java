package como.sekolah.myworkmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class MyWorker extends Worker {
//    constructor
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private static final String TAG = MyWorker.class.getSimpleName();
    private static final String APP_ID = "060f94916f88272d2c3376ff7c97df20";
    public static final String EXTRA_CITY = "city";
    private Result resultStatus;

//    method do Work yaitu method yang akan di eksekusi
    @NonNull
    @Override
    public Result doWork() {
        String dataCity = getInputData().getString(EXTRA_CITY);
        Result status = getCurrentWeaher(dataCity);
        return status;
    }

//    method untuk mengambil informasi dari API
    private Result getCurrentWeaher(final String city) {
        Log.d(TAG, "getCurrentWeather dimulai");
        SyncHttpClient client  = new SyncHttpClient();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + APP_ID;
        Log.d(TAG, "getCurrentWeather: " + url);
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);

                    String currentWeather = responseObject.getJSONArray("weather").getJSONObject(0).getString("main");
                    String description = responseObject.getJSONArray("weather").getJSONObject(0).getString("description");
                    double tempKelvin = responseObject.getJSONObject("main").getDouble("temp");

                    double tempCelcius = tempKelvin - 273;
                    String temperature = new DecimalFormat("##.##").format(tempCelcius);

                    String title = "Current Weather in " + city;
                    String message = currentWeather + ", " + description + " with " +temperature + " celcius";

                    showNotification(title, message);

                    Log.d(TAG, "onSuccsess: selesai");
                    resultStatus = Result.success();

                } catch (JSONException e) {
                    showNotification("Get Current Weather Not Success",e.getMessage());
                    Log.d(TAG, "onSuccess: Gagal.....");
                    resultStatus = Result.failure();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showNotification("Get Current Weather Failed", error.getMessage());
                Log.d(TAG, "onFailure: Gagal.....");
                resultStatus = Result.failure();
            }
        });

        return resultStatus;
    }

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_01";
    private static final String CHANNEL_NAME = "dicoding_channel";

//    method untuk menampilkan notifikasi
    private void showNotification(String title, String description) {

        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notification.setChannelId(CHANNEL_ID);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification.build());
        }
    }
}
