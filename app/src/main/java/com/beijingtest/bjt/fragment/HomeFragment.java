package com.beijingtest.bjt.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beijingtest.bjt.MyApplication;
import com.beijingtest.bjt.R;
import com.beijingtest.bjt.SimulationServer.api.SalesAPI;
import com.beijingtest.bjt.activity.LoginActivity;
import com.beijingtest.bjt.activity.MainActivity;
import com.beijingtest.bjt.activity.SearchCustomActivity;
import com.beijingtest.bjt.activity.SplashActivity;
import com.beijingtest.bjt.entity.Custom;
import com.beijingtest.bjt.activity.VistLogActivity;
import com.beijingtest.bjt.adapter.CustomAdapter;
import com.beijingtest.bjt.entity.CustomList;
import com.beijingtest.bjt.entity.MyLocation;
import com.beijingtest.bjt.model.AsyncCallback;
import com.beijingtest.bjt.model.SalesModel;
import com.beijingtest.bjt.model.UserModel;
import com.beijingtest.bjt.util.LogUtil;
import com.beijingtest.bjt.util.Tools;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Iterator;
import java.util.List;

public class HomeFragment extends Fragment{

	//private static final int REQUECT_CODE_LOCATION = 1;  //申请位置权限返回码
	@ViewInject(R.id.lv_sales_custom_list)
	private ListView customListView;
//	@ViewInject(R.id.btn_sales_add_custom)
//	private Button addCustom;
//	@ViewInject(R.id.et_sales_search)
//	private EditText etSearch;
	@ViewInject(R.id.iv_sales_search)
	private ImageView ivSearch;
	private CustomAdapter adapter;
	private List<Custom> customList;
	private MyLocation myLocation;
	public static final String TAG = "HomeFragment";
	public static final int SCAN_RESULT_LIST = 1;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SCAN_RESULT_LIST:
					setAdapter();
					break;
			}
		}
	};

	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
		x.view().inject(this,view);
		myLocation = new MyLocation();
		//MPermissions.requestPermissions(getActivity(), REQUECT_CODE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);
		//检查获取位置权限
		/*if (ContextCompat.checkSelfPermission(getActivity(),
				Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {
			//ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},REQUECT_CODE_LOCATION);
			MPermissions.requestPermissions(this, REQUECT_CODE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);
		} else {
			loadData();
		}*/

		setListener();
        return view;
    }



	private void loadList() {

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(100);
//				    customList = SalesAPI.getCustomList();
//					Custom.saveCustomList(customList);
//					customList = SalesAPI.getCustomListByLocation(customList, myLocation);
//					//System.out.println(customList.toString());
//				    Message message = new Message();
//				    message.what = SCAN_RESULT_LIST;
//				    handler.sendMessage(message);
//				} catch (InterruptedException e) {
//					Thread.currentThread().interrupt();
//				}
//			}
//		}).start();
		SalesModel model = new SalesModel();
		model.getCustomList(new AsyncCallback() {
			@Override
			public void onSuccess(Object success) {
				customList = (List<Custom>)success;
				Custom.saveCustomList(customList);
				customList = SalesAPI.getCustomListByLocation(customList, myLocation);
				System.out.println("加载成功：" + customList.toString());
				setAdapter();
			}

			@Override
			public void onError(Object error) {
				String msg = error.toString();
				System.out.println("加载失败：" + msg);
				System.out.println("session_id：" + MyApplication.getContext().getSessionId());
				if ("session失效".equals(msg)) {
					String username = MyApplication.getContext().getUsername();
					String password = MyApplication.getContext().getPassword();
					UserModel model = new UserModel();
					model.login(username, password, new AsyncCallback() {
						@Override
						public void onSuccess(Object success) {
							loadList();
						}
						@Override
						public void onError(Object error) {
							Tools.showToast(error.toString());
						}
					});
				}
			}
		});


	}

	/**
	 * 设置监听
	 */
	private void setListener() {
//		addCustom.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Intent intent = new Intent(getActivity(), VistLogActivity.class);
//				getActivity().startActivity(intent);
//			}
//		});
		ivSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), SearchCustomActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}

//	private void search() {
//		String content = etSearch.getText().toString().trim();
//		Iterator<Custom> it = customList.iterator();
//		while(it.hasNext()) {
//			Custom custom = it.next();
//			String customName = custom.getCustomName();
//			if(!customName.contains(content)) {
//				it.remove();
//			}
//		}
//		adapter.notifyDataSetChanged();
//	}

	/**
	 * 设置adapter
	 */
	private void setAdapter() {
		LogUtil.i("客户列表数量：", customList.size());
		LogUtil.i("客户列表：", customList.toString());
		// && longitude != 4.9E-324 && longitude != 0.0
		if (customList.size() > 0 ) {
			adapter = new CustomAdapter(getActivity(),customList);
			customListView.setAdapter(adapter);
		}
	}

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
	 * 获取位置信息
	 */
	public void getLocation() {
		LocationClient locationClient = new LocationClient(getActivity().getApplicationContext());
		// 告诉框架接口指向哪个实现类
		MyBdLocationListener myBdLocationListener = new MyBdLocationListener();
		locationClient.registerLocationListener(myBdLocationListener);
		// 设置定位参数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		// 设置坐标类型
		option.setCoorType("bd09ll");

		//可选，设置是否需要地址信息，默认不需要
		//option.setIsNeedAddress(true);

		// 每隔1秒得1次坐标
		// 少于1000，只得1次坐标
		option.setScanSpan(1);

		locationClient.setLocOption(option);
		// 开始定位
		locationClient.start();
	}

	// 实现类
	class MyBdLocationListener implements BDLocationListener {
		// 框架定位成功后，框架来调,也是回调
		@Override
		public void onReceiveLocation(BDLocation bdLocation) {
			try {
				//经度
				double longitude = bdLocation.getLongitude();
				LogUtil.i("经度->", longitude);
				// 纬度
				double latitude = bdLocation.getLatitude();
				LogUtil.i("纬度->", latitude);
				myLocation.setLatitude(latitude);
				myLocation.setLongitude(longitude);
				loadList();
			} catch (Exception e) {
			}
		}

		@Override
		public void onConnectHotSpotMessage(String s, int i) {
			LogUtil.i("onConnectHotSpotMessage->", s);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		getLocation();
	}
}