package ainaa.acup.database;

import java.util.ArrayList;
import java.util.List;

import ainaa.acup.dto.PCDTO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBActivity {
	public static final String KEY_NAME = "pc_name";
	public static final String KEY_IP = "pc_ip";
	public static final String KEY_ID = "pc_id";
	public static final String KEY_LAST_CONNECTION_TIME = "pc_last_connection_time";
	public static final String KEY_PIN = "pin";
	private static final String DATABASE_NAME = "pc_connection";
	private static final String DATABASE_TABLE = "pc_information";
	private static final int DATABASE_VERSION = 1;

	private DBHelper myHelper;
	private final Context myContext;
	private SQLiteDatabase myDataBase;

	public class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT NOT NULL, " + KEY_IP + " VARCHAR(20), "
					+ KEY_LAST_CONNECTION_TIME + " TIMESTAMP, " + KEY_PIN
					+ " VARCHAR(30));");

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

	public boolean createEntry(PCDTO pcdto) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, pcdto.getPc_name());
		cv.put(KEY_LAST_CONNECTION_TIME, pcdto.getPc_last_connected_time());
		cv.put(KEY_IP, pcdto.getPc_ip());
		cv.put(KEY_PIN, pcdto.getPin());
		long rows = myDataBase.insert(DATABASE_TABLE, null, cv);
		Log.d("db", "" + rows);
		if (rows != -1) {
			return true;
		} else {
			return false;
		}
	}

	public List<PCDTO> allPreviousPCs() {
		List<PCDTO> list = new ArrayList<PCDTO>();
		String[] columns = { KEY_ID, KEY_NAME, KEY_IP, KEY_PIN,
				KEY_LAST_CONNECTION_TIME };
		Cursor c = myDataBase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		int iRow = c.getColumnIndex(KEY_ID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iIp = c.getColumnIndex(KEY_IP);
		int iPin = c.getColumnIndex(KEY_PIN);
		int iLast = c.getColumnIndex(KEY_LAST_CONNECTION_TIME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			PCDTO pcdto = new PCDTO();
			pcdto.setPc_id(c.getString(iRow));
			pcdto.setPc_name(c.getString(iName));
			pcdto.setPc_ip(c.getString(iIp));
			pcdto.setPc_last_connected_time(c.getString(iLast));
			pcdto.setPin(c.getString(iPin));
			list.add(pcdto);
		}
		return list;
	}

	public boolean deletePreviousPCs() {
		myDataBase.execSQL("delete from " + DATABASE_TABLE);
		return true;
	}
}
