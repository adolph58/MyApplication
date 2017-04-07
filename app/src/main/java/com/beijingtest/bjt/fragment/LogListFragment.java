package com.beijingtest.bjt.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beijingtest.bjt.R;
import com.beijingtest.bjt.SimulationServer.SQLiteUtils;
import com.beijingtest.bjt.entity.VistLog;
import com.beijingtest.bjt.adapter.VistLogAdapter;
import com.beijingtest.bjt.util.LogUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class LogListFragment extends Fragment{

	//private static final int REQUECT_CODE_LOCATION = 1;  //申请位置权限返回码
	@ViewInject(R.id.lv_vist_log_list)
	private ListView vistLogListView;
	private VistLogAdapter adapter;
	List<VistLog> vistLogList;
	public static final String TAG = "LogListFragment";
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
        View view = inflater.inflate(R.layout.fragment_log_list, container,false);
		x.view().inject(this,view);
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

        //setAdapter();
		loadList();
		//setListener();
        return view;
    }



	private void loadList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
					SQLiteUtils sqLiteUtils = new SQLiteUtils();
					vistLogList = sqLiteUtils.queryVistLogList();
				    //System.out.println(customList.toString());
				    Message message = new Message();
				    message.what = SCAN_RESULT_LIST;
				    handler.sendMessage(message);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}).start();
	}


	/**
	 * 设置adapter
	 */
	private void setAdapter() {
		if (vistLogList != null && vistLogList.size() > 0) {
			adapter = new VistLogAdapter(getActivity(),vistLogList);
			LogUtil.i(TAG,"setAdapter()");
			vistLogListView.setAdapter(adapter);
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
	 * ListView Item 点击事件监听
	 */
	class ListOnItemClickListener implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {

		}
	}

	@Override
	public void onResume() {
		super.onResume();
		loadList();
	}
}