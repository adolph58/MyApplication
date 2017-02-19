package cn.adolphc.mywifi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.util.ExceptionHandler;

public class SettingActivity extends BaseActivity {
    @ViewInject(R.id.tv_setting_suggest)
    private TextView suggest;
    @ViewInject(R.id.tv_setting_estimate)
    private TextView estimate;
    @ViewInject(R.id.tv_setting_checkout)
    private TextView checkout;
    @ViewInject(R.id.tv_setting_about)
    private TextView about;
    @ViewInject(R.id.tv_setting_exit)
    private TextView exit;
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
            checkout.setOnClickListener(new View.OnClickListener() {
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

                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    @Override
    protected void loadData() {

    }
}
