package com.beijingtest.bjt.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijingtest.bjt.R;
import com.beijingtest.bjt.activity.MainActivity;
import com.beijingtest.bjt.activity.SettingActivity;
import com.beijingtest.bjt.download.DownLoadUtils;
import com.beijingtest.bjt.download.DownloadApk;
import com.beijingtest.bjt.entity.AppVersion;
import com.beijingtest.bjt.model.AsyncCallback;
import com.beijingtest.bjt.model.VersionModel;
import com.beijingtest.bjt.util.GlobalConsts;
import com.beijingtest.bjt.util.Tools;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;



public class MineFragment extends Fragment {

    @ViewInject(R.id.rl_mine_version_update)
    private View versionUpdate;
    @ViewInject(R.id.rl_mine_setting)
    private View setting;
    @ViewInject(R.id.tv_mine_help_current_version)
    private TextView tvCurrentVersion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        x.view().inject(this, view);
        setListener();
        setBaseInfo();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setBaseInfo();
    }

    /**
     * 设置基本信息
     */
    private void setBaseInfo() {
        try {
            String strCurrentVersion = Tools.getCurrentVersion(getActivity());
            tvCurrentVersion.setText("当前版本：" + strCurrentVersion);
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置监听
     */
    private void setListener() {

        versionUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final double currentVersion = Double.parseDouble(Tools.getCurrentVersion(getActivity()));
                    System.out.println(currentVersion);
                    VersionModel model = new VersionModel();
                    model.getAppVersion(new AsyncCallback() {
                        @Override
                        public void onSuccess(Object success) {
                            final AppVersion appVersion = (AppVersion) success;
                            double serverVersion = Double.parseDouble(appVersion.getVersionNO());
                            if (serverVersion > currentVersion ) {
                                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                dialog.setTitle("有新版本：" + appVersion.getVersionNO());
                                dialog.setMessage(appVersion.getRemark());
                                dialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //如果手机已经启动下载程序，执行downloadApk。否则跳转到设置界面
//                                        if (DownLoadUtils.getInstance(getActivity().getApplicationContext()).canDownload()) {
//                                            new Thread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    DownloadApk.downloadApk(getActivity().getApplicationContext(), GlobalConsts.URL_APP_UPDATE + appVersion.getFileName(), "京诚检测更新", "bjt");
//                                                }
//                                            }).start();
//
//                                        } else {
//                                            DownLoadUtils.getInstance(getActivity().getApplicationContext()).skipToDownloadManager();
//                                        }
                                        MainActivity.instance.downloadFile(GlobalConsts.URL_APP_UPDATE + appVersion.getFileName());
                                    }
                                });
                                dialog.setNegativeButton("取消", null);
                                dialog.show();
                            } else {
                                Tools.showToast("当前版本已是最新版本！");
                            }
                        }

                        @Override
                        public void onError(Object error) {

                        }
                    });
                }catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });


    }

}
