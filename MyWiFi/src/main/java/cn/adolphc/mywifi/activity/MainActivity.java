package cn.adolphc.mywifi.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.fragment.HotspotFragment;
import cn.adolphc.mywifi.fragment.MineFragment;
import cn.adolphc.mywifi.fragment.WifiFragment;
import cn.adolphc.mywifi.util.ExceptionHandler;

public class MainActivity extends BaseActivity {

    private WifiFragment wifiFragment;
    private HotspotFragment hotspotFragment;
    private MineFragment mineFragment;
    private Fragment currentShowFragment;
    @ViewInject(R.id.ib_main_wifi)
    private ImageButton ibWiFi;
    @ViewInject(R.id.ib_main_favorite)
    private ImageButton ibFavorite;
    @ViewInject(R.id.ib_main_mine)
    private ImageButton ibMine;
    @ViewInject(R.id.tv_main_title)
    private TextView tvTitle;

    @Override
    protected void initVariables() {
        // 默认显示HomeFragment
        wifiFragment = new WifiFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开始事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, wifiFragment);
        transaction.show(wifiFragment);
        transaction.commit();
        currentShowFragment = wifiFragment;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        try {
            setContentView(R.layout.activity_main);
            x.view().inject(this);
            InnerOnClickListener listener = new InnerOnClickListener();
            ibWiFi.setOnClickListener(listener);
            ibFavorite.setOnClickListener(listener);
            ibMine.setOnClickListener(listener);

            // 设置按钮状态
            ibWiFi.setSelected(true);
            tvTitle.setText("我的WiFi");
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
                case R.id.ib_main_wifi:
                    ibWiFi.setSelected(true);
                    ibFavorite.setSelected(false);
                    ibMine.setSelected(false);
                    tvTitle.setText("WiFi列表");
                    if (wifiFragment == null) {
                        wifiFragment = new WifiFragment();
                    }
                    showFragment(wifiFragment);
                    break;
                case R.id.ib_main_favorite:
                    ibFavorite.setSelected(true);
                    ibWiFi.setSelected(false);
                    ibMine.setSelected(false);
                    tvTitle.setText("热点管理");
                    if (hotspotFragment == null) {
                        hotspotFragment = new HotspotFragment();
                    }
                    showFragment(hotspotFragment);
                    break;
                case R.id.ib_main_mine:
                    ibMine.setSelected(true);
                    ibWiFi.setSelected(false);
                    ibFavorite.setSelected(false);
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
        // currentShowFragment = fragment;
        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_container, fragment);
        }
        transaction.hide(currentShowFragment);
        transaction.show(fragment);
        transaction.commit();
        currentShowFragment = fragment;
    }

    /**
     * 按下返回键返回列表
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 设置toolbar
     */
    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

}
