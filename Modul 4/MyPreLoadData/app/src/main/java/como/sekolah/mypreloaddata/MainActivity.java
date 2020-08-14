package como.sekolah.mypreloaddata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import como.sekolah.mypreloaddata.adapter.MahasiswaAdapter;
import como.sekolah.mypreloaddata.database.MahasiswaHelper;
import como.sekolah.mypreloaddata.model.MahasiswaModel;
import como.sekolah.mypreloaddata.service.DataManagerService;

import static como.sekolah.mypreloaddata.service.DataManagerService.CANCEL_MESSAGE;
import static como.sekolah.mypreloaddata.service.DataManagerService.FAILED_MESSAGE;
import static como.sekolah.mypreloaddata.service.DataManagerService.PREPARATION_MESSAGE;
import static como.sekolah.mypreloaddata.service.DataManagerService.SUCCESS_MESSAGE;
import static como.sekolah.mypreloaddata.service.DataManagerService.UPDATE_MESSAGE;

public class MainActivity extends AppCompatActivity implements HandlerCallback{
    private ProgressBar progressBar;

    Messenger mBoundService;
    boolean mServiceBound;

    /*
    Service Connection adalah interface yang digunakan untuk menghubungkan antara boundservice dengan activity
    */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBoundService = new Messenger(service);
            mServiceBound = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);

        Intent mBoundServiceIntent = new Intent(MainActivity.this, DataManagerService.class);
        Messenger mActivityMessenger = new Messenger(new IncomingHandler(this));
        mBoundServiceIntent.putExtra(DataManagerService.ACTIVITY_HANDLER, mActivityMessenger);

        bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    @Override
    public void preparation() {
        Toast.makeText(this, "MEMULAI MEMUAT DATA", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateProgress(long progress) {
        Log.e("PROGRESS", "updateProgress: " + progress);
        progressBar.setProgress((int) progress);
    }

    @Override
    public void loadSuccess() {
        Toast.makeText(this, "BERHASIL", Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainActivity.this, MahasiswaActivity.class));
        finish();
    }

    @Override
    public void loadFailed() {
        Toast.makeText(this, "GAGAL", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadCancel() {
        finish();
    }

    private static class IncomingHandler extends Handler{

        WeakReference<HandlerCallback> weakCallback;

        IncomingHandler(HandlerCallback callback) {
            weakCallback =  new WeakReference<>(callback);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case PREPARATION_MESSAGE:
                    weakCallback.get().preparation();
                    break;
                case UPDATE_MESSAGE:
                    Bundle bundle = msg.getData();
                    long progress = bundle.getLong("KEY_PROGRESS");
                    weakCallback.get().updateProgress(progress);
                    break;
                case SUCCESS_MESSAGE:
                    weakCallback.get().loadSuccess();
                    break;
                case FAILED_MESSAGE:
                    weakCallback.get().loadFailed();
                    break;
                case CANCEL_MESSAGE:
                    weakCallback.get().loadCancel();
            }
            super.handleMessage(msg);
        }
    }
}

interface HandlerCallback{
    void preparation();

    void updateProgress(long progress);

    void loadSuccess();

    void loadFailed();

    void loadCancel();
}
