package com.artech.demo.util;

import android.graphics.Bitmap;

public interface BitmapCallback {
	/**
	 * 当图片下载成功的回调
	 * @param bitmap
	 */
	void onBitmapLoaded(Bitmap bitmap);
}
