package cn.adolphc.mywifi.entity;

import java.io.Serializable;

public class WifiInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private String ssid;
	private String bssid;
	private String wifiPwd;
	private double unitPrice;
	private String wifiType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	public String getWifiPwd() {
		return wifiPwd;
	}
	public void setWifiPwd(String wifiPwd) {
		this.wifiPwd = wifiPwd;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getWifiType() {
		return wifiType;
	}
	public void setWifiType(String wifiType) {
		this.wifiType = wifiType;
	}
	@Override
	public String toString() {
		return "WifiInfo [id=" + id + ", userId=" + userId + ", ssid=" + ssid
				+ ", bssid=" + bssid + ", wifiPwd=" + wifiPwd + ", unitPrice="
				+ unitPrice + ", wifiType=" + wifiType + "]";
	}	

}
