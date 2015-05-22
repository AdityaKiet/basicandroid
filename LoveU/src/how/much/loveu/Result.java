package how.much.loveu;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class Result extends Activity {

	WebView myview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		myview=(WebView)findViewById(R.id.webView1);
		myview.getSettings().setBuiltInZoomControls(true);
		myview.loadUrl("file:///android_asset/tryagain.html");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}
