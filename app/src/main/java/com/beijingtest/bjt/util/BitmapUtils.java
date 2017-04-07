package com.beijingtest.bjt.util;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;

public class BitmapUtils {

	/**
	 * 异步加载图片 通过资源路径
	 * @param path  url资源路径
	 * @param scale  压缩比例
	 * @param callback
	 */
	public static void loadBitmap(final String path,final int scale, final BitmapCallback callback) {
		AsyncTask<String, String, Bitmap> task = new AsyncTask<String, String, Bitmap>() {
			@Override
			protected Bitmap doInBackground(String... params) {
				try {
					//再从文件缓存中读
					String filename = path.substring(path.lastIndexOf("/")+1);
					File file = new File(FilePathUtils.getDiskCacheDir(), "images/" + filename );
					Bitmap b=loadBitmap(file, scale);
					if(b!=null){
						return b;
					}
					InputStream is = HttpUtils.getInputStream(path);
					//下载图片并且不压缩
					b = BitmapFactory.decodeStream(is);
					//下载下来的原始尺寸图片  存入文件中
					save(b, file);
					//从文件中读取bitmap  按照scale的比例进行压缩
					b = loadBitmap(file, scale);
					return b;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			//主线程中执行回调方法
			@Override
			protected void onPostExecute(Bitmap result) {
				callback.onBitmapLoaded(result);
			}
		};
		task.execute();
	}

	/**
	 * 异步获取一张模糊的图片
	 * @param bitmap  源图片
	 * @param callback
	 */
	public static void loadBlurBitmap(final Bitmap bitmap, final BitmapCallback callback){
		AsyncTask<String, String, Bitmap> task = new AsyncTask<String, String, Bitmap>(){
			protected Bitmap doInBackground(String... params) {
				Bitmap b = createBlurBitmap(bitmap, 5);
				return b;
			}
			protected void onPostExecute(Bitmap result) {
				callback.onBitmapLoaded(result);
			}
		};
		task.execute();
	}

	/**
	 * 从文件中加载一个Bitmap对象
	 * @param file
	 * @param scale 压缩比例
	 * scale==0  :  不压缩
	 * @return
	 */
	public static Bitmap loadBitmap(File file, int scale){
		if(!file.exists()){
			return null;
		}
		if(scale==0){
			Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
			return b;
		}else{
			Options opts = new Options();
			opts.inSampleSize = scale;
			Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
			return b;
		}
	}

	/**
	 * 用jpeg格式存储bitmap图像
	 * @param bitmap
	 * @param file
	 * @throws IOException
	 */
	public static void save(Bitmap bitmap,File file) throws IOException{
		if(!file.getParentFile().exists()) {//如果父目录不存在
			file.getParentFile().mkdirs();//创建父目录
		}
		FileOutputStream fos = new FileOutputStream(file);
		bitmap.compress(CompressFormat.JPEG, 100, fos);//第一个参数是格式，第二个参数值图片质量，取1-100
	}

	/**
	 * 压缩图片 获取相应尺寸的图片
	 * @param is  输入流
	 * @param width  目标宽度
	 * @param height  目标高度
	 * @return
	 */
	public static Bitmap loadBitmap(InputStream is, int width, int height) throws IOException {
		//把is解析  把数据t读取到bye[]中
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//把输入流中的数据读取到bos中
		byte[] buffer = new byte[1024*8];
		int length = 0;
		while((length=is.read(buffer)) != -1) {
			bos.write(buffer, 0, length);
			bos.flush();
		}
		//该byte[] 表述的是一个图片的完整信息
		byte[] bytes = bos.toByteArray();
		//获取图片的原始尺寸
		Options opts = new Options();
		//仅仅加载边界属性
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
		//根据原始尺寸与width和height计算压缩比例
		int w = opts.outWidth/width;
		int h = opts.outHeight/height;
		int rate = w>h?w:h;
		//执行压缩
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = rate;
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
		return bitmap;
	}

	/**
	 * 通过一个网络路径  获取一张图片 Bitmap
	 * @param path
	 * @param callback
	 */
	public static void loadBitmap(final String path,final BitmapCallback callback) {
		AsyncTask<String, String, Bitmap> task = new AsyncTask<String, String, Bitmap>() {
			@Override
			protected Bitmap doInBackground(String... params) {
				try {
					//先从内存缓存中读
					//再从文件缓存中读
					String filename = path.substring(path.lastIndexOf("/")+1);
					File file = new File(FilePathUtils.getDiskFilesDir(), "images/" + filename);
					Bitmap b = loadBitmap(file,0);
					if(b!=null){
						return b;
					}
					InputStream is = HttpUtils.getInputStream(path);
					b = BitmapFactory.decodeStream(is);
					//下载下来的图片  存入文件中
					save(b, file);
					return b;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			//主线程中执行回调方法
			@Override
			protected void onPostExecute(Bitmap result) {
				callback.onBitmapLoaded(result);
			}

		};
		task.execute();
	}

	/**
	 * 传递bitmap 传递模糊半径 返回一个被模糊的bitmap
	 * 比较耗时
	 * @param sentBitmap
	 * @param radius
	 * @return
	 */
	public static Bitmap createBlurBitmap(Bitmap sentBitmap, int radius) {
		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
		if (radius < 1) {
			return (null);
		}
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);
		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;
		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];
		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);

		}
		yw = yi = 0;
		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;
		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];

				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];

				}

			}
			stackpointer = radius;
			for (x = 0; x < w; x++) {
				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];
				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;
				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];
				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];
				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);

				}
				p = pix[yw + vmin[x]];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];
				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;
				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];
				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];
				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];
				yi++;

			}
			yw += w;

		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;
				sir = stack[i + radius];
				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];
				rbs = r1 - Math.abs(i);
				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];

				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];

				}
				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
						| (dv[gsum] << 8) | dv[bsum];
				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;
				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];
				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];
				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;

				}
				p = x + vmin[y];
				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];
				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];
				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;
				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];
				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];
				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];
				yi += w;
			}
		}
		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		return (bitmap);
	}

}
