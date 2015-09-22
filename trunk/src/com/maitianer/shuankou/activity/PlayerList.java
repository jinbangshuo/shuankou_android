package com.maitianer.shuankou.activity;

import java.util.ArrayList;

import me.dawson.kisstools.utils.DeviceUtil;
import me.dawson.kisstools.utils.MsgToastUtil;
import me.dawson.kisstools.utils.MsgToastUtil.MsgDelogCallback;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.maitianer.shuankou.R;
import com.maitianer.shuankou.adapter.PlayerAdapter;
import com.maitianer.shuankou.model.PlayerModel;
import com.maitianer.shuankou.util.DBManager;
import com.maitianer.swipemenulistview.SwipeMenu;
import com.maitianer.swipemenulistview.SwipeMenuCreator;
import com.maitianer.swipemenulistview.SwipeMenuItem;
import com.maitianer.swipemenulistview.SwipeMenuListView;
import com.maitianer.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.umeng.analytics.MobclickAgent;

public class PlayerList extends Activity implements OnClickListener, OnItemClickListener
{
	private TextView title;
	private Button btn_bar_left, btn_bar_right;
	private SwipeMenuListView list;

	private ArrayList<PlayerModel> listModels;
	private PlayerAdapter playerAdapter;
	private int positionIndex;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_playerlist);
		initView();
		loadData();
	}

	// 控件初始化
	private void initView () {
		title = (TextView) findViewById(R.id.title);
		btn_bar_left = (Button) findViewById(R.id.btn_bar_left);
		btn_bar_right = (Button) findViewById(R.id.btn_bar_right);
		list = (SwipeMenuListView) findViewById(R.id.list);
		listModels = new ArrayList<PlayerModel>();
		playerAdapter = new PlayerAdapter(this, listModels, 3);
		list.setAdapter(playerAdapter);

		list.setOnItemClickListener(this);

		btn_bar_left.setOnClickListener(this);
		btn_bar_right.setOnClickListener(this);

		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create (SwipeMenu menu) {
				SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
				deleteItem.setWidth(DeviceUtil.dp2px(90));
				deleteItem.setTitle("删除");
				deleteItem.setTitleSize(16);
				deleteItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		list.setMenuCreator(creator);

		// step 2. listener item click event
		list.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick (int position, SwipeMenu menu, int index) {
				switch (index) {
					case 0:// 删除
						positionIndex = position;
						MsgToastUtil.MsgBox(PlayerList.this, "确定要删除该玩家吗？", MsgToastUtil.Msg_2Button, doDelete);
						break;
				}
			}
		});
	}

	// 加载数据
	private void loadData () {
		title.setText("玩家管理");
		btn_bar_left.setText("返回");
		btn_bar_right.setText("新增");

		getData();
	}

	private void getData () {
		DBManager TheAccess = new DBManager(this);
		String SqlStr = "Select id,name,closed from playerList order by id";
		Cursor rst = TheAccess.sqlQuery(SqlStr);
		listModels.clear();
		while (rst.moveToNext()) {
			PlayerModel model = new PlayerModel(rst.getInt(rst.getColumnIndex("id")), rst.getString(rst.getColumnIndex("name")), rst.getInt(rst.getColumnIndex("closed")));
			listModels.add(model);
		}
		rst.close();
		TheAccess.close();
		playerAdapter.notifyDataSetChanged();
	}

	MsgDelogCallback doDelete = new MsgDelogCallback() {
		public void DoSomething_ChickOK () {
			PlayerModel model = listModels.get(positionIndex);
			DBManager TheAccess = new DBManager(PlayerList.this);
			String SqlStr = "delete from playerList where id=" + model.getId();
			TheAccess.execSQL(SqlStr);
			TheAccess.close();
			listModels.remove(positionIndex);
			playerAdapter.notifyDataSetChanged();
			MsgToastUtil.MsgBox(PlayerList.this, "删除成功！");
		}
	};

	public void onItemClick (AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		PlayerModel model = listModels.get(arg2);
		Bundle bundle = new Bundle();
		bundle.putInt("id", model.getId());
		bundle.putString("name", model.getName());
		bundle.putInt("close", model.getClosed());
		Intent intent = new Intent(this, PlayerEdit.class);
		intent.putExtras(bundle);
		startActivityForResult(intent, 1);
	}

	public void onClick (View arg0) {
		Intent intent;
		switch (arg0.getId()) {
			case R.id.btn_bar_left:
				finish();
				break;
			case R.id.btn_bar_right:
				intent = new Intent(this, PlayerEdit.class);
				startActivityForResult(intent, 1);
				break;

			default:
				break;
		}
	}

	/**
	 * 弹窗返回结果
	 */
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case 1:
					getData();
					break;
				default:
					break;
			}
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