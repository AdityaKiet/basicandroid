package sharma;

import com.example.image.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class Main extends Activity {
	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		image = (ImageView) findViewById(R.id.imageView1);
	}

}
