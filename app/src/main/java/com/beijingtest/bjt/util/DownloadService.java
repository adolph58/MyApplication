package com.beijingtest.bjt.util;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.RemoteViews;

import com.beijingtest.bjt.R;
import com.beijingtest.bjt.activity.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dev on 2017/4/12.
 */

public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";
    //private NotificationCompat.Builder builder;
    private Notification notification;
    private NotificationManager notificationManager;
    private RemoteViews remoteViews;
    private int pro;
    public DownloadService() {
        super("com.beijingtest.bjt.util.DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        //获得下载文件的url
        String downloadUrl = bundle.getString("url");
        //设置下载后的保存路径，保存在SD卡根目录的Download文件夹
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "京诚检测.apk");
                 // 设置Notification
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification(R.mipmap.icon_download, "版本更新下载", System.currentTimeMillis());
        Intent intentNotifi = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentNotifi, 0);
        notification.contentIntent = pendingIntent;
        // 加载Notification的布局文件
        remoteViews = new RemoteViews(getPackageName(), R.layout.download_file);
        // 设置下载进度条
        remoteViews.setProgressBar(R.id.pb_download, 100, 0, false);
        notification.contentView = remoteViews;
        notificationManager.notify(0, notification);
        // 开始下载
        downloadFile(downloadUrl, file);
        // 移除通知栏
        notificationManager.cancel(0);
        // 广播出去，由广播接收器来处理下载完成的文件
        Intent sendIntent = new Intent("com.test.downloadComplete");
        // 把下载好的文件的保存地址加进Intent
        sendIntent.putExtra("downloadFile", file.getPath());
        sendBroadcast(sendIntent);
    }

    private int fileLength;
    private int downloadLength;
    private void downloadFile(String downloadUrl, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            LogUtil.i(TAG,"找不到保存下载文件的目录");
            ExceptionHandler.handleException(e);
        }
        InputStream is = null;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(3000);
            fileLength = Integer.valueOf(httpURLConnection.getHeaderField("Content-Length"));
            is = httpURLConnection.getInputStream();
            //拿到服务器返回的响应码
            int hand = httpURLConnection.getResponseCode();
            if (hand == 200) {
                //开始检查下载进度
                handler.post(run);
                //建立一个byte数组作为缓冲区，等下把读取到的数据存储在这个数组
                byte[] buffer = new byte[8192];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    downloadLength = downloadLength + len;
                }
            } else {
                LogUtil.i(TAG,"服务器返回码" + hand);
            }
        } catch (MalformedURLException e) {
            ExceptionHandler.handleException(e);
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        //移除定时器
        handler.removeCallbacks(run);
        super.onDestroy();

    }
    //定时器，每隔一段时间检查下载进度，然后更新Notification上的ProgressBar
    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            remoteViews.setProgressBar(R.id.pb_download, 100, downloadLength*100 / fileLength, false);
            notification.contentView = remoteViews;
            notificationManager.notify(0, notification);
            handler.postDelayed(run, 1000);
        }
    };
}
