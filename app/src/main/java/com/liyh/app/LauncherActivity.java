package com.liyh.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liyh.annotation.BindView;
import com.liyh.annotation.OnClick;
import com.liyh.butterknifelibrary.ButterKnife;
import com.liyh.pluginlibrary.PluginManager;
import com.liyh.pluginlibrary.ProxyActivity;

public class LauncherActivity extends Activity {
    @BindView(R.id.bt_jump)
    public Button btJump;
    @BindView(R.id.tv_text)
    public TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luacher);
        PluginManager.getInstance().init(this);
        ButterKnife.bind(this);
        tvText.setText("加载三方apk");
        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apkPath = Utils.copyAssetAndWrite(LauncherActivity.this, "plugin.apk");
                Log.i("==>", "onClick:apkPath " + apkPath);
                PluginManager.getInstance().loadApk(apkPath);
            }
        });
    }



    @OnClick(R.id.bt_jump)
    public void onViewClicked(View view) {
        Intent intent = new Intent(LauncherActivity.this, ProxyActivity.class);
        intent.putExtra("className","com.liyh.aidlclient.MainActivity");
        startActivity(intent);
    }
}
