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
import com.liyh.app.database.User;
import com.liyh.app.ioc.IocActivity;
import com.liyh.app.permission.PermissionActivity;
import com.liyh.app.view.CustomViewActivity;
import com.liyh.app.view.KLineActivity;
import com.liyh.app.view.StepActivity;
import com.liyh.butterknifelibrary.ButterKnife;
import com.liyh.databaselibrary.BaseDao;
import com.liyh.databaselibrary.DataDaoFactory;
import com.liyh.httplibrary.HttpCallback;
import com.liyh.httplibrary.HttpHelper;
import com.liyh.pluginlibrary.PluginManager;
import com.liyh.pluginlibrary.ProxyActivity;

import java.util.HashMap;
import java.util.Map;

public class LauncherActivity extends Activity {
    private static final String TAG = "LauncherActivity";
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
        findViewById(R.id.bt_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDao baseDao = DataDaoFactory.getInstance().getBaseDao(User.class);
                baseDao.query();
            }
        });

    }


    @OnClick(R.id.bt_jump)
    public void onViewClicked(View view) {
        Intent intent = new Intent(LauncherActivity.this, ProxyActivity.class);
        intent.putExtra("className", "com.liyh.aidlclient.MainActivity");
        startActivity(intent);
    }

    public void createAndInsert(View view) {
        BaseDao baseDao = DataDaoFactory.getInstance().getBaseDao(User.class);
        for (int i = 0; i < 10; i++) {

            User user = new User(i, "Allen Lee" + i, 150L + i);
            baseDao.insert(user);
        }
    }

    public void toB(View view) {
        startActivity(new Intent(this, BActivity.class));
    }

    public void toTreeView(View view) {
        startActivity(new Intent(this, CustomViewActivity.class));
    }

    public void toStep(View view) {
        startActivity(new Intent(this, StepActivity.class));
    }

    public void toKLine(View view) {
        startActivity(new Intent(this, KLineActivity.class));
    }

    public void toIoc(View view) {
        startActivity(new Intent(this, IocActivity.class));
    }

    private String url = "http://v.juhe.cn/historyWeather/citys";

    public void toNetwork(View view) {
        Map<String, Object> params = new HashMap<>();
//        params.put("province_id", 1);
//        params.put("key", "bb52107206585ab074f5e59a8c73875b");
        HttpHelper.getInstance().post(url, params, new HttpCallback<ResultBean>() {
            @Override
            protected void success(ResultBean resultBean) {
                Log.e(TAG, "success: " + resultBean.toString());
            }
        });
    }
    public void toPermission(View view) {
        startActivity(new Intent(this, PermissionActivity.class));
    }
    public void toNo(View view) {
//        startActivity(new Intent(this, IocActivity.class));
    }
}
