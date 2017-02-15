package cn.adolphc.mywifi.model;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.adolphc.mywifi.MyApplication;
import cn.adolphc.mywifi.entity.User;
import cn.adolphc.mywifi.entity.WifiInfo;
import cn.adolphc.mywifi.util.ExceptionHandler;
import cn.adolphc.mywifi.util.GlobalConsts;

/**
 * Created by Jaunty on 2017/1/17.
 */

public class WifiModel {

    private RequestQueue queue;

    public WifiModel() {
        queue = MyApplication.getContext().getQueue();
    }

    public void getWifiPwd(final String username, final String password, final AsyncCallback callback) {
        String url = GlobalConsts.URL_WIFI_GET_PWD;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("code")==GlobalConsts.RESPONSE_CODE_SUCCESS) {
                        String wifiPwd = jsonObject.getString("data");
                        callback.onSuccess(wifiPwd);
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


    public void claim(final WifiInfo wifiInfo, final AsyncCallback callback) {
        String url = GlobalConsts.URL_WIFI_CLAIM;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("code")==GlobalConsts.RESPONSE_CODE_SUCCESS) {
                        callback.onSuccess("认领成功！");
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
                map.put("wifiInfo", JSON.toJSONString(wifiInfo));
                return map;
            }
        };
        queue.add(request);
    }

}
