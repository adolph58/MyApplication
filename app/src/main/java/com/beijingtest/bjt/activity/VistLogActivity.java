package com.beijingtest.bjt.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beijingtest.bjt.R;
import com.beijingtest.bjt.SimulationServer.SQLiteUtils;
import com.beijingtest.bjt.entity.Custom;
import com.beijingtest.bjt.entity.VistLog;
import com.beijingtest.bjt.entity.User;
import com.beijingtest.bjt.model.AsyncCallback;
import com.beijingtest.bjt.model.UserModel;
import com.beijingtest.bjt.ui.PicChoiceDialog;
import com.beijingtest.bjt.util.BitmapCallback;
import com.beijingtest.bjt.util.BitmapUtils;
import com.beijingtest.bjt.util.BytesBitmap;
import com.beijingtest.bjt.util.FilePathUtils;
import com.beijingtest.bjt.util.GlobalConsts;
import com.beijingtest.bjt.util.LogUtil;
import com.beijingtest.bjt.util.Tools;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

public class VistLogActivity extends BaseActivity {
    @ViewInject(R.id.et_vist_log_company)
    EditText etCompany;
    @ViewInject(R.id.et_vist_log_content)
    EditText etContent;
//    @ViewInject(R.id.et_vist_log_custom_name)
//    EditText etCustomName;
//    @ViewInject(R.id.et_vist_log_reason)
//    EditText etReason;
//    @ViewInject(R.id.btn_vist_log_choice_pic)
//    Button btnChoicePic;
    @ViewInject(R.id.btn_vist_log_submit)
    Button btnSubmit;
    @ViewInject(R.id.iv_vist_log_back)
    ImageView ivBack;

    public static final int SCAN_RESULT_LIST = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCAN_RESULT_LIST:
                    Tools.showToast("保存成功！");
                    VistLogActivity.this.finish();
                    break;
            }
        }
    };

    private Custom custom;
    private VistLog vistLog;
    private Bitmap photo;
    private File tempFile;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    @Override
    protected void initVariables() {
        String path = FilePathUtils.getDiskCacheDir() + "/images";
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();// 如果文件夹不存在创建文件夹
        }
        tempFile = new File(fileDir, "vist.jpg");

        Intent intent = getIntent();
        custom = (Custom) intent.getSerializableExtra("custom");
        if (null == custom) {
            custom = new Custom();
        }
        vistLog = new VistLog();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_vist_log);
        x.view().inject(this);

//        btnChoicePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                choicePic();
//            }
//        });
        if (null != custom) {
            String company = custom.getCustomName();
            if (null != company) {
                etCompany.setText(company);
            }
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String company = etCompany.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                //Tools.showToast("客户：" + custom.getCustomName());
//                VistLog vistLog = new VistLog();
//                Location location = LocationUtils.getLocation();
                vistLog.setCustomName(company);
                vistLog.setUserId(User.getCurrentUser().getId());
                vistLog.setVistContent(content);
                custom.setCustomName(company);
                writeLog();

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void writeLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteUtils sqLiteUtils = new SQLiteUtils();
                long customId = 1;
                if (custom.getId() <= 0) {
                    customId = sqLiteUtils.insertCustom(custom);
                }
                long id = sqLiteUtils.insertVistLog(vistLog);
                if(customId > 0 && id > 0) {
                    Message message = new Message();
                    message.what = SCAN_RESULT_LIST;
                    handler.sendMessage(message);
                }

            }
        }).start();
    }

    /**
     * 选择照片
     */
    private void choicePic() {
        PicChoiceDialog dialog = new PicChoiceDialog(VistLogActivity.this, new PicChoiceDialog.Callback() {
            @Override
            public void onSubmit(int picChoice) {
                if (picChoice == GlobalConsts.PIC_CHOICE_CAMERA) {
                    // 调用系统的拍照功能
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 指定调用相机拍照后照片的储存路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                    VistLogActivity.this.startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                }
                if (picChoice == GlobalConsts.PIC_CHOICE_ALBUM) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    VistLogActivity.this.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void loadData() {
        getLocation();
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
                                //ivAvatar.setImageBitmap(bitmap);
                            }
                        });
                    }
                    Tools.showToast("更新成功！");
                }

                @Override
                public void onError(Object error) {
                    Tools.showToast("更新失败！请重试！");
                }
            });
        }
    }

    /**
     * 获取位置信息
     */
    public void getLocation() {
        LocationClient locationClient = new LocationClient(getApplicationContext());
        //baiduMap = mapView.getMap();
        // 告诉框架接口指向哪个实现类
        MyBdLocationListener myBdLocationListener = new MyBdLocationListener();
        locationClient.registerLocationListener(myBdLocationListener);
        // 设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        // 设置坐标类型
        option.setCoorType("bd09ll");

        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        // 每隔1秒得1次坐标
        // 少于1000，只得1次坐标
        option.setScanSpan(1);

        locationClient.setLocOption(option);
        // 开始定位
        locationClient.start();
    }

    // 实现类
    class MyBdLocationListener implements BDLocationListener {
        // 框架定位成功后，框架来调,也是回调
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            try {
                //地址
                String address = bdLocation.getAddrStr();
                address = address.substring(2);
                LogUtil.i("地址->", address);
                //经度
                double longitude = bdLocation.getLongitude();
                LogUtil.i("经度->", longitude);
                // 纬度
                double latitude = bdLocation.getLatitude();
                LogUtil.i("纬度->", latitude);
                custom.setAddress(address);
                custom.setLatitude(latitude);
                custom.setLongitude(longitude);
                vistLog.setAddress(address);
                vistLog.setLatitude(latitude);
                vistLog.setLongitude(longitude);
            } catch (Exception e) {
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            LogUtil.i("onConnectHotSpotMessage->", s);
        }
    }
}
