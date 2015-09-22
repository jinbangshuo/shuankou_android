package com.maitianer.shuankou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.maitianer.shuankou.R;
import com.umeng.analytics.MobclickAgent;

public class Welcome extends Activity
{
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_welcome);

		new Handler().postDelayed(new Runnable() {
			public void run () {
				Intent intent = new Intent(Welcome.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 3000);
	}

	@Override
	public void onResume () {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause () {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
