package como.sekolah.myunittesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainView{

    EditText edtWidth, edtHeight, edtLength;
    TextView tvresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtWidth = findViewById(R.id.edt_width);
        edtHeight = findViewById(R.id.edt_height);
        edtLength = findViewById(R.id.edt_length);
        Button btnCalculate = findViewById(R.id.btn_calculate);
        tvresult = findViewById(R.id.tv_result);

        final MainPresenter presenter = new MainPresenter(this);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String length = edtLength.getText().toString().trim();
                String width = edtWidth.getText().toString().trim();
                String height = edtHeight.getText().toString().trim();

                boolean isEmptyField = false;

                if (TextUtils.isEmpty(length)){
                    isEmptyField = true;
                    edtLength.setError("Field ini tidak boleh kosong");
                }

                if (TextUtils.isEmpty(width)){
                    isEmptyField = true;
                    edtWidth.setError("Field ini tidak boleh kosong");
                }

                if (TextUtils.isEmpty(height)){
                    isEmptyField = true;
                    edtHeight.setError("Field ini tidak boleh kosong");
                }

                if (!isEmptyField){
                    double l = Double.parseDouble(length);
                    double w = Double.parseDouble(width);
                    double h = Double.parseDouble(height);

                    presenter.calculateVolume(l, w, h);
                }
            }
        });
    }

    public void showVolume(MainModel model){
        tvresult.setText(String.valueOf(model.getVolume()));
    }
}
