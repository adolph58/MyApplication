package com.artech.demo.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import com.artech.demo.R;
import com.artech.demo.util.GlobalConsts;

/**
 * Created by dev on 2017/4/12.
 */

public class PicChoiceDialog extends Dialog {
    Context context;
    @ViewInject(R.id.btn_pic_choice_camera)
    private Button btnCamera;
    @ViewInject(R.id.btn_pic_choice_album)
    private Button btnAlbum;

    private Callback callback;

    public PicChoiceDialog(Context context) {
        super(context);
        this.context = context;
    }

    public PicChoiceDialog(Context context, Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }

    public PicChoiceDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected PicChoiceDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
        View dialogView = View.inflate(context, R.layout.dialog_pic_choice, null);
        window.setContentView(dialogView);
        x.view().inject(this,dialogView);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.onSubmit(GlobalConsts.PIC_CHOICE_CAMERA);
            }
        });
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.onSubmit(GlobalConsts.PIC_CHOICE_ALBUM);
            }
        });

    }

    public interface Callback{
        void onSubmit(int picChoice);
    }

}
