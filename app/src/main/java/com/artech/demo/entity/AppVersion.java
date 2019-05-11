package com.artech.demo.entity;

import java.io.Serializable;

/**
 * Created by dev on 2017/4/1.
 */

public class AppVersion implements Serializable{
    private String VersionNO;
    private String fileName;
    private String Remark;

    public String getVersionNO() {
        return VersionNO;
    }

    public void setVersionNO(String versionNO) {
        VersionNO = versionNO;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "VersionNO='" + VersionNO + '\'' +
                ", fileName='" + fileName + '\'' +
                ", Remark='" + Remark + '\'' +
                '}';
    }
}
