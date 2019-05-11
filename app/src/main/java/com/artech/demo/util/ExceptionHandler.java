package com.artech.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.util.Log;
import com.artech.demo.MyApplication;

public class ExceptionHandler {
	
	public static void handleException (Throwable e) {
		if(MyApplication.isRelease) {
			// 程序运行在真机上
			// 把异常信息 转成字符串
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			// 异常信息先打印到printWriter,再打到stingWriter
			e.printStackTrace(printWriter);
			String errorInfo = stringWriter.toString();
			
			Log.i("errorInfo", errorInfo);
			// 再联网发送
		} else {
			// 是开发阶段，异常信息 显示在logcat
			e.printStackTrace();
		}
	}
}
