package com.kiet.tts;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.texttospeech.R;

public class MainActivity extends Activity implements OnClickListener {
	Button button;
	String[] texts = { "Aditya Agrawal", "Vertika" };
	TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		tts = new TextToSpeech(MainActivity.this,
				new TextToSpeech.OnInitListener() {

					public void onInit(int status) {
						if (status != TextToSpeech.ERROR) {
							tts.setLanguage(Locale.UK);
						}
					}
				});
	}

	@Override
	protected void onPause() {
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button1) {
			tts.speak(texts[0] + texts[1], TextToSpeech.QUEUE_FLUSH, null);
		}

	}

}
