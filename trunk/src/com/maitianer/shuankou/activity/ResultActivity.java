package com.maitianer.shuankou.activity;

import java.util.ArrayList;

import me.dawson.kisstools.utils.DateTimeUtil;
import me.dawson.kisstools.utils.MsgToastUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.maitianer.shuankou.R;
import com.maitianer.shuankou.model.ResultModel;
import com.maitianer.shuankou.util.DBManager;
import com.maitianer.shuankou.util.MyApplication;
import com.umeng.analytics.MobclickAgent;

public class ResultActivity extends Activity implements OnClickListener
{
	private TextView title;
	private Button btn_bar_left, btn_bar_right;
	private TextView lblname1, lblname2, lblname3, lblname4;
	private TextView lblscore1, lblscore2, lblscore3, lblscore4;
	private TextView lblmark1, lblmark2, lblmark3, lblmark4;

	private Bundle bundle;
	private ArrayList<ResultModel> listModels;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_result);
		initView();
		loadData();
	}

	// 控件初始化
	private void initView () {
		title = (TextView) findViewById(R.id.title);
		btn_bar_left = (Button) findViewById(R.id.btn_bar_left);
		btn_bar_right = (Button) findViewById(R.id.btn_bar_right);

		lblname1 = (TextView) findViewById(R.id.lblname1);
		lblname2 = (TextView) findViewById(R.id.lblname2);
		lblname3 = (TextView) findViewById(R.id.lblname3);
		lblname4 = (TextView) findViewById(R.id.lblname4);

		lblscore1 = (TextView) findViewById(R.id.lblscore1);
		lblscore2 = (TextView) findViewById(R.id.lblscore2);
		lblscore3 = (TextView) findViewById(R.id.lblscore3);
		lblscore4 = (TextView) findViewById(R.id.lblscore4);

		lblmark1 = (TextView) findViewById(R.id.lblmark1);
		lblmark2 = (TextView) findViewById(R.id.lblmark2);
		lblmark3 = (TextView) findViewById(R.id.lblmark3);
		lblmark4 = (TextView) findViewById(R.id.lblmark4);

		btn_bar_left.setOnClickListener(this);
		btn_bar_right.setOnClickListener(this);
	}

	// 加载数据
	private void loadData () {
		title.setText("得分结果");
		btn_bar_left.setText("返回");
		btn_bar_right.setText("保存");
		listModels=new ArrayList<ResultModel>();
		bundle = getIntent().getExtras();
		if (bundle != null) {
			boolean win1=bundle.getBoolean("win1");
			boolean win2=bundle.getBoolean("win2");
			boolean win3=bundle.getBoolean("win3");
			boolean win4=bundle.getBoolean("win4");
			
			boolean add1=false,add2=false,add3=false,add4=false;
			
			for (int i = 0; i < 2; i++) {
				if (win1 && !add1) {
					listModels.add(new ResultModel(bundle.getInt("id1"), bundle.getString("name1"), MyApplication.FormatNumber(bundle.getFloat("score1")), bundle.getString("mark1")));
					add1=true;
				}else if (win2 && !add2) {
					listModels.add(new ResultModel(bundle.getInt("id2"), bundle.getString("name2"), MyApplication.FormatNumber(bundle.getFloat("score2")), bundle.getString("mark2")));
					add2=true;
				}else if (win3 && !add3) {
					listModels.add(new ResultModel(bundle.getInt("id3"), bundle.getString("name3"), MyApplication.FormatNumber(bundle.getFloat("score3")), bundle.getString("mark3")));
					add3=true;
				}else if (win4 && !add4) {
					listModels.add(new ResultModel(bundle.getInt("id4"), bundle.getString("name4"), MyApplication.FormatNumber(bundle.getFloat("score4")), bundle.getString("mark4")));
					add4=true;
				}
			}

			for (int i = 0; i < 2; i++) {
				if (!add1) {
					listModels.add(new ResultModel(bundle.getInt("id1"), bundle.getString("name1"), MyApplication.FormatNumber(bundle.getFloat("score1")), bundle.getString("mark1")));
					add1=true;
				}else if (!add2) {
					listModels.add(new ResultModel(bundle.getInt("id2"), bundle.getString("name2"), MyApplication.FormatNumber(bundle.getFloat("score2")), bundle.getString("mark2")));
					add2=true;
				}else if (!add3) {
					listModels.add(new ResultModel(bundle.getInt("id3"), bundle.getString("name3"), MyApplication.FormatNumber(bundle.getFloat("score3")), bundle.getString("mark3")));
					add3=true;
				}else if (!add4) {
					listModels.add(new ResultModel(bundle.getInt("id4"), bundle.getString("name4"), MyApplication.FormatNumber(bundle.getFloat("score4")), bundle.getString("mark4")));
					add4=true;
				}
			}
			
			for (int i = 0; i < listModels.size(); i++) {
				ResultModel model=listModels.get(i);
				switch (i) {
					case 0:
						lblname1.setText(model.getName());
						lblscore1.setText(model.getScore());
						lblmark1.setText(model.getMark());
						break;
					case 1:
						lblname2.setText(model.getName());
						lblscore2.setText(model.getScore());
						lblmark2.setText(model.getMark());
						break;
					case 2:
						lblname3.setText(model.getName());
						lblscore3.setText(model.getScore());
						lblmark3.setText(model.getMark());
						break;
					case 3:
						lblname4.setText(model.getName());
						lblscore4.setText(model.getScore());
						lblmark4.setText(model.getMark());
						break;

					default:
						break;
				}
			}
		}
	}

	public void onClick (View arg0) {
		switch (arg0.getId()) {
			case R.id.btn_bar_left:
				finish();
				break;
			case R.id.btn_bar_right:
				doSave();
				break;

			default:
				break;
		}
	}

	private void doSave () {
		DBManager TheAccess = new DBManager(this);
		ArrayList<String> pSqlStrings = new ArrayList<String>();
		String panKey = DateTimeUtil.format(DateTimeUtil.getNowDateTime(), "yyyyMMdd-HHmmss");
		String fdate = DateTimeUtil.getDateTime(DateTimeUtil.getNowDateTime());
		for (int i = 0; i <listModels.size(); i++) {
			ResultModel model=listModels.get(i);
			pSqlStrings.add("Insert into recordList(panId,staffId,fdate,findex,fcount,remark)Values('" + panKey + "'," + model.getId() + ",'" + fdate + "',"+(i+1)+"," + model.getScore() + ",'" + model.getMark() + "')");
		}
		TheAccess.execSQLByTran(pSqlStrings);
		TheAccess.close();
		MainActivity.instance.doClear();
		MsgToastUtil.MsgBox(this, "保存成功");
		finish();
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
