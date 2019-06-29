package com.liyh.aidlclient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.liyh.aidlclient.explosion.ExplosionField;
import com.liyh.aidlclient.explosion.FallingParticleFactory;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ExplosionField explosionField = new ExplosionField(this, new FallingParticleFactory());
        LinearLayout llContainer = findViewById(R.id.ll_container);
        explosionField.addListener(llContainer);
    }
}
