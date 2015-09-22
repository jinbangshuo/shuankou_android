package com.maitianer.shuankou.activity;

import me.dawson.kisstools.utils.MsgToastUtil;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.maitianer.shuankou.R;
import com.maitianer.shuankou.util.DBManager;
import com.umeng.analytics.MobclickAgent;

public class PlayerEdit extends Activity implements OnClickListener
{
	private TextView title;
	private Button btn_bar_left, btn_bar_right;
	private EditText txtname;
	private CheckBox chkclose;

	private Bundle bundle;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_playeredit);
		initView();
		loadData();
	}

	// 控件初始化
	private void initView () {
		title = (TextView) findViewById(R.id.title);
		btn_bar_left = (Button) findViewById(R.id.btn_bar_left);
		btn_bar_right = (Button) findViewById(R.id.btn_bar_right);
		chkclose = (CheckBox) findViewById(R.id.chkclose);
		txtname = (EditText) findViewById(R.id.txtname);

		btn_bar_left.setOnClickListener(this);
		btn_bar_right.setOnClickListener(this);
	}

	// 加载数据
	private void loadData () {
		title.setText("玩家编辑");
		btn_bar_left.setText("返回");
		btn_bar_right.setText("保存");
		bundle = getIntent().getExtras();
		if (bundle != null) {
			txtname.setText(bundle.getString("name"));
			if (bundle.getInt("close") == 0) {
				chkclose.setChecked(false);
			} else {
				chkclose.setChecked(true);
			}
		}
	}

	@Override
	public void onClick (View arg0) {
		switch (arg0.getId()) {
			case R.id.btn_bar_left:
				setResult(RESULT_CANCELED);
				finish();
				break;
			case R.id.btn_bar_right:
				doSave();
				break;

			default:
				break;
		}
	}

	// 保存
	private void doSave () {
		String SqlStr;
		DBManager TheAccess = new DBManager(this);
		if (bundle == null) {
			SqlStr = "Select 1 from playerList Where name='" + txtname.getText().toString() + "'";
		} else {
			SqlStr = "Select 1 from playerList Where name='" + txtname.getText().toString() + "' and id<>" + bundle.getInt("id");
		}
		Cursor rst = TheAccess.sqlQuery(SqlStr);
		if (rst.moveToNext()) {
			MsgToastUtil.MsgBox(this, "当前已经存在该名称的玩家！");
			return;
		}
		rst.close();
		SqlStr = "Select max(id) as id from playerList";
		rst = TheAccess.sqlQuery(SqlStr);
		int maxId = 1;
		if (rst.moveToNext()) {
			maxId = rst.getInt(rst.getColumnIndex("id")) + 1;
		}
		rst.close();
		int iClose = 0;
		if (chkclose.isChecked()) {
			iClose = 1;
		} else {
			iClose = 0;
		}
		if (bundle == null) {
			SqlStr = "Insert into playerList(id,name,closed)Values(" + maxId + ",'" + txtname.getText().toString() + "'," + iClose + ")";
		} else {
			SqlStr = "Update playerList set name='" + txtname.getText().toString() + "',closed=" + iClose + " Where id=" + bundle.getInt("id");
		}
		TheAccess.execSQL(SqlStr);
		TheAccess.close();
		MsgToastUtil.MsgBox(this, "玩家保存成功！");
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public boolean onKeyDown (int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			setResult(RESULT_CANCELED);
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
