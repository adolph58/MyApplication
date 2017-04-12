package com.beijingtest.bjt.model;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.beijingtest.bjt.MyApplication;
import com.beijingtest.bjt.entity.Custom;
import com.beijingtest.bjt.util.ExceptionHandler;
import com.beijingtest.bjt.util.GlobalConsts;
import com.beijingtest.bjt.util.MyStringRequest;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by dev on 2017/3/30.
 */

public class SalesModel {

    private RequestQueue queue;

    public SalesModel() {
        queue = MyApplication.getContext().getQueue();
    }

    public void getCustomList(final AsyncCallback callback) {
        String url = GlobalConsts.GET_CUSTOM_LIST;
        MyStringRequest request = new MyStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    //org.json.JSONArray jsonArray = new org.json.JSONArray(response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("code") == 404) {
                        callback.onError("session失效");
                        //return;
                    }else if(jsonObject.getInt("code") == 200){
                        String list = jsonObject.getString("data");
                        System.out.println("--------------------");
                        System.out.println(list);
                        System.out.println("--------------------");
                        JSONObject json = new JSONObject(list);
                        String res = json.getString("rows");
                        List<Custom> customList = JSON.parseArray(res, Custom.class);
                        System.out.println(customList.toString());
                        if (null != customList) {
                            callback.onSuccess(customList);
                        } else {
                            callback.onError("获取数据失败");
                        }
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
