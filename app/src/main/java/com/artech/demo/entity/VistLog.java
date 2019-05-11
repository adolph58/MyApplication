package com.artech.demo.entity;

import java.io.Serializable;

/**
 * Created by dev on 2017/3/30.
 */

public class VistLog implements Serializable{
    private int ID;
    //销售人员id
    private int CreateEmpID;
    //客户ID
    private int CustomerID;
    //客户名称
    private String CustomName;
    //拜访内容
    private String Remark;
    //经纬度
    private String LongitudePoint;
    //纬度
    private String latitude;
    //经度
    private String longitude;
    //地址
    private String Adress;
    //客户负责人姓名
    private String VisitName;
    //拜访时间
    private String CreateDate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCreateEmpID() {
        return CreateEmpID;
    }

    public void setCreateEmpID(int createEmpID) {
        CreateEmpID = createEmpID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getCustomName() {
        return CustomName;
    }

    public void setCustomName(String customName) {
        CustomName = customName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getLongitudePoint() {
        return LongitudePoint;
    }

    public void setLongitudePoint(String longitudePoint) {
        LongitudePoint = longitudePoint;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getVisitName() {
        return VisitName;
    }

    public void setVisitName(String visitName) {
        VisitName = visitName;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }
}
