package com.artech.demo.util;

import android.os.Environment;

import com.artech.demo.MyApplication;

/**
 * Created by Jaunty on 2017/3/22.
 */

public class FilePathUtils {

    public static String getDiskFilesDir() {
        String filesPath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            filesPath = MyApplication.getContext().getExternalFilesDir(null).getPath();
        } else {
            filesPath = MyApplication.getContext().getFilesDir().getPath();
        }
        return filesPath;
    }

    public static String getDiskCacheDir() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = MyApplication.getContext().getExternalCacheDir().getPath();
        } else {
            cachePath = MyApplication.getContext().getCacheDir().getPath();
        }
        return cachePath;
    }


}
