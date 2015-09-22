package com.maitianer.shuankou.activity;

import me.dawson.kisstools.utils.SystemUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.maitianer.shuankou.R;
import com.umeng.analytics.MobclickAgent;

public class AboutActivity extends Activity implements OnClickListener
{
	private TextView lbl_ver;
	private TextView title;
	private Button btn_bar_left;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_aboutme);
		initView();
		loadData();
	}

	// 控件初始化
	private void initView () {
		title = (TextView) findViewById(R.id.title);
		btn_bar_left = (Button) findViewById(R.id.btn_bar_left);
		lbl_ver = (TextView) findViewById(R.id.lbl_ver);

		btn_bar_left.setOnClickListener(this);
	}

	// 加载数据
	private void loadData () {
		title.setText("关于我们");
		btn_bar_left.setText("返回");
		lbl_ver.setText("当前版本：" + SystemUtil.GetSystemVersionName(this));
	}

	@Override
	public void onClick (View arg0) {
		switch (arg0.getId()) {
			case R.id.btn_bar_left:
				finish();
				break;

			default:
				break;
		}

	}

	@Override
	public boolean onKeyDown (int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
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