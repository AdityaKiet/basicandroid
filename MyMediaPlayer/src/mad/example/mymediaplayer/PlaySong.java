package mad.example.mymediaplayer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlaySong extends Activity {
	public TextView songName, startTimeField, endTimeField;
	private MediaPlayer mediaPlayer;
	private double startTime = 0;
	private double finalTime = 0;
	private Handler myHandler = new Handler();;
	private int forwardTime = 5000;
	private int backwardTime = 5000;
	private SeekBar seekbar;
	private ImageButton playButton, pauseButton;
	public static int oneTimeOnly = 0;
	private String fileName;

	@Override
	public void onBackPressed() {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		super.onBackPressed();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle bundle = getIntent().getExtras();
		fileName = bundle.getString("key");
		Log.d("log", fileName);
		mediaPlayer = new MediaPlayer();
		songName = (TextView) findViewById(R.id.textView4);
		startTimeField = (TextView) findViewById(R.id.textView1);
		endTimeField = (TextView) findViewById(R.id.textView2);
		seekbar = (SeekBar) findViewById(R.id.seekBar1);
		playButton = (ImageButton) findViewById(R.id.imageButton1);
		pauseButton = (ImageButton) findViewById(R.id.imageButton2);
		songName.setText("song.mp3");
		seekbar.setClickable(false);
		pauseButton.setEnabled(false);
		try {
			mediaPlayer.setDataSource(fileName);
			mediaPlayer.prepare();
			mediaPlayer.start();
			finalTime = mediaPlayer.getDuration();
			startTime = mediaPlayer.getCurrentPosition();
			if (oneTimeOnly == 0) {
				seekbar.setMax((int) finalTime);
				oneTimeOnly = 1;
			}

			endTimeField.setText(String.format(
					"%d min, %d sec",
					TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
					TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes((long) finalTime))));
			startTimeField.setText(String.format(
					"%d min, %d sec",
					TimeUnit.MILLISECONDS.toMinutes((long) startTime),
					TimeUnit.MILLISECONDS.toSeconds((long) startTime)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes((long) startTime))));
			seekbar.setProgress((int) startTime);
			myHandler.postDelayed(UpdateSongTime, 100);
			pauseButton.setEnabled(true);
			playButton.setEnabled(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressLint("NewApi")
	public void play(View view) {
		Toast.makeText(getApplicationContext(), "Playing sound",
				Toast.LENGTH_SHORT).show();
		mediaPlayer.start();
		finalTime = mediaPlayer.getDuration();
		startTime = mediaPlayer.getCurrentPosition();
		if (oneTimeOnly == 0) {
			seekbar.setMax((int) finalTime);
			oneTimeOnly = 1;
		}

		endTimeField.setText(String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
				TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes((long) finalTime))));
		startTimeField.setText(String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes((long) startTime),
				TimeUnit.MILLISECONDS.toSeconds((long) startTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes((long) startTime))));
		seekbar.setProgress((int) startTime);
		myHandler.postDelayed(UpdateSongTime, 100);
		pauseButton.setEnabled(true);
		playButton.setEnabled(false);
	}

	private Runnable UpdateSongTime = new Runnable() {
		@SuppressLint("NewApi")
		public void run() {
			try {
				startTime = mediaPlayer.getCurrentPosition();
				startTimeField.setText(String.format(
						"%d min, %d sec",
						TimeUnit.MILLISECONDS.toMinutes((long) startTime),
						TimeUnit.MILLISECONDS.toSeconds((long) startTime)
								- TimeUnit.MINUTES
										.toSeconds(TimeUnit.MILLISECONDS
												.toMinutes((long) startTime))));
				seekbar.setProgress((int) startTime);
				myHandler.postDelayed(this, 100);
			} catch (NullPointerException e) {
				onBackPressed();
			}
		}
	};

	public void pause(View view) {
		Toast.makeText(getApplicationContext(), "Pausing sound",
				Toast.LENGTH_SHORT).show();

		mediaPlayer.pause();
		pauseButton.setEnabled(false);
		playButton.setEnabled(true);
	}

	public void forward(View view) {
		int temp = (int) startTime;
		if ((temp + forwardTime) <= finalTime) {
			startTime = startTime + forwardTime;
			mediaPlayer.seekTo((int) startTime);
		} else {
			Toast.makeText(getApplicationContext(),
					"Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
		}

	}

	public void rewind(View view) {
		int temp = (int) startTime;
		if ((temp - backwardTime) > 0) {
			startTime = startTime - backwardTime;
			mediaPlayer.seekTo((int) startTime);
		} else {
			Toast.makeText(getApplicationContext(),
					"Cannot jump backward 5 seconds", Toast.LENGTH_SHORT)
					.show();
		}

	}
}
