package com.maitianer.shuankou.util;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager
{
	private DBHelper mHelper;
	private SQLiteDatabase database;

	public DBManager (Context context) {
		mHelper = new DBHelper(context, MyApplication.getInstance().getDbPath());
		mHelper.createDataBase();
		database = mHelper.getWritableDatabase();
	}

	public Cursor sqlQuery (String SQLCommandText) {
		Cursor cursor = database.rawQuery(SQLCommandText, null);
		return cursor;
	}

	public void execSQL (String SQLCommandText) {
		database.execSQL(SQLCommandText);
	}

	public void execSQLByTran (List<String> SQLCommandList) {
		try {
			database.beginTransaction();
			for (int i = 0; i < SQLCommandList.size(); i++) {
				database.execSQL(SQLCommandList.get(i));
			}
			database.setTransactionSuccessful();
		}
		finally {
			database.endTransaction();
		}
	}

	public void close () {
		this.database.close();
	}
}
