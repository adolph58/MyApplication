package com.beijingtest.bjt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import java.io.File;

import com.beijingtest.bjt.MyApplication;
import com.beijingtest.bjt.R;
import com.beijingtest.bjt.entity.User;
import com.beijingtest.bjt.model.AsyncCallback;
import com.beijingtest.bjt.model.UserModel;
import com.beijingtest.bjt.util.ExceptionHandler;
import com.beijingtest.bjt.util.FilePathUtils;

public class SplashActivity extends BaseActivity {
	RelativeLayout animContainer;

	@Override
	protected void initVariables() {
		String path = FilePathUtils.getDiskFilesDir() + "/images";
		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.mkdirs();// 如果文件夹不存在创建文件夹
		}

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		try {
			setContentView(R.layout.activity_splash);
			animContainer = (RelativeLayout) findViewById(R.id.rl_splash_animcontainer);
			initAnim();
		} catch (Exception e) {
		    ExceptionHandler.handleException(e);
		}

	}

	@Override
	protected void loadData() {

	}

	/**
	 * 动画相关
	 */
	private void initAnim() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
		animContainer.startAnimation(anim);
//		new Handler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//				startActivity(intent);
//				finish();
//			}
//		}, 3000);
		
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {	
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {	
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
			    if(MyApplication.getContext().getUsername() != null){
					String username = MyApplication.getContext().getUsername();
					String password = MyApplication.getContext().getPassword();
					UserModel model = new UserModel();
					model.login(username, password, new AsyncCallback() {
						@Override
						public void onSuccess(Object success) {
							Intent intent = new Intent(SplashActivity.this, MainActivity.class);
							startActivity(intent);
						}

						@Override
						public void onError(Object error) {
							Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
							startActivity(intent);
						}
					});

		        }else{
					Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
					startActivity(intent);
		        }
				finish();
			}
		});
	}

}
