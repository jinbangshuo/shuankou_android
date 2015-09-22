package com.maitianer.shuankou.util;

import me.dawson.kisstools.KissTools;
import me.dawson.kisstools.utils.FileUtil;
import android.app.Application;
import android.os.Environment;

import com.umeng.update.UmengUpdateAgent;

public class MyApplication extends Application
{
	public final static String SHARED_MAIN = "USER_SET";
	public final static String KEY_PLAYERID1 = "playerId1";
	public final static String KEY_PLAYERNAME1 = "playerName1";
	public final static String KEY_PLAYERID2 = "playerId2";
	public final static String KEY_PLAYERNAME2 = "playerName2";
	public final static String KEY_PLAYERID3 = "playerId3";
	public final static String KEY_PLAYERNAME3 = "playerName3";
	public final static String KEY_PLAYERID4 = "playerId4";
	public final static String KEY_PLAYERNAME4 = "playerName4";

	private static MyApplication instance;

	private String dbPath;
	private boolean existsSDCard;

	public static MyApplication getInstance () {
		return instance;
	}

	public String getDbPath () {
		return dbPath;
	}

	public boolean isExistsSDCard () {
		return existsSDCard;
	}

	@Override
	public void onCreate () {
		super.onCreate();
		instance = this;
		KissTools.setContext(this);
		// 自动更新
		UmengUpdateAgent.update(this);

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			dbPath = Environment.getExternalStorageDirectory() + "/shuankou/data/";
			FileUtil.mkdirs(dbPath);
			existsSDCard = true;
		} else {
			dbPath = "";
			existsSDCard = false;
		}
	}

	// 去小数点后的0
	public static String FormatNumber (float num) {
		int iNum = (int) num;
		if (iNum == num) {
			return iNum + "";
		} else {
			return num + "";
		}
	}
}