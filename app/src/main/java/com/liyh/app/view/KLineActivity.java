package com.liyh.app.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liyh.app.R;

/**
 * @author allenlee
 */
public class KLineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline);
        KLineView klv_data_chart = findViewById(R.id.klv_data_chart);
        klv_data_chart.setOnPointClickListener(new KLineView.OnPointClickListener() {
            @Override
            public void onClick(String xValue, float yValue) {
                Toast.makeText(KLineActivity.this, "x = " + xValue + ", y = " + yValue, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
