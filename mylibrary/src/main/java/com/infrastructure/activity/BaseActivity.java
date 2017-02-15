package com.infrastructure.activity;

import android.app.Activity;
import android.os.Bundle;

import com.infrastructure.net.RequestManager;

/**
 * Created by Jaunty on 2016/12/10.
 */

public abstract class BaseActivity extends Activity {

    //请求列表管理器
    protected RequestManager requestManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestManager = new RequestManager(this);
        super.onCreate(savedInstanceState);

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    /**
     * 初始化变量，包括 Intent 带的数据和 Activity 内的变量
     */
    protected abstract void initVariables();

    /**
     * 加载 layout 布局文件，初始化控件，为控件挂上事件方法。
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 调用 MobileAPI 获取数据。
     */
    protected abstract void loadData();

    @Override
    protected void onDestroy() {
        //在activity销毁的时候同时设置停止请求，停止线程请求回调
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
         //在activity停止的时候同时设置停止请求，停止线程请求回调
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
        super.onPause();
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}
