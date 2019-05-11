package com.artech.demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.artech.demo.config.SystemParams;
import com.artech.demo.entity.User;
import com.artech.demo.util.CrashHandler;
import com.artech.demo.util.NetworkUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application{
    //全局context,需要context的地方可以直接调用MyApplication.getContext()方法
    private static MyApplication context;
    //Volley网络请求全局变量
    private RequestQueue mQueue;
    //网络状态改变的全局变量
    public static boolean net_type_is_colse=false;
    public static boolean net_type_is_wifi=false;
    public static boolean net_type_is_mobile=false;
    private static final int NOTIFICATION_ID = 0;
    
    //当前用户
    private User user;
    //session_id
    private String sessionId;
    
    // true 表示软件发布了 false 表示在开发阶段
    public static boolean isRelease = false; 
    public static List<Activity> activityList = new ArrayList<Activity>();
    // 放apkServerUrl
    //public static String apkServerUrl;
    public void finish() {
        for(Activity activity:activityList) {
            activity.finish();
        }
        Process.killProcess(Process.myPid());
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mQueue = Volley.newRequestQueue(this);
        CrashHandler crashHandler=new CrashHandler(this);
        //回调：通过框架调我们写的实现类的对象
        //告诉框架调那个对象
        Thread.currentThread().setDefaultUncaughtExceptionHandler(crashHandler);
        
        //初始化 xutils
        x.Ext.init(this);

        //初始化百度地图
        SDKInitializer.initialize(this);
       
        //获取网络类型
        NetworkUtil.getNetworkType(this);

        //初始化系统配置
        SystemParams.init(this);

        //创建本地数据库
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SQLiteUtils data = new SQLiteUtils();
//                data.onCreate();
//            }
//        }).start();



    }
    
    public static String getCurProcessName(Context context) {

        int pid = Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 获取context
     * @return
     */
    public static MyApplication getContext() {
        return context;
    }
    
    /**
     * 获得mQueue
     * @return
     */
    public RequestQueue getQueue() {        
        return  mQueue; 
    }
    
    /**
     * 保存当前用户信息
     * @param
     */
    public void saveSessionId(String sessionId, String userId){
        this.sessionId = sessionId;
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("session_id", sessionId);
        editor.putString("userid", userId);
        editor.commit();
    }

    public void saveUserInfo(String username, String password) {
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    public String getSessionId(){
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String sessionId = pref.getString("session_id", "");
        return sessionId;
    }

    public String getUsername() {
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String username = pref.getString("username", "");
        return username;
    }

    public String getPassword() {
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String password = pref.getString("password", "");
        return password;
    }

    public String getUserId() {
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String userId = pref.getString("userid", "");
        return userId;
    }

    public void clearUser() {
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}
