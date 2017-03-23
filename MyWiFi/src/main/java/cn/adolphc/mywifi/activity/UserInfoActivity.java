package cn.adolphc.mywifi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.entity.User;
import cn.adolphc.mywifi.model.AsyncCallback;
import cn.adolphc.mywifi.model.UserModel;
import cn.adolphc.mywifi.ui.GenderChoiceDialog;
import cn.adolphc.mywifi.ui.PicChoiceDialog;
import cn.adolphc.mywifi.util.BitmapCallback;
import cn.adolphc.mywifi.util.BitmapUtils;
import cn.adolphc.mywifi.util.BytesBitmap;
import cn.adolphc.mywifi.util.FilePathUtils;
import cn.adolphc.mywifi.util.GlobalConsts;
import cn.adolphc.mywifi.util.Tools;

public class UserInfoActivity extends BaseActivity {
    @ViewInject(R.id.rl_user_info_avatar)
    private View rlAvator;
    @ViewInject(R.id.rl_user_info_gender)
    private View rlGender;
    @ViewInject(R.id.rl_user_info_nickname)
    private View rlNickname;
    @ViewInject(R.id.rl_user_info_phone_number)
    private View rlPhoneNumber;
    @ViewInject(R.id.rl_user_info_signature)
    private View rlSignature;
    @ViewInject(R.id.iv_user_info_back)
    private ImageView ivBack;
    @ViewInject(R.id.civ_user_info_avatar)
    private ImageView ivAvatar;
    @ViewInject(R.id.tv_user_info_nickname)
    private TextView tvNickname;
    @ViewInject(R.id.tv_user_info_phone_number)
    private TextView tvPhoneNumber;
    @ViewInject(R.id.tv_user_info_gender)
    private TextView tvGender;
    @ViewInject(R.id.tv_user_info_signature)
    private TextView tvSignature;

    private Bitmap photo;
    private File tempFile;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    @Override
    protected void initVariables() {
        String path = FilePathUtils.getDiskFilesDir() + "/images";
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();// 如果文件夹不存在创建文件夹
        }
        tempFile = new File(fileDir, "avatar.jpg");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info);
        x.view().inject(this);

        rlAvator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicChoiceDialog dialog = new PicChoiceDialog(UserInfoActivity.this, new PicChoiceDialog.Callback() {
                    @Override
                    public void onSubmit(int picChoice) {
                        if (picChoice == GlobalConsts.PIC_CHOICE_CAMERA) {
                            // 调用系统的拍照功能
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // 指定调用相机拍照后照片的储存路径
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                            UserInfoActivity.this.startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                        }
                        if (picChoice == GlobalConsts.PIC_CHOICE_ALBUM) {
                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            UserInfoActivity.this.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                        }
                    }
                });
                dialog.show();
            }
        });

        rlGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderChoiceDialog dialog = new GenderChoiceDialog(UserInfoActivity.this, new GenderChoiceDialog.Callback() {
                    @Override
                    public void onSubmit(int genderChoice) {
                        if (genderChoice == GlobalConsts.GENDER_CHOICE_MALE) {
                            updateGender("男");
                        }
                        if (genderChoice == GlobalConsts.GENDER_CHOICE_FEMALE) {
                            updateGender("女");
                        }
                    }
                });
                dialog.show();
            }
        });

        rlNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, NicknameActivity.class);
                startActivity(intent);
            }
        });

        rlPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, PhoneActivity.class);
                startActivity(intent);
            }
        });

        rlSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, SignatureActivity.class);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置个人信息
        setUserInfo();

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(tempFile), 150);
                break;

            case PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    startPhotoZoom(data.getData(), 150);
                }
                break;

            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 裁剪图片
     * @param uri
     * @param size
     */
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    /**
     * 将进行剪裁后的图片进行处理
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            photo = bundle.getParcelable("data");
            updateAvatar();
        }
    }

    /**
     * 更新图片
     */
    private void updateAvatar () {
        if (photo != null) {
            UserModel model = new UserModel();
            User user = new User();
            user.setId(User.getCurrentUser().getId());
            user.setAvatar(BytesBitmap.getBytesOfJPEG(photo));
            model.update(user, new AsyncCallback() {
                @Override
                public void onSuccess(Object success) {
                    User resUser = (User) success;
                    File oldFile = new File(FilePathUtils.getDiskFilesDir() + "/images/avatar" +resUser.getId() + ".jpg");
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                    if (null != resUser.getAvatarUrl() ) {
                        BitmapUtils.loadBitmap(resUser.getAvatarUrl(), new BitmapCallback() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap) {
                                ivAvatar.setImageBitmap(bitmap);
                            }
                        });
                    }
                    Tools.showToast(UserInfoActivity.this, "更新成功！");
                }

                @Override
                public void onError(Object error) {
                    Tools.showToast(UserInfoActivity.this, "更新失败！请重试！");
                }
            });
        }
    }

    /**
     * 更新性别
     */
    private void updateGender(String gender) {
        UserModel model = new UserModel();
        User user = new User();
        user.setId(User.getCurrentUser().getId());
        user.setGender(gender);
        model.update(user, new AsyncCallback() {
            @Override
            public void onSuccess(Object success) {
                User resUser = (User)success;
                tvGender.setText(resUser.getGender());
                Tools.showToast(UserInfoActivity.this, "更新成功！");
            }

            @Override
            public void onError(Object error) {
                Tools.showToast(UserInfoActivity.this, "更新失败！请重试！");
            }
        });
    }

    /**
     * 设置用户信息
     */
    private void setUserInfo () {
        User user = User.getCurrentUser();
        if (null == user) {
            return;
        }
        if (null != user.getAvatarUrl() ) {
            BitmapUtils.loadBitmap(user.getAvatarUrl(), new BitmapCallback() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap) {
                    ivAvatar.setImageBitmap(bitmap);
                }
            });
        }
        if (null != user.getNickname()) {
            tvNickname.setText(user.getNickname());
        }
        if (null != user.getGender()) {
            tvGender.setText(user.getGender());
        }
        if (null != user.getPhoneNumber()) {
            tvPhoneNumber.setText(user.getPhoneNumber());
        }
        if (null != user.getSignature()) {
            tvSignature.setText(user.getSignature());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserInfo();
    }
}
