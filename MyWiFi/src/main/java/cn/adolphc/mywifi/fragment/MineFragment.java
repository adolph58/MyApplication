package cn.adolphc.mywifi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.activity.AccountActivity;


public class MineFragment extends Fragment{
	@ViewInject(R.id.rl_mine_activity)
	private View activity;
	@ViewInject(R.id.rl_mine_card)
	private View card;
	@ViewInject(R.id.rl_mine_account)
	private View account;
	@ViewInject(R.id.rl_mine_insurance)
	private View insurance;
	@ViewInject(R.id.rl_mine_help)
	private View help;
	@ViewInject(R.id.rl_mine_setting)
	private View setting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container,false);
		x.view().inject(this,view);
		setListener();
        setBaseInfo();
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
		activity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		card.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		account.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AccountActivity.class);
				startActivity(intent);
			}
		});
		insurance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		help.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

	}

}
