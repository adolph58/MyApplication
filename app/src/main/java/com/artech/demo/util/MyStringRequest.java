package com.artech.demo.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.artech.demo.MyApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 2017/4/12.
 */

public class MyStringRequest extends StringRequest {
    public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        String cookie = "ASP.NET_SessionId=" + MyApplication.getContext().getSessionId();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", cookie);
        return headers;
    }
}
