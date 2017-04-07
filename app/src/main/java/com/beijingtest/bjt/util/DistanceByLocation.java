package com.beijingtest.bjt.util;

public class DistanceByLocation {
	 private static double EARTH_RADIUS = 6378.137; 
	   
	    private static double rad(double d) { 
	        return d * Math.PI / 180.0; 
	    }
	     
	    /**
	     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
	     * 参数为 Double 类型
	     * @param longitude1 用户1经度
	     * @param latitude1 用户1纬度
	     * @param longitude2 用户2经度
	     * @param latitude2 用户2纬度
	     * @return
	     */
	    public static Double getDistance(Double longitude1, Double latitude1, 
	    		Double longitude2, Double latitude2) {
	         
	        double radLat1 = rad(longitude1);
	        double radLat2 = rad(longitude2);
	        double difference = radLat1 - radLat2;
	        double mdifference = rad(latitude1) - rad(latitude2);
	        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
	                + Math.cos(radLat1) * Math.cos(radLat2)
	                * Math.pow(Math.sin(mdifference / 2), 2)));
	        distance = distance * EARTH_RADIUS;
	        distance = Math.round(distance*1000)/1000.0;
	        return distance;
	    }
}
