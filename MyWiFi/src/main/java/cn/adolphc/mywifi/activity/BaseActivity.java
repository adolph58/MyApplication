package cn.adolphc.mywifi.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.adolphc.mywifi.MyApplication;
import cn.adolphc.mywifi.util.NetworkUtil;

public abstract class BaseActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication myApplication = (MyApplication) getApplication();
		// this:调方这个方法的当前对象
		// 当前对象可以是这个类BaseActivity的对象,也可以是子类MainActivity,MainFragmentActivity的对象

		// this在运行时，指向了不同的子类的对象，叫多态
		// 面向父类编程，面向接口编程
		myApplication.activityList.add(this);
		NetworkUtil.checkNetworkState(this);

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
	
	/**
     * 配置横竖屏自适应
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        int orientation = newConfig.orientation;
        super.onConfigurationChanged(newConfig);
    }
	
	@Override
	protected void onDestroy() {
		MyApplication myApplication = (MyApplication) getApplication();
		myApplication.activityList.remove(this);
		super.onDestroy();
	}
}
