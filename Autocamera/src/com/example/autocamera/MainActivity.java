package com.example.autocamera;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	ImageView iv_image;
	SurfaceView sv;
	Camera mCamera;
	Bitmap bmp;
	Parameters parameters;
	SurfaceHolder sHolder;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_main);

		int index = getFrontCameraId();
		if (index == -1) {
			Toast.makeText(getApplicationContext(), "No front camera",
					Toast.LENGTH_LONG).show();
		} else {
			iv_image = (ImageView) findViewById(R.id.imageView);

			sv = (SurfaceView) findViewById(R.id.surfaceView);
			sHolder = sv.getHolder();
			sHolder.addCallback(null);
			sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		}

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		parameters = mCamera.getParameters();
		mCamera.setParameters(parameters);
		mCamera.startPreview();

		Camera.PictureCallback mCall = new Camera.PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				Uri uriTarget = getContentResolver().insert// (Media.EXTERNAL_CONTENT_URI,
															// image);
						(Media.EXTERNAL_CONTENT_URI, new ContentValues());

				OutputStream imageFileOS;
				try {
					imageFileOS = getContentResolver().openOutputStream(
							uriTarget);
					imageFileOS.write(data);
					imageFileOS.flush();
					imageFileOS.close();

					Toast.makeText(MainActivity.this,
							"Image saved: " + uriTarget.toString(),
							Toast.LENGTH_LONG).show();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// mCamera.startPreview();

				bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
				iv_image.setImageBitmap(bmp);
			}
		};

		mCamera.takePicture(null, null, mCall);
	}

	int getFrontCameraId() {
		CameraInfo ci = new CameraInfo();
		for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
			Camera.getCameraInfo(i, ci);
			if (ci.facing == CameraInfo.CAMERA_FACING_FRONT)
				return i;
		}
		return -1; // No front-facing camera found
	}

	public void surfaceCreated(SurfaceHolder holder) {
		int index = getFrontCameraId();
		if (index == -1) {
			Toast.makeText(getApplicationContext(), "No front camera",
					Toast.LENGTH_LONG).show();
		} else {
			mCamera = Camera.open(index);
			Toast.makeText(getApplicationContext(), "With front camera",
					Toast.LENGTH_LONG).show();
		}
		mCamera = Camera.open(index);
		try {
			mCamera.setPreviewDisplay(holder);

		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
		}

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
