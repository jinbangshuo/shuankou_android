package com.maitianer.shuankou.activity;

import me.dawson.kisstools.utils.MsgToastUtil;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.maitianer.shuankou.R;
import com.maitianer.shuankou.util.DBManager;
import com.maitianer.shuankou.util.MyApplication;
import com.umeng.analytics.MobclickAgent;

public class SystemSetActivity extends Activity implements OnClickListener
{
	private TextView title;
	private Button btn_bar_left, btn_bar_right;
	private EditText txt_p, txt_d, txt_s;
	private EditText txt_6, txt_7, txt_8, txt_9, txt_10, txt_11, txt_12;
	private Spinner cbo_max;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_systemset);
		initView();
		loadData();
	}

	// 控件初始化
	private void initView () {
		title = (TextView) findViewById(R.id.title);
		btn_bar_left = (Button) findViewById(R.id.btn_bar_left);
		btn_bar_right = (Button) findViewById(R.id.btn_bar_right);

		txt_p = (EditText) findViewById(R.id.txt_p);
		txt_d = (EditText) findViewById(R.id.txt_d);
		txt_s = (EditText) findViewById(R.id.txt_s);
		cbo_max = (Spinner) findViewById(R.id.cbo_max);
		txt_p.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged (Editable s) {}

			public void beforeTextChanged (CharSequence s, int start, int count, int after) {}

			public void onTextChanged (CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					float f_p = Float.parseFloat(txt_p.getText().toString());
					txt_d.setText(MyApplication.FormatNumber(f_p * 2));
					txt_s.setText(MyApplication.FormatNumber(f_p * 3));
				} else {
					txt_d.setText("");
					txt_s.setText("");
				}
			}
		});

		txt_6 = (EditText) findViewById(R.id.txt_6);
		txt_7 = (EditText) findViewById(R.id.txt_7);
		txt_8 = (EditText) findViewById(R.id.txt_8);
		txt_9 = (EditText) findViewById(R.id.txt_9);
		txt_10 = (EditText) findViewById(R.id.txt_10);
		txt_11 = (EditText) findViewById(R.id.txt_11);
		txt_12 = (EditText) findViewById(R.id.txt_12);
		txt_6.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged (Editable s) {}

			public void beforeTextChanged (CharSequence s, int start, int count, int after) {}

			public void onTextChanged (CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					float f_6 = Float.parseFloat(txt_6.getText().toString());
					txt_7.setText(MyApplication.FormatNumber(f_6 * 2));
					txt_8.setText(MyApplication.FormatNumber(f_6 * 4));
					txt_9.setText(MyApplication.FormatNumber(f_6 * 8));
					txt_10.setText(MyApplication.FormatNumber(f_6 * 16));
					txt_11.setText(MyApplication.FormatNumber(f_6 * 32));
					txt_12.setText(MyApplication.FormatNumber(f_6 * 64));
				} else {
					txt_7.setText("");
					txt_8.setText("");
					txt_9.setText("");
					txt_10.setText("");
					txt_11.setText("");
					txt_12.setText("");
				}
			}
		});
		btn_bar_left.setOnClickListener(this);
		btn_bar_right.setOnClickListener(this);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.slevel, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cbo_max.setAdapter(adapter);
		cbo_max.setPrompt("最高翻至");
	}

	// 加载数据
	private void loadData () {
		title.setText("设置");
		btn_bar_left.setText("返回");
		btn_bar_right.setText("保存");

		DBManager TheAccess = new DBManager(this);
		String SqlStr = "Select set_p,set_d,set_s,set_max,set_6,set_7,set_8,set_9,set_10,set_11,set_12 from systemSet";
		Cursor rst = TheAccess.sqlQuery(SqlStr);
		if (rst.moveToNext()) {
			txt_p.setText(rst.getString(rst.getColumnIndex("set_p")));
			txt_d.setText(rst.getString(rst.getColumnIndex("set_d")));
			txt_s.setText(rst.getString(rst.getColumnIndex("set_s")));
			cbo_max.setSelection(rst.getInt(rst.getColumnIndex("set_max")));
			txt_6.setText(rst.getString(rst.getColumnIndex("set_6")));
			txt_7.setText(rst.getString(rst.getColumnIndex("set_7")));
			txt_8.setText(rst.getString(rst.getColumnIndex("set_8")));
			txt_9.setText(rst.getString(rst.getColumnIndex("set_9")));
			txt_10.setText(rst.getString(rst.getColumnIndex("set_10")));
			txt_11.setText(rst.getString(rst.getColumnIndex("set_11")));
			txt_12.setText(rst.getString(rst.getColumnIndex("set_12")));
		} else {
			doDefaultAction();
		}
		rst.close();
		TheAccess.close();
	}

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
		if (txt_p.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "平扣得分不能为空！");
			return;
		}
		if (txt_d.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "单扣得分不能为空！");
			return;
		}
		if (txt_s.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "双扣得分不能为空！");
			return;
		}
		if (txt_6.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "6扇贡献不能为空！");
			return;
		}
		if (txt_7.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "7扇贡献不能为空！");
			return;
		}
		if (txt_8.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "8扇贡献不能为空！");
			return;
		}
		if (txt_9.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "9扇贡献不能为空！");
			return;
		}
		if (txt_10.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "10扇贡献不能为空！");
			return;
		}
		if (txt_11.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "11扇贡献不能为空！");
			return;
		}
		if (txt_12.getText().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "12扇贡献不能为空！");
			return;
		}
		DBManager TheAccess = new DBManager(this);
		String SqlStr = "Select set_p,set_d,set_s,set_max,set_6,set_7,set_8,set_9,set_10,set_11,set_12 from systemSet";
		Cursor rst = TheAccess.sqlQuery(SqlStr);
		if (rst.moveToNext()) {
			SqlStr = "Update systemSet set set_p=" + txt_p.getText().toString() + ",set_d=" + txt_d.getText().toString() + ",set_s=" + txt_s.getText().toString() + ",set_max=" + cbo_max.getSelectedItemPosition() + ",set_6=" + txt_6.getText().toString() + ",set_7=" + txt_7.getText().toString() + ",set_8=" + txt_8.getText().toString() + ",set_9=" + txt_9.getText().toString() + ",set_10=" + txt_10.getText().toString() + ",set_11=" + txt_11.getText().toString() + ",set_12=" + txt_12.getText().toString();
		} else {
			SqlStr = "Insert into systemSet(set_p,set_d,set_s,set_max,set_6,set_7,set_8,set_9,set_10,set_11,set_12)Values(" + txt_p.getText().toString() + "," + txt_d.getText().toString() + "," + txt_s.getText().toString() + "," + cbo_max.getSelectedItemPosition() + "," + txt_6.getText().toString() + "," + txt_7.getText().toString() + "," + txt_8.getText().toString() + "," + txt_9.getText().toString() + "," + txt_10.getText().toString() + "," + txt_11.getText().toString() + "," + txt_12.getText().toString() + ")";
		}
		TheAccess.execSQL(SqlStr);
		rst.close();
		TheAccess.close();
		MsgToastUtil.MsgBox(this, "保存成功！");
		finish();
	}

	// 默认
	private void doDefaultAction () {
		txt_p.setText("1");
		txt_d.setText("2");
		txt_s.setText("3");
		cbo_max.setSelection(0);
		txt_6.setText("1");
		txt_7.setText("2");
		txt_8.setText("4");
		txt_9.setText("8");
		txt_10.setText("16");
		txt_11.setText("32");
		txt_12.setText("64");
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