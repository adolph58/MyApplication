package cn.adolphc.mywifi.util;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import cn.adolphc.mywifi.MyApplication;
import cn.adolphc.mywifi.activity.MainActivity;

//出了异常，没加try
//不要加断点
public class CrashHandler implements UncaughtExceptionHandler{
	private MyApplication myApplication;
	
	public CrashHandler(MyApplication myApplication) {
		this.myApplication = myApplication;
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.i("CrashHandler", "出异常了" + ex.getMessage());
		// 1,把异常信息转成字符串，联网发送
		ExceptionHandler.handleException(ex);
		// 2,用toast显示自动重启
		// 原先出异常的主线程要死了
		new Thread(){
			public void run() {
				// toast.show用到了队列，必须由looper从队列取消息，
				// 工作线程是没有looper,主线程是有looper

				// 自己创建looper
				Looper.prepare();
				Toast.makeText(myApplication, "抱歉，程序将重启", Toast.LENGTH_LONG)
				.show();
				Looper.loop();
			}
		}.start();
		
		// 3,过一会重启
		Intent intent = new Intent(myApplication,MainActivity.class);
		// myApplication.startActivity(intent);
		PendingIntent pendingIntent = PendingIntent.getActivity(myApplication,
				100, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		// AlarmManager定时器
		AlarmManager alarmManager = (AlarmManager) myApplication.getSystemService(Context.ALARM_SERVICE);
		// RTC:锁屏了不执行任务
		alarmManager.set(AlarmManager.RTC, System.currentTimeMillis()+2000, pendingIntent);
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			ExceptionHandler.handleException(e);
		}
		myApplication.finish();
	}

}
