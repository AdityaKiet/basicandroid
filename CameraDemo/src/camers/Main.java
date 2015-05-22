package camers;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.camerademo.R;

public class Main extends Activity {
	public static final int DONE = 1;
	public static final int NEXT = 2;
	public static final int PERIOD = 1;
	private Camera camera;
	private int cameraId = 0;
	private ImageView display;
	private Timer timer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		display = (ImageView) findViewById(R.id.imageView1);
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
				safeCameraOpen(cameraId);
			}
		}
		// THIS IS JUST A FAKE SURFACE TO TRICK THE CAMERA PREVIEW
		// http://stackoverflow.com/questions/17859777/how-to-take-pictures-in-android-
		// application-without-the-user-interface
		SurfaceView view = new SurfaceView(this);
		try {
			camera.setPreviewDisplay(view.getHolder());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		camera.startPreview();
		Camera.Parameters params = camera.getParameters();
		params.setJpegQuality(100);
		camera.setParameters(params);
		// We need something to trigger periodically the capture of a
		// picture to be processed
		timer = new Timer(getApplicationContext(), threadHandler);
		timer.execute();
	}

	// //////////////////////////////////thread
	// Handler///////////////////////////////////////
	private Handler threadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DONE:
				// Trigger camera callback to take pic
				camera.takePicture(null, null, mCall);
				break;
			case NEXT:
				timer = new Timer(getApplicationContext(), threadHandler);
				timer.execute();
				break;
			}
		}
	};
	Camera.PictureCallback mCall = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			// decode the data obtained by the camera into a Bitmap
			// display.setImageBitmap(photo);
			Bitmap bitmapPicture = BitmapFactory.decodeByteArray(data, 0,
					data.length);
			display.setImageBitmap(bitmapPicture);
			Message.obtain(threadHandler, Main.NEXT, "").sendToTarget();
			// Log.v("MyActivity","Length: "+data.length);
		}
	};

	private int findFrontFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				Log.v("MyActivity", "Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	@Override
	protected void onPause() {
		if (timer != null) {
			timer.cancel(true);
		}
		releaseCamera();
		super.onPause();
	}

	// I think Android Documentation recommends doing this in a separate
	// task to avoid blocking main UI
	private boolean safeCameraOpen(int id) {
		boolean qOpened = false;
		try {
			releaseCamera();
			camera = Camera.open(id);
			qOpened = (camera != null);
		} catch (Exception e) {
			Log.e(getString(R.string.app_name), "failed to open Camera");
			e.printStackTrace();
		}
		return qOpened;
	}

	private void releaseCamera() {
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}

}