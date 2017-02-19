package cn.adolphc.mywifi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import cn.adolphc.mywifi.R;
import cn.adolphc.mywifi.entity.User;
import cn.adolphc.mywifi.util.ExceptionHandler;

public class SplashActivity extends BaseActivity {
	RelativeLayout animContainer;

	@Override
	protected void initVariables() {

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
			    if(User.getCurrentUser().getUsername() == null){
		            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
		            startActivity(intent);
		        }else{
		            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				    startActivity(intent);
		        }
				finish();
			}
		});
	}

}
