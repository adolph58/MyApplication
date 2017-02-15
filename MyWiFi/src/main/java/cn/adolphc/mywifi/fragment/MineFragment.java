package cn.adolphc.mywifi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import cn.adolphc.mywifi.R;


public class MineFragment extends Fragment{
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container,false);
        //setListener();
        //setBaseInfo();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    	setBaseInfo();
    }

	/**
	 * 设置基本信息
	 */
	private void setBaseInfo() {
		// 显示基本信息，头像，昵称，消费，收入，账户余额

	}


    /**
	 * 设置监听
	 */
	private void setListener() {
		InnerOnClickListener listener = new InnerOnClickListener();

	}

	/**
	 * 内部监听类
	 */
	class InnerOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {

		}
	}
}
