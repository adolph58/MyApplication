package com.artech.demo.model;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.artech.demo.MyApplication;
import com.artech.demo.entity.Custom;
import com.artech.demo.entity.VistLog;
import com.artech.demo.util.ExceptionHandler;
import com.artech.demo.util.GlobalConsts;
import com.artech.demo.util.MyStringRequest;

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

    /**
     * 获取客户列表
     * @param callback
     */
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

    /**
     * 获取日志列表
     * @param callback
     */
    public void getVistLog(final AsyncCallback callback) {
        String url = GlobalConsts.GET_VIST_LOG;
        MyStringRequest request = new MyStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    //org.json.JSONArray jsonArray = new org.json.JSONArray(response);
                    JSONObject jsonObject = new JSONObject(response);
                    //System.out.println(vistLog);
                    if (jsonObject.getInt("code") == 404) {
                        callback.onError("session失效");
                    }else if(jsonObject.getInt("code") == 200){
                        String list = jsonObject.getString("data");
                        System.out.println("--------------------");
                        System.out.println(list);
                        System.out.println("--------------------");
                        JSONObject json = new JSONObject(list);
                        String res = json.getString("rows");
                        List<VistLog> vistLogList = JSON.parseArray(res, VistLog.class);
                        System.out.println(vistLogList.toString());
                        if (null != vistLogList) {
                            callback.onSuccess(vistLogList);
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

    /**
     * 写入日志
     * @param callback
     */
    public void writeVistLog(final VistLog vistLog, final AsyncCallback callback) {
        String url = GlobalConsts.GET_VIST_LOG;
        MyStringRequest request = new MyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    //org.json.JSONArray jsonArray = new org.json.JSONArray(response);
                    JSONObject jsonObject = new JSONObject(response);
                    //System.out.println(vistLog);
                    if (jsonObject.getInt("code") == 404) {
                        callback.onError("session失效");
                    }else if(jsonObject.getInt("code") == 200){
                       callback.onSuccess("保存成功");
                    }else if(jsonObject.getInt("code") == 201) {
                        callback.onError("保存失败");
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("t", "ins");
                map.put("isPhone", "1");
                map.put("CustomerID", String.valueOf(vistLog.getCustomerID()));
                map.put("LongitudePoint", vistLog.getLongitudePoint());
                map.put("Adress", vistLog.getAdress());
                map.put("Remark", vistLog.getRemark());
                return map;
            }
        };
        queue.add(request);
    }
}
