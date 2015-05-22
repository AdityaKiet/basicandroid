package com.kiet.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBActivity {

	private static final String DATABASE_NAME = "KIET";
	private static final String DATABASE_TABLE = "kiet";
	private static final int DATABASE_VERSION = 1;
	private static final String KEY_ROW = "name";
	private DBHelper dbHelper;
	private final Context myContext;
	private SQLiteDatabase myDataBase;

	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ROW
					+ " VARCHAR(100));");
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public DBActivity(Context c) {
		myContext = c;
	}

	public void close() {
		dbHelper.close();
	}

	public DBActivity open() {
		dbHelper = new DBHelper(myContext);
		myDataBase = dbHelper.getWritableDatabase();
		return this;
	}

	public void insertDB(String name) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_ROW, name);
		myDataBase.insert(DATABASE_TABLE, null, cv);
	}

	public ArrayList<String> getData() {
		ArrayList<String> names = new ArrayList<>();
		String query = "select * from " + DATABASE_TABLE;
		Cursor c = myDataBase.rawQuery(query, null);
		int iName = c.getColumnIndex(KEY_ROW);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			names.add(c.getString(iName));
		}
		return names;
	}

	public void deleteDB(String name) {
		myDataBase.delete(DATABASE_TABLE, KEY_ROW + " = '" + name + "'", null);
	}
}
