package cn.adolphc.mywifi.activity;

import android.os.Bundle;

import org.xutils.x;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.util.ExceptionHandler;

public class AccountActivity extends BaseActivity {

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        try {
            setContentView(R.layout.activity_account);
            x.view().inject(this);
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
