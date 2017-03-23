package cn.adolphc.mywifi.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.entity.User;
import cn.adolphc.mywifi.util.GlobalConsts;

/**
 * Created by Jaunty on 2017/3/12.
 */

public class GenderChoiceDialog extends Dialog {
    Context context;
    @ViewInject(R.id.rb_gender_choice_male)
    private RadioButton male;
    @ViewInject(R.id.rb_gender_choice_female)
    private RadioButton female;

    private Callback callback;

    public GenderChoiceDialog(Context context) {
        super(context);
        this.context = context;
    }

    public GenderChoiceDialog(Context context, Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }

    public GenderChoiceDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected GenderChoiceDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
        View dialogView = View.inflate(context, R.layout.dialog_gender_choice, null);
        window.setContentView(dialogView);
        x.view().inject(this,dialogView);
        User user = User.getCurrentUser();
        if (null != user) {
            String gender = user.getGender();
            if (null != gender && "男".equals(gender)) {
                male.setChecked(true);
            }
            if (null != gender && "女".equals(gender)) {
                female.setChecked(true);
            }
        }
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.onSubmit(GlobalConsts.GENDER_CHOICE_MALE);
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.onSubmit(GlobalConsts.GENDER_CHOICE_FEMALE);
            }
        });

    }

    public interface Callback{
        void onSubmit(int genderChoice);
    }

}
