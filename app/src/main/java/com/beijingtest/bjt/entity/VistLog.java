package com.beijingtest.bjt.entity;

import java.io.Serializable;

/**
 * Created by dev on 2017/3/30.
 */

public class VistLog implements Serializable{
    private int id;
    //销售人员id
    private int userId;
    //客户名称
    private String CustomName;
    //拜访内容
    private String vistContent;
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //地址
    String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCustomName() {
        return CustomName;
    }

    public void setCustomName(String customName) {
        CustomName = customName;
    }

    public String getVistContent() {
        return vistContent;
    }

    public void setVistContent(String vistContent) {
        this.vistContent = vistContent;
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
}
