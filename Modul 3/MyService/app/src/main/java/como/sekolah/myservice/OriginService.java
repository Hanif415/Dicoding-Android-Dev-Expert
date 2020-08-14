package como.sekolah.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.lang.ref.WeakReference;

//langkah 2 : implement interface dan implement method preAsync dan postAsync
public class OriginService extends Service implements DummyAsyncCallback {

    static final String TAG = OriginService.class.getSimpleName();


    public OriginService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        //langkah 4 : inisialisasi dan jalankan AsyncTask
        DummyAsync dummyAsync = new DummyAsync(this);
        dummyAsync.execute();

//        START_STICKY BERFUNGSI untuk apabila aplikasi dimatikan karena kekurangan memori maka ia akan di ciptakan kembali ketik sudah ada memori yang tersedia dan onStartCommand akan di jalankan kembal
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    //langkah 5 : tambahkan aksi di callback
    @Override
    public void preAsync() {
        Log.d(TAG, "preAsync: Mulai.....");
    }

    @Override
    public void postAsync() {
        Log.d(TAG, "postAsync: Selesai.....");
        stopSelf();
    }


    //langkah 3 : buat AsyncTask dan WeakReference
    private static class DummyAsync extends AsyncTask<Void, Void, Void>{

        WeakReference<DummyAsyncCallback> callback;

        DummyAsync(DummyAsyncCallback callback){
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            callback.get().preAsync();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG,  "doInBackground: ");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: ");
            callback.get().postAsync();
        }
    }
}

//langkah 1 : buat interface
interface DummyAsyncCallback {
    void preAsync();
    void postAsync();
}
