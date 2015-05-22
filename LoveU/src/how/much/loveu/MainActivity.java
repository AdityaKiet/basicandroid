package how.much.loveu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Button mButton;
	EditText mEdit1, mEdit2, mEdit3;
	String name = "", key = "";
	Integer result;
	Intent silly;
	MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButton = (Button) findViewById(R.id.button1);
		mEdit1 = (EditText) findViewById(R.id.editText1);
		mEdit2 = (EditText) findViewById(R.id.editText2);
		mEdit3 = (EditText) findViewById(R.id.editText3);

		silly = new Intent();

		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				name = mEdit1.getText().toString()
						+ mEdit2.getText().toString();
				key = mEdit3.getText().toString();

				result = loco(name, key);

				AlertDialog builder = new AlertDialog.Builder(MainActivity.this)
						.create();
				builder.setTitle("Results! Page Loading...\nPress Back Button to remove this box");
				builder.setMessage(result.toString() + "%");
				builder.show();

				// //System.out.println("--------------------------"+result+"---------------------");

				immabe();
			}

		});

	}

	private void immabe() {
		if (result < 50) {
			silly.setClass(this, Result.class);
		} else {
			silly.setClass(this, Result2.class);
		}

		startActivity(silly);
	}

	@Override
	protected void onResume() {
		player = MediaPlayer.create(this, R.raw.songbird);
		player.start();
		player.setLooping(true);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		player.stop();
		super.onPause();
		player.release();
	}

	public int loco(String name, String key) {
		int l = key.length();
		int power = 0;
		int l2 = name.length();
		int res = 0, count = 0;
		for (int h = l - 1; h >= 0; h--) {
			for (int j = 0; j < l2; j++) {
				if (name.charAt(j) == key.charAt(h))
					count++;
			}

			res += (int) Math.pow(10, power) * count;
			count = 0;
			power++;

		}

		while (res > 100) {
			int pw = 0;
			int n = res;
			res = 0;
			while (n > 10) {
				int rem1 = n % 10;
				n /= 10;
				int rem2 = n % 10;
				res += (int) Math.pow(10, pw) * (rem1 + rem2);
				pw++;
			}

		}
		return res;
	}

}
