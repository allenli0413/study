package com.liyh.app.glide;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liyh.app.glide.core.Glide;
import com.liyh.app.glide.listener.RequestListener;
import com.liyh.app.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout scrooll_line;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrooll_line = findViewById(R.id.scrooll_line);
        //最简单的请求图片方式
        verifyStoragePermissions(this);

    }


    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void single(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrooll_line.addView(imageView);
        Glide.with(this).load("http://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558636227808&di=ed707a6cfc4e0d91ec3c5efede62d1fb&imgtype=0&src=http%3A%2F%2Fpic.rmb.bdstatic.com%2F036fdb11f3172092151f30ccdd2925b0.jpeg%40wm_2%2Ct_55m%2B5a625Y%2B3L%2BaXoOWOmOiAgeWktOiwiOWoseS5kA%3D%3D%2Cfc_ffffff%2Cff_U2ltSGVp%2Csz_20%2Cx_13%2Cy_13").loading(R.drawable.loading).listener(new RequestListener() {
            @Override
            public void onfailure() {
            }

            @Override
            public boolean onSuccess(Bitmap resource) {
                Toast.makeText(MainActivity.this, "自定义处理图片（比如设置圆角）"
                        , Toast.LENGTH_SHORT).show();
                return false;
            }
        }).into(imageView);


    }

    public void more(View view) {

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrooll_line.addView(imageView);
        ImageView imageView1 = new ImageView(this);
        imageView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrooll_line.addView(imageView1);
        ImageView imageView2 = new ImageView(this);
        imageView2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrooll_line.addView(imageView2);
        ImageView imageView3 = new ImageView(this);
        imageView3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrooll_line.addView(imageView3);
        ImageView imageView4 = new ImageView(this);
        imageView4.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrooll_line.addView(imageView4);
        ImageView imageView5 = new ImageView(this);
        imageView5.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrooll_line.addView(imageView5);
        //设置占位图片
        Glide.with(this)
                .loading(R.drawable.loading).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558636227808&di=fddedfa4cb36347fac50b83974f6c31e&imgtype=0&src=http%3A%2F%2Fdingyue.ws.126.net%2F2019%2F03%2F14%2Facaeffcfb5f544658938085c58beaa5f.jpeg").loading(R.drawable.loading)
                .into(imageView);
        Glide.with(this)
                .loading(R.drawable.loading).load("").loading(R.drawable.loading)
                .into(imageView1);
        Glide.with(this)
                .loading(R.drawable.loading).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558636380221&di=25ae0a2a95dd3bd31897e3c79e495742&imgtype=0&src=http%3A%2F%2Fimg2.ph.126.net%2F9RMeHxnUJs0L38ZUtZH45g%3D%3D%2F6630466332840109571.jpg").loading(R.drawable.loading)
                .into(imageView2);
        Glide.with(this)
                .loading(R.drawable.loading).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558636380221&di=f6d36181f6c13c9864ab80ce8eb9f39b&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7e3e8d175f280bbe4e735ffed52924e43278af6231f9f-YjVcZM_fw658").loading(R.drawable.loading)
                .into(imageView3);
        Glide.with(this)
                .loading(R.drawable.loading).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558636453768&di=3af543bd05c1b98b0c76f9116099f538&imgtype=0&src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F66%2F9b%2F91%2F669b91f7dab80b4a37601debb8e9fd9d.jpg").loading(R.drawable.loading)
                .into(imageView4);
        Glide.with(this)
                .loading(R.drawable.loading).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558636380221&di=e2298714209f20a41a8d7fbf16b1e497&imgtype=0&src=http%3A%2F%2Fimg.ph.126.net%2FT3xfDm6NpNk_MZfNrsDjYA%3D%3D%2F3071736420860264427.jpg").loading(R.drawable.loading)
                .into(imageView5);


    }


}
