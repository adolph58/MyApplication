package com.artech.demo.entity;

import java.io.Serializable;

/**
 * Created by dev on 2017/4/6.
 */

public class MyLocation implements Serializable{
    //纬度
    private double latitude;
    //经度
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "MyLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
