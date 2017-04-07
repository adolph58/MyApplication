package com.beijingtest.bjt.util;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import com.beijingtest.bjt.MyApplication;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

/**
 * 重写Request请求
 */
public class CommonRequest extends StringRequest{

	public static String JSESSIONID=null;

	public CommonRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}

	public CommonRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
		super(url, listener, errorListener);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();

		if(headers!=null || headers.equals(Collections.emptyMap())){
			headers = new HashMap<String, String>();
		}
		//如果有session则传递sessionid
		if(JSESSIONID!=null) {
			headers.put("Cookie", JSESSIONID);
		}
		//判断sharepreference中是否存有cart信息  有的话则一起发送数据
		SharedPreferences pref = MyApplication.getContext().getSharedPreferences("cart", Context.MODE_PRIVATE);
		String cart=pref.getString("cart", null);
		if(cart!=null){
			String cookie=headers.get("Cookie");
			headers.put("Cookie", cookie==null ? cart:cookie+", cart="+cart);
		}
		return headers;
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		Map<String, String> headers = response.headers;
		String sessionid=headers.get("Set-Cookie");
		if(sessionid!=null){
			JSESSIONID=sessionid.split(";")[0];
		}
		String parsed;
		try {
			parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
	}
}
