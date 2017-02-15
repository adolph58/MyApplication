package cn.adolphc.mywifi.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.Toast;

public class Tools {
    
    /**
     * Toast 提示
     * @param context
     * @param msg
     */
    public static void showToast(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    
	/**
	 * 得当前版本号
	 * 
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public static String getCurrentVersion(Context context) throws NameNotFoundException {
		//管理包
		PackageManager packageManager = context.getPackageManager();
		String packageName = context.getPackageName();
		PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
		String versionName = packageInfo.versionName;
		return versionName;
	}
}
