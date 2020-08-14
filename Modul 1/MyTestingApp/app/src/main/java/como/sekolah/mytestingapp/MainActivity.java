package como.sekolah.mytestingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSetValue;
    private TextView tvText;
    private ArrayList<String> names;
    private DelayAsync delayAsync;
    ImageView imgPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = findViewById(R.id.tv_text);
        btnSetValue = findViewById(R.id.btn_set_value);

//        NullPointerException karena kita memberi method setOncick pada button yang belum di inisialisasi
        btnSetValue.setOnClickListener(this);

        names = new ArrayList<>();
        names.add("Narenda Wicaksono");
        names.add("Kevin");
        names.add("yoza");

//        Out of memory karena ukuran gambar yang dimuatt terlalu besar
        Glide.with(this).load(R.drawable.fronalpstock_big).into(imgPreview);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_set_value){
            StringBuilder name = new StringBuilder();
//            IndexOutOfBoundsException karena collextion dimulai dari 0
            for (int i = 0; i < names.size(); i++){
                name.append(names.get(i)).append("\n");
            }
            tvText.setText(name.toString());

            delayAsync = new DelayAsync();
            delayAsync.execute();
        }
    }

    private static class DelayAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(3000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            Log.d("DelayAsync", "Done");
        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
            Log.d("DelayAsync", "Cancelled");
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (delayAsync != null){
            if (delayAsync.getStatus().equals(AsyncTask.Status.RUNNING)){
                delayAsync.cancel(true);
            }
        }
    }

}
