package cn.adolphc.mywifi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.entity.User;
import cn.adolphc.mywifi.model.AsyncCallback;
import cn.adolphc.mywifi.model.UserModel;
import cn.adolphc.mywifi.util.Tools;

public class SignatureActivity extends BaseActivity {

    @ViewInject(R.id.iv_setting_signature_back)
    private ImageView ivBack;
    @ViewInject(R.id.et_setting_signature)
    private EditText editText;
    @ViewInject(R.id.btn_setting_signature)
    private Button button;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_signature);
        x.view().inject(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    private void update() {
        String signatrue = editText.getText().toString().trim();
        if (signatrue.length() > 50) {
            Tools.showToast(SignatureActivity.this, "个性签名不能大于50个字符");
            return;
        }
        User user = new User();
        user.setId(User.getCurrentUser().getId());
        user.setSignature(signatrue);
        UserModel model = new UserModel();
        model.update(user, new AsyncCallback() {
            @Override
            public void onSuccess(Object success) {
                Tools.showToast(SignatureActivity.this, "更新成功！");
                finish();
            }

            @Override
            public void onError(Object error) {
                Tools.showToast(SignatureActivity.this, "更新失败，请重试！");
            }
        });
    }

    @Override
    protected void loadData() {

    }

}
