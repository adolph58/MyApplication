package com.beijingtest.bjt.util;


public class TimeUtils {
	
	public static String calculateTime(Long time) {
//		Date date = new Date();
//		Long currentTime = date.getTime();
		long d = (System.currentTimeMillis()-time)/60/1000;
		String dis = "";
		if(d<60) {
			dis = d+"分钟前";
		} else if(d>60 && d<1440) {
			dis = d/60+"小时前";
		} else if(d>1440) {
			dis = d/1440+"天前";
		} 
		return dis;		
	}
}
