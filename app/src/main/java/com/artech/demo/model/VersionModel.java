package com.artech.demo.model;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.artech.demo.MyApplication;
import com.artech.demo.entity.AppVersion;
import com.artech.demo.util.ExceptionHandler;
import com.artech.demo.util.GlobalConsts;

import org.json.JSONObject;

/**
 * Created by dev on 2017/4/1.
 */

public class VersionModel {
    private RequestQueue queue;

    public VersionModel () {
        queue = MyApplication.getContext().getQueue();
    }

    public void getAppVersion(final AsyncCallback callback) {
        String url = GlobalConsts.URL_APP_CHECK_VERSION;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    AppVersion appVersion = new AppVersion();
                    appVersion.setVersionNO(jsonObject.getString("VersionNO"));
                    appVersion.setRemark(jsonObject.getString("Remark"));
                    appVersion.setFileName(jsonObject.getString("fileName"));
//                    System.out.println(appVersion.getVersionNO());
//                    System.out.println(appVersion.getRemark());
                    callback.onSuccess(appVersion);
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
