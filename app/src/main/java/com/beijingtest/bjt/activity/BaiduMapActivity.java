package com.beijingtest.bjt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.beijingtest.bjt.R;
import com.beijingtest.bjt.entity.VistLog;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class BaiduMapActivity extends BaseActivity {
    // 定位
    LocationClient locationClient;
    @ViewInject(R.id.bmapView)
    MapView mapView;
    @ViewInject(R.id.iv_bdmap_back)
    ImageView ivBack;
    BaiduMap baiduMap;
    VistLog vistLog;
    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        vistLog = (VistLog) intent.getSerializableExtra("vistLog");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_baidu_map);
        x.view().inject(this);
        //locationClient = new LocationClient(getApplicationContext());
        baiduMap = mapView.getMap();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //定位
        toLocation();
        // 告诉框架接口指向哪个实现类
        //MyBdLocationListener myBdLocationListener = new MyBdLocationListener();
        //locationClient.registerLocationListener(myBdLocationListener);
        // 设置定位参数
        //LocationClientOption option = new LocationClientOption();
        //option.setOpenGps(true);
        // 设置坐标类型
        //option.setCoorType("bd09ll");

        //可选，设置是否需要地址信息，默认不需要
        //option.setIsNeedAddress(true);

        // 每隔1秒得1次坐标
        // 少于1000，只得1次坐标
        //option.setScanSpan(1000);

        //locationClient.setLocOption(option);
        // 开始定位
        //locationClient.start();
    }

    @Override
    protected void loadData() {

    }

    /**
     * 定位
     */
    private void toLocation() {
        // 移动地图
        // 地图的显示级别4-17 数字越小，比例越大

        String[] strings = vistLog.getLongitudePoint().split(",");

        double latitude = Double.parseDouble(strings[1]);
        double longitude = Double.parseDouble(strings[0]);
        LatLng currentLocation = new LatLng(latitude, longitude);
        // status状态
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                .newLatLngZoom(currentLocation, 17);
        baiduMap.animateMapStatus(mapStatusUpdate);

        // 在地图上显示图片
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_mark);
        MarkerOptions markerOptions = new MarkerOptions();
        // 设置标记在地图上的位置
        markerOptions.position(currentLocation);
        markerOptions.icon(bitmapDescriptor);
        // 把标记添加到地图上
        baiduMap.addOverlay(markerOptions);
    }

    // 实现类
//    class MyBdLocationListener implements BDLocationListener {
//        // 框架定位成功后，框架来调,也是回调
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            try {
//                // 在原生模拟器上得到的是4.9E-324
//                // 定位失败，得到的也是4.9E-324
//                // 经度
//                double longitude = bdLocation.getLongitude();
//                // 纬度
//                double latitude = bdLocation.getLatitude();
//                LogUtil.i("定位成功", "纬度=" + latitude + ",经度" + longitude);
//                LogUtil.i("地址", bdLocation.getAddrStr());
//                Tools.showToast(bdLocation.getAddrStr());
//                // 模拟一个地址
//                if (longitude == 4.9E-324) {
//                    longitude = 116.392932;
//                    latitude = 39.916814;
//                }
//
//                // 移动地图
//                // 地图的显示级别4-17
//                LatLng currentLocation = new LatLng(latitude, longitude);
//                // status状态
//                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
//                        .newLatLngZoom(currentLocation, 10);
//                baiduMap.animateMapStatus(mapStatusUpdate);
//
//                // 在地图上显示图片
//                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
//                        .fromResource(R.mipmap.icon_mark);
//                MarkerOptions markerOptions = new MarkerOptions();
//                // 设置标记在地图上的位置
//                markerOptions.position(currentLocation);
//                markerOptions.icon(bitmapDescriptor);
//                // 把标记添加到地图上
//                baiduMap.addOverlay(markerOptions);
//            } catch (Exception e) {
//            }
//
//        }
//
//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//
//        }
//    }
}
