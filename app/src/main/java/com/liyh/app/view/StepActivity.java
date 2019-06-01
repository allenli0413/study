package com.liyh.app.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liyh.app.R;

public class StepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        MyCircleView circleView = findViewById(R.id.circleView);
    }
}
