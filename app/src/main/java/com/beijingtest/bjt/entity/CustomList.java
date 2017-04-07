package com.beijingtest.bjt.entity;

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
}
