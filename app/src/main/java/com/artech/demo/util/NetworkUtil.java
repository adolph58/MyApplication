
package com.artech.demo.util;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import com.artech.demo.MyApplication;


public class NetworkUtil {
    
    /**
     * 获得用户网络类型
     * @param context
     */
    public static void getNetworkType(Context context) {
        try {
            // 判断用户是打开网络还是关闭网络
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                LogUtil.i("ChangedReceiver", "关闭了");
                MyApplication.net_type_is_colse=true;
                MyApplication.net_type_is_mobile=false;
                MyApplication.net_type_is_wifi=false;

            } else {
                MyApplication.net_type_is_colse=false;

                // 判断网络类型
                // 包月流量3G,超过1M是1块钱。
                // 看电影 4G 24*60*60*100=
                NetworkInfo mobileNetwork = manager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobileNetwork != null && mobileNetwork.isConnected()) {
                    LogUtil.i("ChangedReceiver", "打开了移动网络");
                    MyApplication.net_type_is_mobile=true;

                }

                NetworkInfo wifiNetwork = manager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (wifiNetwork != null && wifiNetwork.isConnected()) {
                    LogUtil.i("ChangedReceiver", "打开了wifi网络");
                    MyApplication.net_type_is_wifi=true;
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }
    
    public static void checkNetworkState(final Activity activity) {
        try {
            ConnectivityManager manager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            //判断有没有网
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if(activeNetworkInfo==null) {
                // 没网显示dialog
                Builder dialog = new Builder(activity);
                dialog.setMessage("亲，现在没网");
                dialog.setPositiveButton("打开", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            // 打开按钮调出系统自带的管理网络的activity
                            Intent intent = new Intent(
                                    Settings.ACTION_WIRELESS_SETTINGS);
                            activity.startActivity(intent);
                        } catch (Exception e) {
                            ExceptionHandler.handleException(e);
                        }
                    }
                });
                dialog.setNegativeButton("取消", null);
                dialog.show();
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }
}
