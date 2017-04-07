package com.beijingtest.bjt.entity;

import java.io.Serializable;

/**
 * Created by dev on 2017/3/30.
 */

public class Custom implements Serializable{
    //客户id
    private int id;
    //客户名称
    private String CustomName;
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //地址
    private String address;

    public Custom() {
    }

    public Custom(int id, String CustomName) {
        this.id = id;
        this.CustomName = CustomName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomName() {
        return CustomName;
    }

    public void setCustomName(String customName) {
        CustomName = customName;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Custom{" +
                "id=" + id +
                ", CustomName='" + CustomName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                '}';
    }
}
