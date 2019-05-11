package com.artech.demo.entity;

import com.artech.demo.util.FilePathUtils;
import com.artech.demo.util.GlobalConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 2017/3/30.
 */

public class Custom implements Serializable{
    //客户id
    private int ID;
    //客户名称
    private String CustomName;
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //地址
    private String Address;

    public Custom() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "Custom{" +
                "ID=" + ID +
                ", CustomName='" + CustomName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", Address='" + Address + '\'' +
                '}';
    }

    /**
     * 从序列化的文件中读取用户信息
     *
     * @return
     */
    public static List<Custom> getCustomListByFile() {
        try {
            File file = new File(FilePathUtils.getDiskFilesDir(), GlobalConsts.CUSTOM_LIST_NAME);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            List<Custom> customList = (List<Custom>)ois.readObject();
            if (customList == null) {
                return new ArrayList<Custom>();
            }
            return customList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<Custom>();
    }

    /**
     * 持久化到文件中 下次打开应用用户信息依然存在
     */
    public static void saveCustomList(List<Custom> customList) {
        try {
            File file = new File(FilePathUtils.getDiskFilesDir(), GlobalConsts.CUSTOM_LIST_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(customList);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
