package com.beijingtest.bjt.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.beijingtest.bjt.MyApplication;
import com.beijingtest.bjt.entity.Custom;
import com.beijingtest.bjt.entity.CustomList;
import com.beijingtest.bjt.util.ExceptionHandler;
import com.beijingtest.bjt.util.GlobalConsts;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    //org.json.JSONArray jsonArray = new org.json.JSONArray(response);
                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getInt("code") == 404) {
//                        callback.onError("session失效");
//                        //return;
//                    }
                    String list = jsonObject.getString("rows");
                    System.out.println("--------------------");
                    System.out.println(list);
                    System.out.println("--------------------");
                    List<Custom> customList = JSON.parseArray(list, Custom.class);
                    System.out.println(customList.toString());
                    if (null != customList) {
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String cookie = "ASP.NET_SessionId=" + MyApplication.getContext().getSessionId();
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie", cookie);
                return headers;
            }
        };
        queue.add(request);
    }
}
