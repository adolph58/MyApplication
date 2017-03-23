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

public class NicknameActivity extends BaseActivity {
    @ViewInject(R.id.iv_setting_nickname_back)
    private ImageView ivBack;
    @ViewInject(R.id.et_setting_nickname)
    private EditText editText;
    @ViewInject(R.id.btn_setting_nickname)
    private Button button;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nickname);
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
        String nickname = editText.getText().toString().trim();
        if (nickname.length() > 20) {
            Tools.showToast(NicknameActivity.this, "用户名不能大于20个字符");
            return;
        }
        User user = new User();
        user.setId(User.getCurrentUser().getId());
        user.setNickname(nickname);
        UserModel model = new UserModel();
        model.update(user, new AsyncCallback() {
            @Override
            public void onSuccess(Object success) {
                Tools.showToast(NicknameActivity.this, "更新成功！");
                finish();
            }

            @Override
            public void onError(Object error) {
                Tools.showToast(NicknameActivity.this, "更新失败，请重试！");
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
