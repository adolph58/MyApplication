package com.artech.demo.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.artech.demo.MyApplication;

/**
 * Created by dev on 2017/4/5.
 */

public class BDLocationUtils {
    public static String addr;
    public static String getLocation() {
        //String addr = "";
        LocationClient locationClient = new LocationClient(MyApplication.getContext());
       MyBdLocationListener myBdLocationListener = new MyBdLocationListener();
        locationClient.registerLocationListener(myBdLocationListener);
        // 设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        // 设置坐标类型
        option.setCoorType("bd09ll");
        // 每隔1秒得1次坐标
        // 少于1000，只得1次坐标
        option.setScanSpan(1000);

        locationClient.setLocOption(option);
        // 开始定位
        locationClient.start();
        return addr;
    }

    // 实现类
    static class MyBdLocationListener implements BDLocationListener {
        // 框架定位成功后，框架来调,也是回调
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            try {
                addr = bdLocation.getAddrStr();

                // 在原生模拟器上得到的是4.9E-324
                // 定位失败，得到的也是4.9E-324
                // 经度
                //double longitude = bdLocation.getLongitude();
                // 纬度
                //double latitude = bdLocation.getLatitude();
                //Log.i("定位成功", "纬度=" + latitude + ",经度" + longitude);

                // 模拟一个地址
//                if (longitude == 4.9E-324) {
//                    longitude = 116.392932;
//                    latitude = 39.916814;
//                }

                // 移动地图
                // 地图的显示级别4-17
                //LatLng currentLocation = new LatLng(latitude, longitude);
                // status状态
                //MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                        //.newLatLngZoom(currentLocation, 10);
               // baiduMap.animateMapStatus(mapStatusUpdate);

                // 在地图上显示图片
                //BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                       // .fromResource(R.drawable.icon_marka);
                //MarkerOptions markerOptions = new MarkerOptions();
                // 设置标记在地图上的位置
               // markerOptions.position(currentLocation);
                //markerOptions.icon(bitmapDescriptor);
                // 把标记添加到地图上
                //baiduMap.addOverlay(markerOptions);
            } catch (Exception e) {
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

}
