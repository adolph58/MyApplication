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
import java.util.List;

/**
 * Created by dev on 2017/4/6.
 */

public class CustomList implements Serializable{
    private List<Custom> customList;

    public List<Custom> getCustomList() {
        return customList;
    }

    public void setCustomList(List<Custom> customList) {
        this.customList = customList;
    }

    /**
     * 从序列化的文件中读取用户信息
     *
     * @return
     */
    public static CustomList getCustomListByFile() {
        try {
            File file = new File(FilePathUtils.getDiskFilesDir(), GlobalConsts.CUSTOM_LIST_NAME);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            CustomList customList = (CustomList)ois.readObject();
            if (customList == null) {
                return new CustomList();
            }
            return customList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CustomList();
    }

    /**
     * 持久化到文件中 下次打开应用用户信息依然存在
     */
    public static void saveCustomList(CustomList customList) {
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
