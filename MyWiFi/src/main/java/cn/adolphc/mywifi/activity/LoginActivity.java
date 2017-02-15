package cn.adolphc.mywifi.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.model.AsyncCallback;
import cn.adolphc.mywifi.model.UserModel;

public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.et_login_username)
    private EditText etUsername;
    @ViewInject(R.id.et_login_pwd)
    private EditText etPwd;
    @ViewInject(R.id.btn_login_login)
    private Button btnLogin;
    @ViewInject(R.id.tv_login_to_regist)
    private View tvToRegist;
    @ViewInject(R.id.tv_login_forget_pwd)
    private View tvFogetPwd;
    @ViewInject(R.id.iv_login_back)
    private ImageView ivBack;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        tvToRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
        tvFogetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "敬请期待！", Toast.LENGTH_SHORT).show();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    /**
     * 登录
     */
    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPwd.getText().toString().trim();
        if ("".equals(username)) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if ("".equals(password)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (username != null && password != null) {
            UserModel model = new UserModel();
            model.login(username,password,new AsyncCallback(){

                @Override
                public void onSuccess(Object success) {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }

                @Override
                public void onError(Object error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
