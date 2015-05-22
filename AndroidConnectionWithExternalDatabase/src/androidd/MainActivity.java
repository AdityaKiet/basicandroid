package androidd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ResourceBundle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.androidconnectionwithexternaldatabase.R;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		testDB();
	}

	public void testDB() {
		TextView tv = (TextView) this.findViewById(R.id.textView1);
		String result = null;
		try {
			ResourceBundle rb = ResourceBundle.getBundle("db");
			String driverName = rb.getString("drivername");
			Class.forName(driverName);
			System.out.println("Driver Loaded...");
			String url = rb.getString("dburl");
			String userid = rb.getString("userid");
			String password = rb.getString("password");
			Connection con = DriverManager.getConnection(url, userid, password);
			if (con != null) {
				System.out.println("Connection Ready...");
			}
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from student_info");
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
				result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
				result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
			}
			tv.setText(result);
		} catch (Exception e) {
			e.printStackTrace();
			tv.setText(e.toString());
		}

	}
}