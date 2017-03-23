package cn.adolphc.mywifi.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import cn.adolphc.mywifi.R;

/**
 * Created by Jaunty on 2017/3/12.
 */

public class LinkWifiDialog extends Dialog {
    Context context;

    public LinkWifiDialog(Context context) {
        super(context);
        this.context = context;
    }

    public LinkWifiDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected LinkWifiDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
        View dialogView = View.inflate(context, R.layout.dialog_link_wifi, null);
        window.setContentView(dialogView);
        //x.view().inject(this,dialogView);
        //setViews(dialogView);
        //setListener();
    }

    private void setListener() {
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Address address = new Address();
//                address.setReceiveName(etReceiveName.getText().toString());
//                address.setFull_address(etAddress.getText().toString());
//                address.setMobile(etPhone.getText().toString());
//                address.setPostalCode(etCode.getText().toString());
//                address.setPhone(etTel.getText().toString());
//                //点击提交后由 IAddressPresenter 的实现类（AddressActivity）执行 onSubmit 方法
//                callback.onSubmit(address);
//            }
//        });
    }


}
