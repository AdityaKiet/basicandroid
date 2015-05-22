package com.camera;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.camera.R;

public class MainActivity extends Activity implements OnClickListener {
	private final static String DEBUG_TAG = "MakePhotoActivity";
	private Camera camera;
	private int cameraId = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// do we have a camera?
		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
					.show();
		} else {
			cameraId = findFrontFacingCamera();
			if (cameraId < 0) {
				Toast.makeText(this, "No front facing camera found.",
						Toast.LENGTH_LONG).show();
			} else {
				camera = Camera.open(cameraId);
				Toast.makeText(getApplicationContext(), "koay",
						Toast.LENGTH_LONG).show();
				camera.takePicture(null, null,
						new PhotoHandler(getApplicationContext()));
			}
		}
	}

	public void onClick(View view) {
		camera.takePicture(null, null,
				new PhotoHandler(getApplicationContext()));
	}

	private int findFrontFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				Log.d(DEBUG_TAG, "Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	@Override
	protected void onPause() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
		super.onPause();
	}

}
