package cn.adolphc.mywifi.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.m.permission.MPermissions;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.adapter.WifiListAdapter;
import cn.adolphc.mywifi.ui.WifiPswDialog;
import cn.adolphc.mywifi.util.LogUtil;
import cn.adolphc.mywifi.util.WifiUtils;

public class WifiFragment extends Fragment{
	private String wifiPassword = null;
	private WifiUtils localWifiUtils;
	//private static final int REQUECT_CODE_LOCATION = 1;  //申请位置权限返回码
	@ViewInject(R.id.lv_wifi_list)
	private ListView wifiListView;
	@ViewInject(R.id.tv_wifi_linked)
	private TextView tvLinked;
	//private WifiManager wifiManager;  //声明管理对象
	//private WifiInfo wifiInfo;  //Wifi信息
	private WifiListAdapter adapter;
	private List<ScanResult> scanResultList;  //扫描出来的网络连接列表
	private List<WifiConfiguration> wifiConfigList;  //网络配置列表
	public static final String TAG = "WifiFragment";
	//public static final int SCAN_RESULT_LIST = 1;
	/*private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SCAN_RESULT_LIST:
						setAdapter();
					break;
			}
		}
	};*/

	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi, container,false);
		x.view().inject(this,view);
		//MPermissions.requestPermissions(getActivity(), REQUECT_CODE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);
		//wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);  //获取Wifi服务
		//wifiInfo = wifiManager.getConnectionInfo();  //得到连接信息
		//检查获取位置权限
		/*if (ContextCompat.checkSelfPermission(getActivity(),
				Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {
			//ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},REQUECT_CODE_LOCATION);
			MPermissions.requestPermissions(this, REQUECT_CODE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);
		} else {
			loadData();
		}*/

        //setAdapter();
		localWifiUtils = new WifiUtils(getActivity());
		loadList();
		setListener();
        return view;
    }



	private void loadList() {
		localWifiUtils.WifiOpen();
		localWifiUtils.WifiStartScan();
		//0正在关闭,1WIFi不可用,2正在打开,3可用,4状态不可知
		while(localWifiUtils.WifiCheckState() != WifiManager.WIFI_STATE_ENABLED){//等待Wifi开启
			LogUtil.i("WifiState",String.valueOf(localWifiUtils.WifiCheckState()));
		}
		try {
			Thread.sleep(3000);//休眠3s，不休眠则会在程序首次开启WIFI时候，处理getScanResults结果，wifiResultList.size()发生异常
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		scanResultList = localWifiUtils.getScanResults();
		localWifiUtils.getConfiguration();
		setAdapter();
	}

	/**
	 * 加载数据,4.0以后必须在工作线程扫描
	 */
	/*private void loadData() {
		new Thread() {
			@Override
			public void run() {
				openWifi();
				wifiManager.startScan();
				scanResultList = wifiManager.getScanResults(); // 扫描返回结果列表
				handler.sendEmptyMessage(SCAN_RESULT_LIST);
				wifiConfigList = wifiManager.getConfiguredNetworks(); // 扫描配置列表
			}
		}.start();
	}*/

	/**
	 * 设置监听
	 */
	private void setListener() {
		//InnerOnClickListener listener = new InnerOnClickListener();
		ListOnItemClickListener wifiListListener = new ListOnItemClickListener();
		wifiListView.setOnItemClickListener(wifiListListener);
	}

	/**
	 * 内部监听类
	 */
	/*class InnerOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {

		}
	}*/

	/**
	 * 设置adapter
	 */
	private void setAdapter() {
		if (scanResultList != null && scanResultList.size() > 0) {
			adapter = new WifiListAdapter(getActivity(),scanResultList);
			LogUtil.i(TAG,"setAdapter()");
			wifiListView.setAdapter(adapter);
		}
	}

	// 打开/关闭 wifi
	/*public boolean openWifi() {
		if (!wifiManager.isWifiEnabled()) {
			return wifiManager.setWifiEnabled(true);
		} else {
			return false;
		}
	}*/

	/**
	 * 6.0权限申请回调
     */
/*	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch(requestCode) {
			case REQUECT_CODE_LOCATION:
				if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					loadData();
				}else {
					Toast.makeText(getActivity(), "位置权限被拒绝，请开启权限！", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}*/

	/**
	 * ListView Item 点击事件监听
	 */
	class ListOnItemClickListener implements AdapterView.OnItemClickListener {
		String wifiItemSSID = null;
		private View selectedItem;
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			LogUtil.i("ListOnItemClickListener","start");
			selectedItem = view;
			view.setBackgroundResource(R.color.gray);//点击的Item项背景设置
			ScanResult wifiItem = scanResultList.get(position);//获得选中的设备
			wifiItemSSID = wifiItem.SSID;
			//String []ItemValue = wifiItem.split("--");
			//wifiItemSSID = ItemValue[0];
			LogUtil.i("ListOnItemClickListener",wifiItemSSID);
			int wifiItemId = localWifiUtils.IsConfiguration("\""+wifiItemSSID+"\"");
			LogUtil.i("ListOnItemClickListener",String.valueOf(wifiItemId));
			if(wifiItemId != -1){
				if(localWifiUtils.ConnectWifi(wifiItemId)){//连接指定WIFI
					view.setBackgroundResource(R.color.green);
				}
			}
			else{//没有配置好信息，配置
				WifiPswDialog pswDialog = new WifiPswDialog(getActivity(),new WifiPswDialog.OnCustomDialogListener() {
					@Override
					public void back(String str) {
						wifiPassword = str;
						if(wifiPassword != null){
							int netId = localWifiUtils.AddWifiConfig(scanResultList,wifiItemSSID, wifiPassword);
							LogUtil.i("WifiPswDialog",String.valueOf(netId));
							if(netId != -1){
								localWifiUtils.getConfiguration();//添加了配置信息，要重新得到配置信息
								if(localWifiUtils.ConnectWifi(netId)){
									selectedItem.setBackgroundResource(R.color.green);
								}
							}
							else{
								Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
								selectedItem.setBackgroundResource(R.color.burlywood);
							}
						}
						else{
							selectedItem.setBackgroundResource(R.color.burlywood);
						}
					}
				});
				pswDialog.show();
			}
		}
	}
}