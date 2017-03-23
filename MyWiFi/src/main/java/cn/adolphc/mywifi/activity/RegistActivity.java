package cn.adolphc.mywifi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.model.AsyncCallback;
import cn.adolphc.mywifi.model.UserModel;
import cn.adolphc.mywifi.util.ExceptionHandler;
import cn.adolphc.mywifi.util.Tools;

public class RegistActivity extends BaseActivity {

    @ViewInject(R.id.et_regist_username)
    private EditText etUsername;
    @ViewInject(R.id.et_regist_pwd)
    private EditText etPwd;
    @ViewInject(R.id.et_regist_re_pwd)
    private EditText etRePwd;
    @ViewInject(R.id.btn_regist)
    private Button btnRegist;
    @ViewInject(R.id.iv_regist_back)
    private ImageView ivBack;


    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        try {
            setContentView(R.layout.activity_regist);
            x.view().inject(this);
            btnRegist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    regist();
                }
            });
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    @Override
    protected void loadData() {

    }

    /**
     * 注册
     */
    private void regist() {
        String username = etUsername.getText().toString().trim();
        if (username.length() > 10) {
            Tools.showToast(RegistActivity.this, "用户名不能大于10个字符");
            return;
        }
        String password = etPwd.getText().toString().trim();
        if (password.length() > 64) {
            Tools.showToast(RegistActivity.this, "密码不能大于64个字符");
            return;
        }
        String rePwd = etRePwd.getText().toString().trim();
        if ("".equals(username)) {
            Tools.showToast(RegistActivity.this, "用户名不能为空");
        } else if ("".equals(password)||"".equals(rePwd)) {
            Tools.showToast(RegistActivity.this, "密码不能为空");
        } else if(!password.equals(rePwd)) {
            Tools.showToast(RegistActivity.this, "两次输入密码不一致，请重新输入！");
        } else if (username != null && password != null && rePwd != null && password.equals(rePwd)) {
            UserModel model = new UserModel();
            model.regist(username, password, new AsyncCallback() {
                @Override
                public void onSuccess(Object success) {
                    Tools.showToast(RegistActivity.this, "注册成功！");
                    RegistActivity.this.finish();
                }

                @Override
                public void onError(Object error) {
                    Tools.showToast(RegistActivity.this, error.toString());
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
