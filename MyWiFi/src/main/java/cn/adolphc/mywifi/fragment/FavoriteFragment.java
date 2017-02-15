package cn.adolphc.mywifi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.adolphc.mywifi.R;

public class FavoriteFragment extends Fragment{
	@ViewInject(R.id.tv_linked_hotspot_ssid)
	private TextView tvSsid;
	@ViewInject(R.id.tv_linked_hotspot_bssid)
	private TextView tvBssid;
	@ViewInject(R.id.tv_who_belongs_to)
	private TextView tvBelong;
	@ViewInject(R.id.btn_claim)
	private Button btnClaim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container,false);
		x.view().inject(this,view);
		return view;
    }

    @Override
    public void onResume() {
        super.onResume();

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
