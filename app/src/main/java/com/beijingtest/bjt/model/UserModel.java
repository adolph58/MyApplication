package com.beijingtest.bjt.model;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beijingtest.bjt.MyApplication;
import com.beijingtest.bjt.entity.User;
import com.beijingtest.bjt.util.ExceptionHandler;
import com.beijingtest.bjt.util.GlobalConsts;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaunty on 2017/1/17.
 */

public class UserModel {

    private RequestQueue queue;

    public UserModel () {
        queue = MyApplication.getContext().getQueue();
    }

    public void regist(final String username, final String password, final AsyncCallback callback) {
        String url = GlobalConsts.URL_USER_REGIST;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("code")==GlobalConsts.RESPONSE_CODE_SUCCESS) {
                        callback.onSuccess("注册成功！");
                    } else {
                        callback.onError(jsonObject.getString("msg"));
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                return map;
            }
        };
        queue.add(request);
    }


    public void login(final String username, final String password, final AsyncCallback callback) {
        String url = GlobalConsts.URL_USER_LOGIN;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("code")==GlobalConsts.RESPONSE_CODE_SUCCESS) {
                        User user = JSON.parseObject(jsonObject.getString("data"), User.class);
                        User.saveUser(user);
                        callback.onSuccess(user);
                    } else {
                        callback.onError(jsonObject.getString("msg"));
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                return map;
            }
        };
        queue.add(request);
    }

    public void update(final User user, final AsyncCallback callback) {
        String url = GlobalConsts.URL_USER_UPDATE_USER_INFO;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("code")==GlobalConsts.RESPONSE_CODE_SUCCESS) {
                        User user = JSON.parseObject(jsonObject.getString("data"), User.class);
                        User.saveUser(user);
                        callback.onSuccess(user);
                    } else {
                        callback.onError(jsonObject.getString("msg"));
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user", com.alibaba.fastjson.JSONObject.toJSONString(user));
                return map;
            }
        };
        queue.add(request);
    }

}
