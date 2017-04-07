package com.beijingtest.bjt.model;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beijingtest.bjt.MyApplication;
import com.beijingtest.bjt.entity.CustomList;
import com.beijingtest.bjt.util.ExceptionHandler;
import com.beijingtest.bjt.util.GlobalConsts;

/**
 * Created by dev on 2017/3/30.
 */

public class SalesModel {

    private RequestQueue queue;

    public SalesModel () {
        queue = MyApplication.getContext().getQueue();
    }

    public void getCustomList(final AsyncCallback callback) {
        String url = GlobalConsts.GET_CUSTOM_LIST;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    CustomList customList = JSON.parseObject(response, CustomList.class);
                    if(null != customList) {
                        callback.onSuccess(customList);
                    } else {
                        callback.onError("获取数据失败");
                    }
                } catch (Exception e) {
                    ExceptionHandler.handleException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(volleyError);
            }
        });
        queue.add(request);
    }
}
