package cn.adolphc.mywifi.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.BaseAdapter;

public class BytesBitmap {
	
	 public static Bitmap getBitmapOfBytes(byte[] data) {  
         return BitmapFactory.decodeByteArray(data, 0, data.length);  
     }  
   
     public static byte[] getBytesOfPNG(Bitmap bitmap) {  
         ByteArrayOutputStream baops = new ByteArrayOutputStream();
         bitmap.compress(CompressFormat.PNG, 100, baops);  
         return baops.toByteArray();  
     }
     
     public static byte[] getBytesOfJPEG(Bitmap bitmap) {  
         ByteArrayOutputStream baops = new ByteArrayOutputStream();
         bitmap.compress(CompressFormat.JPEG, 100, baops);  
         return baops.toByteArray();  
     } 
     
    
}












