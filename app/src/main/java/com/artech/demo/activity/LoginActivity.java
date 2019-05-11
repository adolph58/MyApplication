package com.artech.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.artech.demo.MyApplication;
import com.artech.demo.R;
import com.artech.demo.model.AsyncCallback;
import com.artech.demo.model.UserModel;
import com.artech.demo.util.ExceptionHandler;
import com.artech.demo.util.Tools;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.et_login_username)
    private EditText etUsername;
    @ViewInject(R.id.et_login_pwd)
    private EditText etPwd;
//    @ViewInject(R.id.et_login_verify)
//    private EditText etVerify;
//    @ViewInject(R.id.iv_login_verify)
//    private ImageView ivVerify;
    @ViewInject(R.id.btn_login_login)
    private Button btnLogin;
//    @ViewInject(R.id.tv_login_to_regist)
//    private View tvToRegist;
//    @ViewInject(R.id.tv_login_forget_pwd)
//    private View tvFogetPwd;
    @ViewInject(R.id.iv_login_back)
    private ImageView ivBack;


    //private UserModel model;
//    public static final int SCAN_RESULT_LIST = 1;
//    public static final int LOGIN_FAIL = 2;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SCAN_RESULT_LIST:
//                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    LoginActivity.this.finish();
//                    break;
//                case LOGIN_FAIL:
//                    Tools.showToast("用户名或密码错误");
//                    break;
//            }
//        }
//    };

    @Override
    protected void initVariables() {
        //model = new UserModel();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        try {
            setContentView(R.layout.activity_login);
            x.view().inject(this);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });
//            tvToRegist.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
//                    startActivity(intent);
//                }
//            });
//            tvFogetPwd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Tools.showToast(LoginActivity.this, "敬请期待！");
//                }
//            });
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
//            ivVerify.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    getImageCode();
//                }
//            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }

    }

    @Override
    protected void loadData() {
        //getImageCode();
    }

    /**
     * 登录
     */
    private void login() {
        UserModel model = new UserModel();
        final String username = etUsername.getText().toString().trim();
        final String password = etPwd.getText().toString().trim();
        //final String verify = etVerify.getText().toString().trim();
        if ("".equals(username)) {
            Tools.showToast(LoginActivity.this, "用户名不能为空");
        } else if ("".equals(password)) {
            Tools.showToast(LoginActivity.this, "密码不能为空");
        }
//        else if("".equals(verify)) {
//            Tools.showToast(LoginActivity.this, "验证码不能为空");
//        }
        else if (username.length() > 0 && password.length() > 0) {
//            final User user = new User();
//            user.setUsername(username);
//            user.setPassword(password);
//            final SQLiteUtils sqLiteUtils = new SQLiteUtils();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String pwd = sqLiteUtils.findPwdByUsername(username);
//                    if(password.equals(pwd)) {
//                        User resUser = sqLiteUtils.findUserByUsername(username);
//                        User.saveUser(resUser);
//                        Message message = new Message();
//                        message.what = SCAN_RESULT_LIST;
//                        handler.sendMessage(message);
//                    }else {
//                        Message message = new Message();
//                        message.what = LOGIN_FAIL;
//                        handler.sendMessage(message);
//                    }
//                }
//            }).start();
            MyApplication.getContext().saveUserInfo(username, password);
            model.login(username,password,new AsyncCallback(){

                @Override
                public void onSuccess(Object success) {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }

                @Override
                public void onError(Object error) {
                    Tools.showToast(LoginActivity.this, error.toString());
                }
            });
        }

    }

//    private void getImageCode() {
//        model.getImageCode(new AsyncCallback() {
//            @Override
//            public void onSuccess(Object success) {
//                Bitmap bitmap = (Bitmap)success;
//                if(null != bitmap) {
//                    ivVerify.setImageBitmap(bitmap);
//                }
//            }
//
//            @Override
//            public void onError(Object error) {
//                Tools.showToast(error.toString());
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication.getContext().finish();
    }
}
