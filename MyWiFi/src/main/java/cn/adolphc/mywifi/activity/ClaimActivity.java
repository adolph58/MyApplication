package cn.adolphc.mywifi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.ui.ClaimTypeDialog;
import cn.adolphc.mywifi.util.ExceptionHandler;

public class ClaimActivity extends BaseActivity {

    @ViewInject(R.id.et_claim_hotspot_pwd)
    private EditText etPwd;
    @ViewInject(R.id.et_claim_unit_price)
    private EditText etUnitPrice;
    @ViewInject(R.id.tv_claim_hotspot_type)
    private TextView tvType;
    @ViewInject(R.id.btn_claim_hotspot_submit)
    private Button btnSubmit;
    @ViewInject(R.id.ll_claim_hotspot_type)
    private LinearLayout llType;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        try {
            setContentView(R.layout.activity_claim);
            x.view().inject(this);
            llType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectType();
                }
            });
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    claim();
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }

    }

    @Override
    protected void loadData() {

    }

    private void claim() {
        String pwd = etPwd.getText().toString().trim();
        String unitPrice = etUnitPrice.getText().toString().trim();
    }

    private void selectType() {
        ClaimTypeDialog typeDialog = new ClaimTypeDialog(this, new ClaimTypeDialog.OnCustomDialogListener() {
            @Override
            public void back(String type) {
                tvType.setText(type);
            }
        });
        typeDialog.show();
    }
}
