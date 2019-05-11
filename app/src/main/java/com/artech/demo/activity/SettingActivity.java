package com.artech.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artech.demo.MyApplication;
import com.artech.demo.R;
import com.artech.demo.util.ExceptionHandler;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class SettingActivity extends BaseActivity {
    @ViewInject(R.id.tv_setting_suggest)
    private TextView suggest;
    @ViewInject(R.id.tv_setting_estimate)
    private TextView estimate;
    @ViewInject(R.id.tv_setting_about)
    private TextView about;
    @ViewInject(R.id.tv_setting_exit)
    private TextView exit;
    @ViewInject(R.id.iv_setting_back)
    private ImageView ivBack;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        try {
            setContentView(R.layout.activity_setting);
            x.view().inject(this);
            suggest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            estimate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void logout() {
//        File file = new File(FilePathUtils.getDiskFilesDir() ,
//                GlobalConsts.USER_INFO_NAME);
//        file.delete();
        MyApplication.getContext().clearUser();
        MainActivity.instance.finish();
        Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
        startActivity(intent);
        SettingActivity.this.finish();
    }

    @Override
    protected void loadData() {

    }
}
