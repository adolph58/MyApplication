package cn.adolphc.mywifi.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.adolphc.mywifi.R;


public class ClaimTypeDialog extends Dialog{

	private Button btnPersonal;
	private Button btnMerchant;
	private Button btnCompany;

	private OnCustomDialogListener customDialogListener;
	public ClaimTypeDialog(Context context, OnCustomDialogListener customListener) {
		//OnCancelListener cancelListener) {
		super(context);
		customDialogListener = customListener;
		
	}
	//定义dialog的回调事件
	public interface OnCustomDialogListener{
		void back(String type);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_claim_type);
		btnPersonal = (Button) findViewById(R.id.btn_wifi_type_personal);
		btnMerchant = (Button) findViewById(R.id.btn_wifi_type_merchant);
		btnCompany = (Button) findViewById(R.id.btn_wifi_type_company);

		btnPersonal.setOnClickListener(buttonDialogListener);
		btnMerchant.setOnClickListener(buttonDialogListener);
		btnCompany.setOnClickListener(buttonDialogListener);
	}
	
	private View.OnClickListener buttonDialogListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.btn_wifi_type_personal:
					customDialogListener.back("个人");
					dismiss();
					break;
				case R.id.btn_wifi_type_merchant:
					customDialogListener.back("商家");
					dismiss();
					break;
				case R.id.btn_wifi_type_company:
					customDialogListener.back("单位");
					dismiss();
					break;
			}

		}
	};
	
}
