package com.maitianer.shuankou.activity;

import java.util.ArrayList;

import me.dawson.kisstools.utils.FileUtil;
import me.dawson.kisstools.utils.MsgToastUtil;
import me.dawson.kisstools.utils.MsgToastUtil.MsgDelogCallback;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maitianer.shuankou.R;
import com.maitianer.shuankou.adapter.PlayerAdapter;
import com.maitianer.shuankou.model.PlayerModel;
import com.maitianer.shuankou.util.DBManager;
import com.maitianer.shuankou.util.MyApplication;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener
{
	private DrawerLayout mDrawerLayout;
	public static MainActivity instance = null;

	private RadioButton radio_p, radio_d, radio_s;
	private CheckBox chkwin1, chkwin2, chkwin3, chkwin4;
	private TextView title;
	private Button btn_bar_left, btn_bar_right;
	private Button btn_user1, btn_user2, btn_user3, btn_user4, btn_clear, btn_result, btn_cancel;
	private CheckBox chk1_5, chk1_6, chk1_7, chk1_8, chk1_9, chk1_10, chk1_11, chk1_12;
	private CheckBox chk2_5, chk2_6, chk2_7, chk2_8, chk2_9, chk2_10, chk2_11, chk2_12;
	private CheckBox chk3_5, chk3_6, chk3_7, chk3_8, chk3_9, chk3_10, chk3_11, chk3_12;
	private CheckBox chk4_5, chk4_6, chk4_7, chk4_8, chk4_9, chk4_10, chk4_11, chk4_12;
	private ListView list_user, list_left, list_right;
	private RelativeLayout layout_left, layout_right, layout_select, layout_bottom;

	private ArrayList<PlayerModel> playerModels;
	private ArrayList<PlayerModel> leftModels;
	private ArrayList<PlayerModel> rightModels;
	private PlayerAdapter playerAdapter;
	private PlayerAdapter leftMenuAdapter;
	private PlayerAdapter rightMenuAdapter;
	private int showSelectUserIndex = 0;
	private long mkeyTime = 0;

	public String titleString;
	public int statisticsType;
	// 系统设置信息变量
	private float set_p = 1;// 平扣基础分
	private float set_d = 2;// 单扣基础分
	private float set_s = 3;// 双扣基础分

	private float set_6 = 1;// 6扇贡献分
	private float set_7 = 2;// 7扇贡献分
	private float set_8 = 4;// 8扇贡献分
	private float set_9 = 8;// 9扇贡献分
	private float set_10 = 16;// 10扇贡献分
	private float set_11 = 32;// 11扇贡献分
	private float set_12 = 64;// 12扇贡献分
	private int set_max = 0;// 最高翻至

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_main);
		initView();
		loadData();
	}

	// 控件初始化
	private void initView () {
		instance = this;
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		title = (TextView) findViewById(R.id.title);
		btn_bar_left = (Button) findViewById(R.id.btn_bar_left);
		btn_bar_right = (Button) findViewById(R.id.btn_bar_right);

		radio_p = (RadioButton) findViewById(R.id.radio_p);
		radio_d = (RadioButton) findViewById(R.id.radio_d);
		radio_s = (RadioButton) findViewById(R.id.radio_s);

		chkwin1 = (CheckBox) findViewById(R.id.chkwin1);
		chkwin2 = (CheckBox) findViewById(R.id.chkwin2);
		chkwin3 = (CheckBox) findViewById(R.id.chkwin3);
		chkwin4 = (CheckBox) findViewById(R.id.chkwin4);

		btn_user1 = (Button) findViewById(R.id.btn_user1);
		btn_user2 = (Button) findViewById(R.id.btn_user2);
		btn_user3 = (Button) findViewById(R.id.btn_user3);
		btn_user4 = (Button) findViewById(R.id.btn_user4);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_result = (Button) findViewById(R.id.btn_result);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);

		chk1_5 = (CheckBox) findViewById(R.id.chkwin1_5);
		chk1_6 = (CheckBox) findViewById(R.id.chkwin1_6);
		chk1_7 = (CheckBox) findViewById(R.id.chkwin1_7);
		chk1_8 = (CheckBox) findViewById(R.id.chkwin1_8);
		chk1_9 = (CheckBox) findViewById(R.id.chkwin1_9);
		chk1_10 = (CheckBox) findViewById(R.id.chkwin1_10);
		chk1_11 = (CheckBox) findViewById(R.id.chkwin1_11);
		chk1_12 = (CheckBox) findViewById(R.id.chkwin1_12);

		chk2_5 = (CheckBox) findViewById(R.id.chkwin2_5);
		chk2_6 = (CheckBox) findViewById(R.id.chkwin2_6);
		chk2_7 = (CheckBox) findViewById(R.id.chkwin2_7);
		chk2_8 = (CheckBox) findViewById(R.id.chkwin2_8);
		chk2_9 = (CheckBox) findViewById(R.id.chkwin2_9);
		chk2_10 = (CheckBox) findViewById(R.id.chkwin2_10);
		chk2_11 = (CheckBox) findViewById(R.id.chkwin2_11);
		chk2_12 = (CheckBox) findViewById(R.id.chkwin2_12);

		chk3_5 = (CheckBox) findViewById(R.id.chkLose1_5);
		chk3_6 = (CheckBox) findViewById(R.id.chkLose1_6);
		chk3_7 = (CheckBox) findViewById(R.id.chkLose1_7);
		chk3_8 = (CheckBox) findViewById(R.id.chkLose1_8);
		chk3_9 = (CheckBox) findViewById(R.id.chkLose1_9);
		chk3_10 = (CheckBox) findViewById(R.id.chkLose1_10);
		chk3_11 = (CheckBox) findViewById(R.id.chkLose1_11);
		chk3_12 = (CheckBox) findViewById(R.id.chkLose1_12);

		chk4_5 = (CheckBox) findViewById(R.id.chkLose2_5);
		chk4_6 = (CheckBox) findViewById(R.id.chkLose2_6);
		chk4_7 = (CheckBox) findViewById(R.id.chkLose2_7);
		chk4_8 = (CheckBox) findViewById(R.id.chkLose2_8);
		chk4_9 = (CheckBox) findViewById(R.id.chkLose2_9);
		chk4_10 = (CheckBox) findViewById(R.id.chkLose2_10);
		chk4_11 = (CheckBox) findViewById(R.id.chkLose2_11);
		chk4_12 = (CheckBox) findViewById(R.id.chkLose2_12);

		list_user = (ListView) findViewById(R.id.list_user);
		list_left = (ListView) findViewById(R.id.list_left);
		list_right = (ListView) findViewById(R.id.list_right);

		layout_select = (RelativeLayout) findViewById(R.id.layout_select);
		layout_bottom = (RelativeLayout) findViewById(R.id.layout_bottom);
		layout_left = (RelativeLayout) findViewById(R.id.layout_left);
		layout_right = (RelativeLayout) findViewById(R.id.layout_right);

		playerModels = new ArrayList<PlayerModel>();
		leftModels = new ArrayList<PlayerModel>();
		rightModels = new ArrayList<PlayerModel>();
		leftMenuAdapter = new PlayerAdapter(this, leftModels, 1);
		rightMenuAdapter = new PlayerAdapter(this, rightModels, 2);
		playerAdapter = new PlayerAdapter(this, playerModels, 3);
		list_left.setAdapter(leftMenuAdapter);
		list_right.setAdapter(rightMenuAdapter);
		list_user.setAdapter(playerAdapter);

		btn_bar_left.setOnClickListener(this);
		btn_bar_right.setOnClickListener(this);
		btn_user1.setOnClickListener(this);
		btn_user2.setOnClickListener(this);
		btn_user3.setOnClickListener(this);
		btn_user4.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_result.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		list_left.setOnItemClickListener(this);
		list_right.setOnItemClickListener(this);
		list_user.setOnItemClickListener(this);
	}

	// 加载数据
	private void loadData () {
		title.setText("双扣算分器");
		btn_bar_left.setText("设置");
		btn_bar_right.setText("查询");
		// 左边列表
		leftModels.add(new PlayerModel("基础分设置"));
		leftModels.add(new PlayerModel("玩家管理"));
		leftModels.add(new PlayerModel("清空数据"));
		leftModels.add(new PlayerModel("关于"));
		leftMenuAdapter.notifyDataSetChanged();

		// 右边列表
		rightModels.add(new PlayerModel("当日输赢分数统计"));
		rightModels.add(new PlayerModel("当日记录明细查询"));
		rightModels.add(new PlayerModel("全部输赢分数统计"));
		rightModels.add(new PlayerModel("全部记录明细查询"));
		rightMenuAdapter.notifyDataSetChanged();

		// 中间内容
		int playerId = FileUtil.readSharedInteger(MyApplication.SHARED_MAIN, MyApplication.KEY_PLAYERID1);
		String playerName = FileUtil.readSharedString(MyApplication.SHARED_MAIN, MyApplication.KEY_PLAYERNAME1);
		if (playerId == 0) {
			btn_user1.setTag(1);
			btn_user1.setText("玩家(1)");
		} else {
			btn_user1.setTag(playerId);
			btn_user1.setText(playerName);
		}
		playerId = FileUtil.readSharedInteger(MyApplication.SHARED_MAIN, MyApplication.KEY_PLAYERID2);
		playerName = FileUtil.readSharedString(MyApplication.SHARED_MAIN, MyApplication.KEY_PLAYERNAME2);
		if (playerId == 0) {
			btn_user2.setTag(2);
			btn_user2.setText("玩家(2)");
		} else {
			btn_user2.setTag(playerId);
			btn_user2.setText(playerName);
		}
		playerId = FileUtil.readSharedInteger(MyApplication.SHARED_MAIN, MyApplication.KEY_PLAYERID3);
		playerName = FileUtil.readSharedString(MyApplication.SHARED_MAIN, MyApplication.KEY_PLAYERNAME3);
		if (playerId == 0) {
			btn_user3.setTag(3);
			btn_user3.setText("玩家(3)");
		} else {
			btn_user3.setTag(playerId);
			btn_user3.setText(playerName);
		}
		playerId = FileUtil.readSharedInteger(MyApplication.SHARED_MAIN, MyApplication.KEY_PLAYERID4);
		playerName = FileUtil.readSharedString(MyApplication.SHARED_MAIN, MyApplication.KEY_PLAYERNAME4);
		if (playerId == 0) {
			btn_user4.setTag(4);
			btn_user4.setText("玩家(4)");
		} else {
			btn_user4.setTag(playerId);
			btn_user4.setText(playerName);
		}

		doClear();
	}

	@Override
	public void onItemClick (AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent;
		Bundle bundle;
		showContent();
		switch (arg0.getId()) {
			case R.id.list_left:
				switch (arg2) {
					case 0:// 基础分设置
						intent = new Intent(this, SystemSetActivity.class);
						startActivity(intent);
						break;
					case 1:// 玩家管理
						intent = new Intent(this, PlayerList.class);
						startActivity(intent);
						break;
					case 2:// 清空数据
						MsgToastUtil.MsgBox(this, "确定要清空所有数据吗？", MsgToastUtil.Msg_2Button, doClearData);
						break;
					case 3:// 关于
						intent = new Intent(this, AboutActivity.class);
						startActivity(intent);
						break;

					default:
						break;
				}
				break;
			case R.id.list_right:
				bundle = new Bundle();
				bundle.putInt("type", arg2 + 1);
				bundle.putString("title", rightModels.get(arg2).getName());
				intent = new Intent(this, ResultList.class);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.list_user:
				PlayerModel model = playerModels.get(arg2);
				switch (showSelectUserIndex) {
					case 1:
						btn_user1.setTag(model.getId());
						btn_user1.setText(model.getName());
						FileUtil.writeShared(MyApplication.SHARED_MAIN, model.getId(), MyApplication.KEY_PLAYERID1);
						FileUtil.writeShared(MyApplication.SHARED_MAIN, model.getName(), MyApplication.KEY_PLAYERNAME1);
						break;
					case 2:
						btn_user2.setTag(model.getId());
						btn_user2.setText(model.getName());
						FileUtil.writeShared(MyApplication.SHARED_MAIN, model.getId(), MyApplication.KEY_PLAYERID2);
						FileUtil.writeShared(MyApplication.SHARED_MAIN, model.getName(), MyApplication.KEY_PLAYERNAME2);
						break;
					case 3:
						btn_user3.setTag(model.getId());
						btn_user3.setText(model.getName());
						FileUtil.writeShared(MyApplication.SHARED_MAIN, model.getId(), MyApplication.KEY_PLAYERID3);
						FileUtil.writeShared(MyApplication.SHARED_MAIN, model.getName(), MyApplication.KEY_PLAYERNAME3);
						break;
					case 4:
						btn_user4.setTag(model.getId());
						btn_user4.setText(model.getName());
						FileUtil.writeShared(MyApplication.SHARED_MAIN, model.getId(), MyApplication.KEY_PLAYERID4);
						FileUtil.writeShared(MyApplication.SHARED_MAIN, model.getName(), MyApplication.KEY_PLAYERNAME4);
						break;

					default:
						break;
				}
				layout_select.setVisibility(View.GONE);
				layout_bottom.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}

	@SuppressLint("InlinedApi")
	@Override
	public void onClick (View arg0) {
		switch (arg0.getId()) {
			case R.id.btn_bar_left:
				showLeftMenu();
				break;
			case R.id.btn_bar_right:
				showRightMenu();
				break;
			case R.id.btn_user1:
				showPlayerList(1);
				break;
			case R.id.btn_user2:
				showPlayerList(2);
				break;
			case R.id.btn_user3:
				showPlayerList(3);
				break;
			case R.id.btn_user4:
				showPlayerList(4);
				break;
			case R.id.btn_clear:
				doClear();
				break;
			case R.id.btn_result:
				doResult();
				break;
			case R.id.btn_cancel:
				layout_select.setVisibility(View.GONE);
				layout_bottom.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}

	}

	// 显示/隐藏 侧边栏
	@SuppressLint("InlinedApi")
	public void showContent () {
		if (mDrawerLayout.isDrawerOpen(layout_left)) {
			mDrawerLayout.closeDrawer(Gravity.START);
		} else if (mDrawerLayout.isDrawerOpen(layout_right)) {
			mDrawerLayout.closeDrawer(Gravity.END);
		}
	}

	@SuppressLint("InlinedApi")
	private void showLeftMenu () {
		if (mDrawerLayout.isDrawerOpen(layout_left)) {
			mDrawerLayout.closeDrawer(Gravity.START);
		} else {
			mDrawerLayout.openDrawer(Gravity.START);
		}
	}

	@SuppressLint("InlinedApi")
	private void showRightMenu () {
		if (mDrawerLayout.isDrawerOpen(layout_right)) {
			mDrawerLayout.closeDrawer(Gravity.END);
		} else {
			mDrawerLayout.openDrawer(Gravity.END);
		}
	}

	private void showPlayerList (int indexFlag) {
		showSelectUserIndex = indexFlag;
		String SqlStr = "Select id,name from playerList where closed=0 order by id desc";
		DBManager TheAccess = new DBManager(this);
		Cursor rst = TheAccess.sqlQuery(SqlStr);
		playerModels.clear();
		while (rst.moveToNext()) {
			PlayerModel model = new PlayerModel(rst.getInt(rst.getColumnIndex("id")), rst.getString(rst.getColumnIndex("name")));
			playerModels.add(model);
		}
		rst.close();
		TheAccess.close();
		playerAdapter.notifyDataSetChanged();
		layout_select.setVisibility(View.VISIBLE);
		layout_bottom.setVisibility(View.GONE);
	}

	// 计算
	private void doResult () {
		// 读取参数
		if (btn_user1.getTag().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "请选择玩家(1)");
			return;
		}
		if (btn_user2.getTag().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "请选择玩家(2)");
			return;
		}
		if (btn_user3.getTag().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "请选择玩家(3)");
			return;
		}
		if (btn_user4.getTag().toString().equals("")) {
			MsgToastUtil.MsgBox(this, "请选择玩家(4)");
			return;
		}
		if (btn_user1.getTag().toString().equals(btn_user2.getTag().toString())) {
			MsgToastUtil.MsgBox(this, "玩家(1) 与 玩家(2) 不能为同一个人！");
			return;
		}
		if (btn_user1.getTag().toString().equals(btn_user3.getTag().toString())) {
			MsgToastUtil.MsgBox(this, "赢家(1) 与 输家(1) 不能为同一个人！");
			return;
		}
		if (btn_user1.getTag().toString().equals(btn_user4.getTag().toString())) {
			MsgToastUtil.MsgBox(this, "玩家(1) 与 玩家(3) 不能为同一个人！");
			return;
		}
		if (btn_user2.getTag().toString().equals(btn_user3.getTag().toString())) {
			MsgToastUtil.MsgBox(this, "玩家(2) 与 玩家(3) 不能为同一个人！");
			return;
		}
		if (btn_user2.getTag().toString().equals(btn_user4.getTag().toString())) {
			MsgToastUtil.MsgBox(this, "玩家(2) 与 玩家(4) 不能为同一个人！");
			return;
		}
		if (btn_user3.getTag().toString().equals(btn_user4.getTag().toString())) {
			MsgToastUtil.MsgBox(this, "玩家(3) 与 玩家(4) 不能为同一个人！");
			return;
		}
		int winNum = 0;
		if (chkwin1.isChecked()) {
			winNum++;
		}
		if (chkwin2.isChecked()) {
			winNum++;
		}
		if (chkwin3.isChecked()) {
			winNum++;
		}
		if (chkwin4.isChecked()) {
			winNum++;
		}
		if (winNum != 2) {
			MsgToastUtil.MsgBox(this, "请选择2位赢家！");
			return;
		}

		DBManager TheAccess = new DBManager(this);
		String SqlStr = "Select set_p,set_d,set_s,set_max,set_6,set_7,set_8,set_9,set_10,set_11,set_12 from systemSet";
		Cursor rst = TheAccess.sqlQuery(SqlStr);
		if (rst.moveToNext()) {
			set_p = rst.getFloat(rst.getColumnIndex("set_p"));
			set_d = rst.getFloat(rst.getColumnIndex("set_d"));
			set_s = rst.getFloat(rst.getColumnIndex("set_s"));

			set_max = rst.getInt(rst.getColumnIndex("set_max"));

			set_6 = rst.getFloat(rst.getColumnIndex("set_6"));
			set_7 = rst.getFloat(rst.getColumnIndex("set_7"));
			set_8 = rst.getFloat(rst.getColumnIndex("set_8"));
			set_9 = rst.getFloat(rst.getColumnIndex("set_9"));
			set_10 = rst.getFloat(rst.getColumnIndex("set_10"));
			set_11 = rst.getFloat(rst.getColumnIndex("set_11"));
			set_12 = rst.getFloat(rst.getColumnIndex("set_12"));
		}
		rst.close();
		TheAccess.close();

		// 界面数据变量
		int nS = 0;// 倍数
		float nAlt = 0;// 牌面得分
		String sAlt = "";

		float nRlt1 = 0;// 玩家一进供
		String sRlt1 = "";
		float nRlt2 = 0;// 玩家二进供
		String sRlt2 = "";
		float nRlt3 = 0;// 玩家三进供
		String sRlt3 = "";
		float nRlt4 = 0;// 玩家四进供
		String sRlt4 = "";

		float nGet1 = 0;// 玩家一得分
		String sGet1 = "";
		float nGet2 = 0;// 玩家二得分
		String sGet2 = "";
		float nGet3 = 0;// 玩家三得分
		String sGet3 = "";
		float nGet4 = 0;// 玩家四得分
		String sGet4 = "";

		if (chkwin1.isChecked()) {
			if (chk1_5.isChecked()) {
				nS = 1;
			}
			if (chk1_6.isChecked()) {
				nS = 2;
			}
			if (chk1_7.isChecked()) {
				nS = 3;
			}
			if (chk1_8.isChecked()) {
				nS = 4;
			}
			if (chk1_9.isChecked()) {
				nS = 5;
			}
			if (chk1_10.isChecked()) {
				nS = 6;
			}
			if (chk1_11.isChecked()) {
				nS = 7;
			}
			if (chk1_12.isChecked()) {
				nS = 8;
			}
		}
		if (chkwin2.isChecked()) {
			if (chk2_5.isChecked() && nS < 1) {
				nS = 1;
			}
			if (chk2_6.isChecked() && nS < 2) {
				nS = 2;
			}
			if (chk2_7.isChecked() && nS < 3) {
				nS = 3;
			}
			if (chk2_8.isChecked() && nS < 4) {
				nS = 4;
			}
			if (chk2_9.isChecked() && nS < 5) {
				nS = 5;
			}
			if (chk2_10.isChecked() && nS < 6) {
				nS = 6;
			}
			if (chk2_11.isChecked() && nS < 7) {
				nS = 7;
			}
			if (chk2_12.isChecked() && nS < 8) {
				nS = 8;
			}
		}
		if (chkwin3.isChecked()) {
			if (chk3_5.isChecked() && nS < 1) {
				nS = 1;
			}
			if (chk3_6.isChecked() && nS < 2) {
				nS = 2;
			}
			if (chk3_7.isChecked() && nS < 3) {
				nS = 3;
			}
			if (chk3_8.isChecked() && nS < 4) {
				nS = 4;
			}
			if (chk3_9.isChecked() && nS < 5) {
				nS = 5;
			}
			if (chk3_10.isChecked() && nS < 6) {
				nS = 6;
			}
			if (chk3_11.isChecked() && nS < 7) {
				nS = 7;
			}
			if (chk3_12.isChecked() && nS < 8) {
				nS = 8;
			}
		}
		if (chkwin4.isChecked()) {
			if (chk4_5.isChecked() && nS < 1) {
				nS = 1;
			}
			if (chk4_6.isChecked() && nS < 2) {
				nS = 2;
			}
			if (chk4_7.isChecked() && nS < 3) {
				nS = 3;
			}
			if (chk4_8.isChecked() && nS < 4) {
				nS = 4;
			}
			if (chk4_9.isChecked() && nS < 5) {
				nS = 5;
			}
			if (chk4_10.isChecked() && nS < 6) {
				nS = 6;
			}
			if (chk4_11.isChecked() && nS < 7) {
				nS = 7;
			}
			if (chk4_12.isChecked() && nS < 8) {
				nS = 8;
			}
		}

		if (set_max != 0 && nS > set_max) {
			nS = set_max;
		}

		// 算牌面分
		if (radio_p.isChecked()) {
			nAlt = (float) (set_p * Math.pow(2, nS));
			sAlt = MyApplication.FormatNumber(set_p) + "x" + MyApplication.FormatNumber((float) Math.pow(2, nS));
		} else if (radio_d.isChecked()) {
			nAlt = (float) (set_d * Math.pow(2, nS));
			sAlt = MyApplication.FormatNumber(set_d) + "x" + MyApplication.FormatNumber((float) Math.pow(2, nS));
		} else if (radio_s.isChecked()) {
			nAlt = (float) (set_s * Math.pow(2, nS));
			sAlt = MyApplication.FormatNumber(set_s) + "x" + MyApplication.FormatNumber((float) Math.pow(2, nS));
		}
		// 算玩家进供
		// 赢家一
		if (chk1_6.isChecked()) {
			nRlt1 = nRlt1 + set_6;
		}
		if (chk1_7.isChecked()) {
			nRlt1 = nRlt1 + set_7;
		}
		if (chk1_8.isChecked()) {
			nRlt1 = nRlt1 + set_8;
		}
		if (chk1_9.isChecked()) {
			nRlt1 = nRlt1 + set_9;
		}
		if (chk1_10.isChecked()) {
			nRlt1 = nRlt1 + set_10;
		}
		if (chk1_11.isChecked()) {
			nRlt1 = nRlt1 + set_11;
		}
		if (chk1_12.isChecked()) {
			nRlt1 = nRlt1 + set_12;
		}
		// 赢家二
		if (chk2_6.isChecked()) {
			nRlt2 = nRlt2 + set_6;
		}
		if (chk2_7.isChecked()) {
			nRlt2 = nRlt2 + set_7;
		}
		if (chk2_8.isChecked()) {
			nRlt2 = nRlt2 + set_8;
		}
		if (chk2_9.isChecked()) {
			nRlt2 = nRlt2 + set_9;
		}
		if (chk2_10.isChecked()) {
			nRlt2 = nRlt2 + set_10;
		}
		if (chk2_11.isChecked()) {
			nRlt2 = nRlt2 + set_11;
		}
		if (chk2_12.isChecked()) {
			nRlt2 = nRlt2 + set_12;
		}
		// 输家一
		if (chk3_6.isChecked()) {
			nRlt3 = nRlt3 + set_6;
		}
		if (chk3_7.isChecked()) {
			nRlt3 = nRlt3 + set_7;
		}
		if (chk3_8.isChecked()) {
			nRlt3 = nRlt3 + set_8;
		}
		if (chk3_9.isChecked()) {
			nRlt3 = nRlt3 + set_9;
		}
		if (chk3_10.isChecked()) {
			nRlt3 = nRlt3 + set_10;
		}
		if (chk3_11.isChecked()) {
			nRlt3 = nRlt3 + set_11;
		}
		if (chk3_12.isChecked()) {
			nRlt3 = nRlt3 + set_12;
		}
		// 输家二
		if (chk4_6.isChecked()) {
			nRlt4 = nRlt4 + set_6;
		}
		if (chk4_7.isChecked()) {
			nRlt4 = nRlt4 + set_7;
		}
		if (chk4_8.isChecked()) {
			nRlt4 = nRlt4 + set_8;
		}
		if (chk4_9.isChecked()) {
			nRlt4 = nRlt4 + set_9;
		}
		if (chk4_10.isChecked()) {
			nRlt4 = nRlt4 + set_10;
		}
		if (chk4_11.isChecked()) {
			nRlt4 = nRlt4 + set_11;
		}
		if (chk4_12.isChecked()) {
			nRlt4 = nRlt4 + set_12;
		}

		// 算得分
		sRlt1 = MyApplication.FormatNumber(nRlt1);
		sRlt2 = MyApplication.FormatNumber(nRlt2);
		sRlt3 = MyApplication.FormatNumber(nRlt3);
		sRlt4 = MyApplication.FormatNumber(nRlt4);

		if (chkwin1.isChecked()) {
			nGet1 = nRlt1 * 3 - nRlt2 - nRlt3 - nRlt4 + nAlt;
			sGet1 = ((nRlt1 == 0) ? "" : "(" + sRlt1 + ")x3") + ((nRlt2 == 0) ? "" : "-" + sRlt2) + ((nRlt3 == 0) ? "" : "-" + sRlt3) + ((nRlt4 == 0) ? "" : "-" + sRlt4) + "+" + sAlt;
		} else {
			nGet1 = nRlt1 * 3 - nRlt2 - nRlt3 - nRlt4 - nAlt;
			sGet1 = ((nRlt1 == 0) ? "" : "(" + sRlt1 + ")x3") + ((nRlt2 == 0) ? "" : "-" + sRlt2) + ((nRlt3 == 0) ? "" : "-" + sRlt3) + ((nRlt4 == 0) ? "" : "-" + sRlt4) + "-" + sAlt;
		}

		if (chkwin2.isChecked()) {
			nGet2 = nRlt2 * 3 - nRlt1 - nRlt3 - nRlt4 + nAlt;
			sGet2 = ((nRlt2 == 0) ? "" : "(" + sRlt2 + ")x3") + ((nRlt1 == 0) ? "" : "-" + sRlt1) + ((nRlt3 == 0) ? "" : "-" + sRlt3) + ((nRlt4 == 0) ? "" : "-" + sRlt4) + "+" + sAlt;
		} else {
			nGet2 = nRlt2 * 3 - nRlt1 - nRlt3 - nRlt4 - nAlt;
			sGet2 = ((nRlt2 == 0) ? "" : "(" + sRlt2 + ")x3") + ((nRlt1 == 0) ? "" : "-" + sRlt1) + ((nRlt3 == 0) ? "" : "-" + sRlt3) + ((nRlt4 == 0) ? "" : "-" + sRlt4) + "-" + sAlt;
		}

		if (chkwin3.isChecked()) {
			nGet3 = nRlt3 * 3 - nRlt1 - nRlt2 - nRlt4 + nAlt;
			sGet3 = ((nRlt3 == 0) ? "" : "(" + sRlt3 + ")x3") + ((nRlt1 == 0) ? "" : "-" + sRlt1) + ((nRlt2 == 0) ? "" : "-" + sRlt2) + ((nRlt4 == 0) ? "" : "-" + sRlt4) + "+" + sAlt;
		} else {
			nGet3 = nRlt3 * 3 - nRlt1 - nRlt2 - nRlt4 - nAlt;
			sGet3 = ((nRlt3 == 0) ? "" : "(" + sRlt3 + ")x3") + ((nRlt1 == 0) ? "" : "-" + sRlt1) + ((nRlt2 == 0) ? "" : "-" + sRlt2) + ((nRlt4 == 0) ? "" : "-" + sRlt4) + "-" + sAlt;
		}

		if (chkwin4.isChecked()) {
			nGet4 = nRlt4 * 3 - nRlt1 - nRlt2 - nRlt3 + nAlt;
			sGet4 = ((nRlt4 == 0) ? "" : "(" + sRlt4 + ")x3") + ((nRlt1 == 0) ? "" : "-" + sRlt1) + ((nRlt2 == 0) ? "" : "-" + sRlt2) + ((nRlt3 == 0) ? "" : "-" + sRlt3) + "+" + sAlt;
		} else {
			nGet4 = nRlt4 * 3 - nRlt1 - nRlt2 - nRlt3 - nAlt;
			sGet4 = ((nRlt4 == 0) ? "" : "(" + sRlt4 + ")x3") + ((nRlt1 == 0) ? "" : "-" + sRlt1) + ((nRlt2 == 0) ? "" : "-" + sRlt2) + ((nRlt3 == 0) ? "" : "-" + sRlt3) + "-" + sAlt;
		}

		Bundle bundle = new Bundle();
		bundle.putBoolean("win1", chkwin1.isChecked());
		bundle.putBoolean("win2", chkwin2.isChecked());
		bundle.putBoolean("win3", chkwin3.isChecked());
		bundle.putBoolean("win4", chkwin4.isChecked());
		
		bundle.putInt("id1", (int) btn_user1.getTag());
		bundle.putInt("id2", (int) btn_user2.getTag());
		bundle.putInt("id3", (int) btn_user3.getTag());
		bundle.putInt("id4", (int) btn_user4.getTag());

		bundle.putString("name1", btn_user1.getText().toString());
		bundle.putString("name2", btn_user2.getText().toString());
		bundle.putString("name3", btn_user3.getText().toString());
		bundle.putString("name4", btn_user4.getText().toString());

		bundle.putString("mark1", sGet1);
		bundle.putString("mark2", sGet2);
		bundle.putString("mark3", sGet3);
		bundle.putString("mark4", sGet4);

		bundle.putFloat("score1", nGet1);
		bundle.putFloat("score2", nGet2);
		bundle.putFloat("score3", nGet3);
		bundle.putFloat("score4", nGet4);
		Intent intent = new Intent(this, ResultActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	// 清空
	public void doClear () {
		radio_p.setChecked(true);

		chkwin1.setChecked(true);
		chkwin2.setChecked(true);
		chkwin3.setChecked(false);
		chkwin4.setChecked(false);

		chk1_5.setChecked(false);
		chk1_6.setChecked(false);
		chk1_7.setChecked(false);
		chk1_8.setChecked(false);
		chk1_9.setChecked(false);
		chk1_10.setChecked(false);
		chk1_11.setChecked(false);
		chk1_12.setChecked(false);

		chk2_5.setChecked(false);
		chk2_6.setChecked(false);
		chk2_7.setChecked(false);
		chk2_8.setChecked(false);
		chk2_9.setChecked(false);
		chk2_10.setChecked(false);
		chk2_11.setChecked(false);
		chk2_12.setChecked(false);

		chk3_5.setChecked(false);
		chk3_6.setChecked(false);
		chk3_7.setChecked(false);
		chk3_8.setChecked(false);
		chk3_9.setChecked(false);
		chk3_10.setChecked(false);
		chk3_11.setChecked(false);
		chk3_12.setChecked(false);

		chk4_5.setChecked(false);
		chk4_6.setChecked(false);
		chk4_7.setChecked(false);
		chk4_8.setChecked(false);
		chk4_9.setChecked(false);
		chk4_10.setChecked(false);
		chk4_11.setChecked(false);
		chk4_12.setChecked(false);
	}

	MsgDelogCallback doClearData = new MsgDelogCallback() {
		public void DoSomething_ChickOK () {
			DBManager TheAccess = new DBManager(MainActivity.this);
			ArrayList<String> pSqlStr = new ArrayList<String>();
			String SqlStr = "Delete from playerList where id>4";
			pSqlStr.add(SqlStr);
			SqlStr = "Delete from recordList";
			pSqlStr.add(SqlStr);
			SqlStr = "Update systemSet set set_p=1,set_d=2,set_s=3,set_max=0,set_6=1,set_7=2,set_8=4,set_9=8,set_10=16,set_11=32,set_12=64";
			pSqlStr.add(SqlStr);
			TheAccess.execSQLByTran(pSqlStr);
			TheAccess.close();

			FileUtil.writeShared(MyApplication.SHARED_MAIN, 0, MyApplication.KEY_PLAYERID1);
			FileUtil.writeShared(MyApplication.SHARED_MAIN, 0, MyApplication.KEY_PLAYERID2);
			FileUtil.writeShared(MyApplication.SHARED_MAIN, 0, MyApplication.KEY_PLAYERID3);
			FileUtil.writeShared(MyApplication.SHARED_MAIN, 0, MyApplication.KEY_PLAYERID4);

			FileUtil.writeShared(MyApplication.SHARED_MAIN, "", MyApplication.KEY_PLAYERNAME1);
			FileUtil.writeShared(MyApplication.SHARED_MAIN, "", MyApplication.KEY_PLAYERNAME2);
			FileUtil.writeShared(MyApplication.SHARED_MAIN, "", MyApplication.KEY_PLAYERNAME3);
			FileUtil.writeShared(MyApplication.SHARED_MAIN, "", MyApplication.KEY_PLAYERNAME4);
			MsgToastUtil.MsgBox(MainActivity.this, "保存成功！");
		}
	};

	@SuppressLint("InlinedApi")
	@Override
	public boolean onKeyDown (int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (layout_select.getVisibility() == View.VISIBLE) {
				layout_select.setVisibility(View.GONE);
				return true;
			} else if (mDrawerLayout.isDrawerOpen(layout_left)) {
				mDrawerLayout.closeDrawer(Gravity.START);
			} else if (mDrawerLayout.isDrawerOpen(layout_right)) {
				mDrawerLayout.closeDrawer(Gravity.END);
			} else if ((System.currentTimeMillis() - mkeyTime) > 2000) {
				mkeyTime = System.currentTimeMillis();
				MsgToastUtil.MsgBox(this, "再按一次退出程序");
			} else {
				finish();
			}
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
