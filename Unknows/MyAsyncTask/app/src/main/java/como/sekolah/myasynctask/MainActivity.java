package como.sekolah.myasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements MyAsyncCallback {

    TextView tvStatus;
    TextView tvDesc;

    final static String INPUT_STRING = "Halo Ini Demo AsyncTask!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        tvDesc = findViewById(R.id.tv_desc);

//        menjalankan Asyntask dengan inputang berupa string
        DemoAsync demoAsync = new DemoAsync(this);
        demoAsync.execute(INPUT_STRING);
    }


//    implement mthod from MyAsyncCallback class
    @Override
    public void onPreExecute() {
        tvStatus.setText(R.string.status_pre);
        tvDesc.setText(INPUT_STRING);
    }

    @Override
    public void onPostExecute(String result) {
        tvStatus.setText(R.string.status_post);
        if (result != null){
            tvDesc.setText(result);
        }
    }



    private static class DemoAsync extends AsyncTask<String, Void, String> {
        static final String LOG_ASYNC= "DemoAsync";
        WeakReference<MyAsyncCallback> myListener;
        DemoAsync(MyAsyncCallback myListener){
            this.myListener = new WeakReference<>(myListener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_ASYNC, "status : onPreExecute");

            MyAsyncCallback myListener = this.myListener.get();
            if(myListener != null){
                myListener.onPreExecute();
            }
        }


        //        implement method from AsyncTask
        @Override
        protected String doInBackground(String... params) {
            Log.d(LOG_ASYNC, "status : onPreExecute");
            String output = null;
            try {
                String input = params[0];
                output = input + " Selamat Belajar!!";
                Thread.sleep(5000);
            } catch (Exception e){
                Log.d(LOG_ASYNC, e.getMessage());
            }
            return output;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(LOG_ASYNC, "status : onPostExecute");

            MyAsyncCallback myListener = this.myListener.get();
            if (myListener != null){
                myListener.onPostExecute(result);
            }
        }
    }
}