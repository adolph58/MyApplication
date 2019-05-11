package com.artech.demo.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.artech.demo.MyApplication;

import java.util.List;


public class LocationUtils {
	/**
	 * 获取地理位置
	 * @return Location
	 */
	public static Location getLocation() {
		LocationManager locationManager= (LocationManager) MyApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
		String provider = "";
		//获取所有可用的位置提供器
		List<String> providerList = locationManager.getProviders(true);
		if(providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if(providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
		} else {
			return null;
		}		
		Location location = locationManager.getLastKnownLocation(provider);		
		return location;		
	}

}
