package com.beijingtest.bjt.util;

import com.beijingtest.bjt.entity.Custom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 搜索工具类
 * Created by dev on 2017/4/10.
 */

public class SearchUtils {

    /**
     * 搜索客户列表
     * @param customList 要搜索的列表
     * @param searchContent 要搜索的内容
     * @return 返回搜索结果
     */
    public static List<Custom> searchCustom(List<Custom> customList, String searchContent) {
        List<Custom> searchCustomList = new ArrayList<>();
        Iterator<Custom> it = customList.iterator();
        while(it.hasNext()) {
            Custom custom = it.next();
            String customName = custom.getCustomName();
            if(customName.contains(searchContent)) {
                searchCustomList.add(custom);
            }
        }
        return searchCustomList;
    }
}
