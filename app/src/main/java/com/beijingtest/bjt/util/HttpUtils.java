package com.beijingtest.bjt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 发送http请求的工具类
 */
public class HttpUtils {

	/**
	 * 把输入流按照utf-8编码解析为字符串 返回
	 * @return
	 */
	public static String isToString(InputStream is) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = reader.readLine()) != null){
			sb.append(line);
		}
		return sb.toString();
	}

	/**
	 * 发送post请求 获取响应输入流
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static InputStream postInputStream(String path, Map<String, String> params)throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();
		//遍历map，把键值对拼接为参数字符串
		StringBuilder sb = new StringBuilder();
		Set<String> keys = params.keySet();
		Iterator<String> ite = keys.iterator();
		while(ite.hasNext()){
			String key = ite.next();
			String val = params.get(key);
			sb.append(key+"="+val+"&");
		}
		//把最后一个&字符移除
		sb.deleteCharAt(sb.length()-1);
		String param = sb.toString();
		os.write(param.getBytes("utf-8"));
		os.flush();
		InputStream is = conn.getInputStream();
		return is;
	}

	/**
	 * 通过url路径 发送HttpGet请求  获取响应数据
	 * 如果含有参数，则需要在path后用?拼接
	 * @param path
	 * @return 输入流
	 */
	public static InputStream getInputStream(String path)throws Exception {
		//1. URL
		URL url = new URL(path);
		//2. HttpURLConenction
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//3. getInputStream
		conn.setRequestMethod("GET");
		InputStream is = conn.getInputStream();
		return is;
	}

}
