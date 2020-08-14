package como.sekolah.mywidgets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int JOB_ID = 100;
    private static final int SCHEDLE_OF_PERIOD = 86000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btn_start);
        Button btnStop = findViewById(R.id.btn_stop);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

//    method 1
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_start:
                startJob();
                break;
            case R.id.btn_stop:
                cancleJob();
                break;
            default:
                break;
        }
    }

//    method 2
    private void startJob() {
        ComponentName mServiceComponent = new ComponentName(this, UpdateWidgetService.class);

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            15 menit
            builder.setPeriodic(900000L);
        } else {
//            3 menit
            builder.setPeriodic(SCHEDLE_OF_PERIOD);
        }

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
        }

        Toast.makeText(this, "Job Service Started", Toast.LENGTH_SHORT).show();
    }

//    method 3
    private void cancleJob() {
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (tm != null) {
            tm.cancel(JOB_ID);
        }
        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show();
    }
}
