package com.maitianer.shuankou.activity;

import java.util.ArrayList;

import me.dawson.kisstools.utils.DeviceUtil;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.maitianer.shuankou.R;
import com.maitianer.shuankou.adapter.ScoreAdapter;
import com.maitianer.shuankou.model.ScoreModel;
import com.maitianer.shuankou.util.DBManager;
import com.maitianer.shuankou.util.MyApplication;
import com.umeng.analytics.MobclickAgent;

public class ResultList extends Activity implements OnClickListener
{
	private TextView title;
	private Button btn_bar_left;
	private ListView list;

	private ArrayList<ScoreModel> listModels;
	private ScoreAdapter scoreAdapter;
	private int statisticsType = 0;
	private String titleString = "";

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_resultlist);
		initView();
		loadData();
	}

	// 控件初始化
	private void initView () {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			statisticsType = bundle.getInt("type");
			titleString = bundle.getString("title");
		}

		title = (TextView) findViewById(R.id.title);
		btn_bar_left = (Button) findViewById(R.id.btn_bar_left);
		list = (ListView) findViewById(R.id.list);
		listModels = new ArrayList<ScoreModel>();
		if (statisticsType == 1 || statisticsType == 3) {
			scoreAdapter = new ScoreAdapter(this, listModels, 2);
		} else {
			scoreAdapter = new ScoreAdapter(this, listModels, 1);
		}

		list.setAdapter(scoreAdapter);

		btn_bar_left.setOnClickListener(this);
	}

	// 加载数据
	private void loadData () {
		title.setText(titleString);
		btn_bar_left.setText("返回");

		getData();
	}

	// 加载数据
	private void getData () {
		DBManager TheAccess = new DBManager(this);
		Cursor rst;
		String SqlStr = "";
		float allScore = 0;
		int drawableWidth = DeviceUtil.getWidth_px() - DeviceUtil.dp2px(70);// 获取屏幕当前分辨率

		if (statisticsType == 1) {
			// 算总分
			SqlStr = "Select sum(fcount) as allCount,staffId from recordList where strftime('%j',fdate)=strftime('%j','now') Group by staffId order by allCount desc limit 1";
			rst = TheAccess.sqlQuery(SqlStr);
			if (rst.moveToNext()) {
				allScore = rst.getFloat(rst.getColumnIndex("allCount"));
			}
			rst.close();
			SqlStr = "Select b.name,sum(a.fcount) as aCount from recordList a left join playerList b on a.staffId=b.id where strftime('%j',a.fdate)=strftime('%j','now') group by b.name order by aCount desc";
			rst = TheAccess.sqlQuery(SqlStr);
			listModels.clear();
			while (rst.moveToNext()) {
				ScoreModel model = new ScoreModel(allScore, rst.getFloat(rst.getColumnIndex("aCount")), drawableWidth, rst.getString(rst.getColumnIndex("name")));
				listModels.add(model);
			}
			scoreAdapter.notifyDataSetChanged();
			rst.close();
			TheAccess.close();
		} else if (statisticsType == 2) {
			SqlStr = "Select b.name,a.fcount,a.fdate from recordList a left join playerList b on a.staffId=b.id where strftime('%j',a.fdate)=strftime('%j','now')  Order by a.fdate desc";
			rst = TheAccess.sqlQuery(SqlStr);
			while (rst.moveToNext()) {
				String sRemark = rst.getString(rst.getColumnIndex("name")) + "：" + MyApplication.FormatNumber(rst.getFloat(rst.getColumnIndex("fcount"))) + "  [" + rst.getString(rst.getColumnIndex("fdate")) + "]";
				ScoreModel model = new ScoreModel(sRemark);
				listModels.add(model);
			}
			scoreAdapter.notifyDataSetChanged();
			rst.close();
			TheAccess.close();
		} else if (statisticsType == 3) {
			// 算总分
			SqlStr = "Select sum(fcount) as allCount,staffId from recordList where fcount>0 Group by staffId order by allCount desc limit 1";
			rst = TheAccess.sqlQuery(SqlStr);
			if (rst.moveToNext()) {
				allScore = rst.getFloat(rst.getColumnIndex("allCount"));
			}
			rst.close();
			SqlStr = "Select b.name,sum(a.fcount) as aCount from recordList a left join playerList b on a.staffId=b.id group by b.name order by aCount desc";
			rst = TheAccess.sqlQuery(SqlStr);
			while (rst.moveToNext()) {
				ScoreModel model = new ScoreModel(allScore, rst.getFloat(rst.getColumnIndex("aCount")), drawableWidth, rst.getString(rst.getColumnIndex("name")));
				listModels.add(model);
			}
			scoreAdapter.notifyDataSetChanged();
			rst.close();
			TheAccess.close();
		} else if (statisticsType == 4) {
			SqlStr = "Select b.name,a.fcount,a.fdate from recordList a left join playerList b on a.staffId=b.id  Order by a.fdate desc";
			rst = TheAccess.sqlQuery(SqlStr);
			while (rst.moveToNext()) {
				String sRemark = rst.getString(rst.getColumnIndex("name")) + "：" + MyApplication.FormatNumber(rst.getFloat(rst.getColumnIndex("fcount"))) + "  [" + rst.getString(rst.getColumnIndex("fdate")) + "]";
				ScoreModel model = new ScoreModel(sRemark);
				listModels.add(model);
			}
			scoreAdapter.notifyDataSetChanged();
			rst.close();
			TheAccess.close();
		}
	}

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
