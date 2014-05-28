package com.depalma.whoswhere.activities;

import com.depalma.whoswhere.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class LoadingActivity extends Activity {
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_screen);
		
		RotateAnimation anim = new RotateAnimation(0f, 350f, 350f, 350f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(700);

		// Start animating the image
		final ImageView glass = (ImageView) findViewById(R.id.glass);
		glass.startAnimation(anim);

		Thread welcomeThread = new Thread() {

			@Override
			public void run() {
				try {
					super.run();
					sleep(5000); // Delay of 10 seconds
				} catch (Exception e) {

				} finally {

					Intent i = new Intent(LoadingActivity.this,
							LoginActivity.class);
					startActivity(i);
					finish();
				}
			}
		};
		welcomeThread.start();
	}
}
