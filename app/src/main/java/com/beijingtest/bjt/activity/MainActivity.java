package com.beijingtest.bjt.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.beijingtest.bjt.R;
import com.beijingtest.bjt.download.DownLoadUtils;
import com.beijingtest.bjt.download.DownloadApk;
import com.beijingtest.bjt.entity.AppVersion;
import com.beijingtest.bjt.fragment.HomeFragment;
import com.beijingtest.bjt.fragment.LogListFragment;
import com.beijingtest.bjt.fragment.MineFragment;
import com.beijingtest.bjt.model.AsyncCallback;
import com.beijingtest.bjt.model.VersionModel;
import com.beijingtest.bjt.util.ExceptionHandler;
import com.beijingtest.bjt.util.GlobalConsts;
import com.beijingtest.bjt.util.Tools;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends BaseActivity {

    public static MainActivity instance;
    private HomeFragment homeFragment;
    private LogListFragment logListFragment;
    private MineFragment mineFragment;
    private Fragment currentShowFragment;
    @ViewInject(R.id.ib_main_home)
    private ImageButton ibHome;
    @ViewInject(R.id.ib_main_log)
    private ImageButton ibLog;
    @ViewInject(R.id.ib_main_mine)
    private ImageButton ibMine;
    @ViewInject(R.id.tv_main_title)
    private TextView tvTitle;

    @Override
    protected void initVariables() {
        instance = this;
        // 默认显示HomeFragment
        homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开始事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, homeFragment);
        transaction.show(homeFragment);
        transaction.commit();
        currentShowFragment = homeFragment;

        //1.注册下载广播接收器
        DownloadApk.registerBroadcast(this);
        //2.删除已存在的Apk
        DownloadApk.removeFile(this);

        //检查新版本
        checkAppVersion();

        //创建本地数据库
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SQLiteUtils data = new SQLiteUtils();
//               data.onCreate();
//            }
//        }).start();

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        try {
            setContentView(R.layout.activity_main);
            x.view().inject(this);
            InnerOnClickListener listener = new InnerOnClickListener();
            ibHome.setOnClickListener(listener);
            ibLog.setOnClickListener(listener);
            ibMine.setOnClickListener(listener);

            // 设置按钮状态
            ibHome.setSelected(true);
            tvTitle.setText("销售管理");
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    @Override
    protected void loadData() {

    }

    /**
     * 监听内部类
     *
     * @author Android
     */
    class InnerOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_main_home:
                    ibHome.setSelected(true);
                    ibLog.setSelected(false);
                    ibMine.setSelected(false);
                    tvTitle.setText("销售管理");
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                    }
                    showFragment(homeFragment);
                    break;
                case R.id.ib_main_log:
                    ibLog.setSelected(true);
                    ibHome.setSelected(false);
                    ibMine.setSelected(false);
                    tvTitle.setText("拜访日志列表");
                    if (logListFragment == null) {
                        logListFragment = new LogListFragment();
                    }
                    showFragment(logListFragment);
                    break;
                case R.id.ib_main_mine:
                    ibMine.setSelected(true);
                    ibLog.setSelected(false);
                    ibHome.setSelected(false);
                    tvTitle.setText("个人中心");
                    if (mineFragment == null) {
                        mineFragment = new MineFragment();
                    }
                    showFragment(mineFragment);
                    break;
            }
        }
    }

    /**
     * 显示 Fragment
     */
    public void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_container, fragment);
        }
        transaction.hide(currentShowFragment);
        transaction.show(fragment);
        transaction.commit();
        currentShowFragment = fragment;
    }

    /**
     * 按下返回键退出
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {

        //4.反注册广播接收器
        DownloadApk.unregisterBroadcast(this);
        super.onDestroy();
    }

    /**
     * 检查新版本
     */
    private void checkAppVersion() {
        try {
            final double currentVersion = Double.parseDouble(Tools.getCurrentVersion(MainActivity.this));
            System.out.println(currentVersion);
            VersionModel model = new VersionModel();
            model.getAppVersion(new AsyncCallback() {
                @Override
                public void onSuccess(Object success) {
                    final AppVersion appVersion = (AppVersion) success;
                    double serverVersion = Double.parseDouble(appVersion.getVersionNO());
                    if (serverVersion > currentVersion ) {
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("有新版本：" + appVersion.getVersionNO());
                        dialog.setMessage(appVersion.getRemark());
                        dialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //如果手机已经启动下载程序，执行downloadApk。否则跳转到设置界面
                                if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DownloadApk.downloadApk(getApplicationContext(), GlobalConsts.URL_APP_UPDATE + appVersion.getFileName(), "京诚检测更新", "bjt");
                                        }
                                    }).start();

                                } else {
                                    DownLoadUtils.getInstance(getApplicationContext()).skipToDownloadManager();
                                }
                            }
                        });
                        dialog.setNegativeButton("取消", null);
                        dialog.show();
                    } else {

                    }
                }

                @Override
                public void onError(Object error) {

                }
            });
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置toolbar
     */
//    public void initActionBar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//    }

}
