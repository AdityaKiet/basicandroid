package com.kiet.lenden;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kiet.dto.PersonDTO;

public class DBActivity {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "person_name";
	public static final String KEY_TAKE = "take_amount";
	public static final String KEY_GIVE = "give_amount";

	private static final String DATABASE_NAME = "LenDen";
	private static final String DATABASE_TABLE = "LenDen";
	private static final int DATABASE_VERSION = 1;

	private DBHelper myHelper;
	private final Context myContext;
	private SQLiteDatabase myDataBase;

	public class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT UNIQUE NOT NULL, " + KEY_TAKE + " INTEGER, "
					+ KEY_GIVE + " INTEGER);");

		}

		public void onUpgrade(SQLiteDatabase db, int oldversion, int newvesrion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public DBActivity(Context c) {
		myContext = c;
	}

	public void close() {
		myHelper.close();
	}

	public DBActivity open() {
		myHelper = new DBHelper(myContext);
		myDataBase = myHelper.getWritableDatabase();
		return this;
	}

	public List<PersonDTO> getArrayList() {
		List<PersonDTO> list = new ArrayList<PersonDTO>();
		String[] columns = { KEY_ROWID, KEY_NAME, KEY_GIVE, KEY_TAKE };
		Cursor c = myDataBase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iGive = c.getColumnIndex(KEY_GIVE);
		int iTake = c.getColumnIndex(KEY_TAKE);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			PersonDTO personDTO = new PersonDTO();
			personDTO.setId(c.getInt(iRow));
			personDTO.setName(c.getString(iName));
			personDTO.setGiveAmount(c.getInt(iGive));
			personDTO.setTakeAmount(c.getInt(iTake));
			list.add(personDTO);
		}
		return list;
	}

	public void deleteEntry(String name) {
		myDataBase.delete(DATABASE_TABLE, KEY_NAME + "='" + name + "'", null);
	}

	public void updateData(String name, String giveamount, String takeamount) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_GIVE, giveamount);
		cv.put(KEY_TAKE, takeamount);
		myDataBase.update(DATABASE_TABLE, cv, KEY_NAME + "='" + name + "'",
				null);
	}

	public boolean createEntery(String name, int giveamount, int takeamount) {
		boolean isexists = checkData(name);
		if (isexists) {
			ContentValues cv = new ContentValues();
			cv.put(KEY_NAME, name);
			cv.put(KEY_GIVE, giveamount);
			cv.put(KEY_TAKE, takeamount);
			myDataBase.insert(DATABASE_TABLE, null, cv);
		}
		return !isexists;
	}

	public PersonDTO getPersonData(String name) {

		boolean isexists = checkData(name);

		String query = "select " + KEY_ROWID + "," + KEY_NAME + "," + KEY_GIVE
				+ "," + KEY_TAKE + " " + " from " + DATABASE_TABLE + " where "
				+ KEY_NAME + "= '" + name + "'";
		PersonDTO obj = new PersonDTO();
		if (!isexists) {
			Cursor c = myDataBase.rawQuery(query, null);
			int iRow = c.getColumnIndex(KEY_ROWID);
			int iName = c.getColumnIndex(KEY_NAME);
			int iGive = c.getColumnIndex(KEY_GIVE);
			int iTake = c.getColumnIndex(KEY_TAKE);
			if (c.moveToFirst()) {
				obj.setId(Integer.parseInt(c.getString(iRow)));
				obj.setName(c.getString(iName));
				obj.setGiveAmount(Integer.parseInt(c.getString(iGive)));
				obj.setTakeAmount(Integer.parseInt(c.getString(iTake)));
			} else {
				obj = null;
			}
			return obj;
		} else {
			return null;
		}
	}

	private boolean checkData(String name) {
		Cursor c = myDataBase.rawQuery("select * from " + DATABASE_TABLE
				+ " where " + KEY_NAME + "= '" + name + "'", null);
		if (c.getCount() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
