package com.maitianer.shuankou.util;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
	private static final int DB_VERSION = 1;
	private static String DB_NAME = "data.sqlite";
	private String DB_PATH;

	private DBHelper (Context context, String name, int version) {
		super(context, name, null, version);
	}

	public DBHelper (Context context, String db_path) {
		this(context, db_path + DB_NAME, DB_VERSION);
		DB_PATH = db_path;
	}

	@Override
	public synchronized void close () {
		super.close();
	}

	public void createDataBase () {
		boolean dbExist = checkDataBase();
		if (!dbExist) {
			// 创建数据库
			File dbf = new File(DB_PATH + DB_NAME);
			SQLiteDatabase.openOrCreateDatabase(dbf, null);
		}
	}

	// 检查数据库是否有效
	private boolean checkDataBase () {
		SQLiteDatabase checkDB = null;
		String myPath = DB_PATH + DB_NAME;
		try {
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {

		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	@Override
	public void onCreate (SQLiteDatabase db) {
		ArrayList<String> pSqlStrings = new ArrayList<String>();

		pSqlStrings.add("DROP TABLE IF EXISTS recordList;");
		pSqlStrings.add("CREATE TABLE recordList (panId text(50,0),staffId integer DEFAULT 0,fdate datetime,findex integer,fcount numeric(18,2),remark text(50,0),UNIQUE (panId COLLATE RTRIM ASC, staffId COLLATE RTRIM ASC));");
		pSqlStrings.add("DROP TABLE IF EXISTS playerList;");
		pSqlStrings.add("CREATE TABLE playerList (id integer NOT NULL,name text,closed smallint,PRIMARY KEY(id),UNIQUE (id COLLATE RTRIM ASC));");
		pSqlStrings.add("DROP TABLE IF EXISTS systemSet;");
		pSqlStrings.add("CREATE TABLE systemSet (set_p numeric(18,2),set_d numeric(18,2),set_s numeric(18,2),set_max integer,set_6 numeric(18,2),set_7 numeric(18,2),set_8 numeric(18,2),set_9 numeric(18,2),set_10 numeric(18,2),set_11 numeric(18,2),set_12 numeric(18,2));");
		pSqlStrings.add("CREATE INDEX R1 ON recordList (staffId ASC, fdate ASC, panId ASC);");

		pSqlStrings.add("INSERT INTO playerList(id,name,closed) VALUES (1, '玩家(1)', 0);");
		pSqlStrings.add("INSERT INTO playerList(id,name,closed) VALUES (2, '玩家(2)', 0);");
		pSqlStrings.add("INSERT INTO playerList(id,name,closed) VALUES (3, '玩家(3)', 0);");
		pSqlStrings.add("INSERT INTO playerList(id,name,closed) VALUES (4, '玩家(4)', 0);");
		pSqlStrings.add("INSERT INTO systemSet(set_p,set_d,set_s,set_max,set_6,set_7,set_8,set_9,set_10,set_11,set_12) VALUES (1, 2, 3, 0, 1, 2, 4, 8, 16, 32, 64);");

		try {
			db.beginTransaction();
			for (int i = 0; i < pSqlStrings.size(); i++) {
				db.execSQL(pSqlStrings.get(i));
			}
			db.setTransactionSuccessful();
		}
		finally {
			db.endTransaction();
		}
		System.out.println("新建数据库");
	}

	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}