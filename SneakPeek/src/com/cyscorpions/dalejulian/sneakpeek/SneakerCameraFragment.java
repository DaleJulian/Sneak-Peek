package com.cyscorpions.dalejulian.sneakpeek;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SneakerCameraFragment extends Fragment {

	public static final String PHOTO_FILENAME_EXTRA = "SneakerCameraFragment.filename";

	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private View mProgressContainer;

	private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		public void onShutter() {
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	};

	private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// create filename
			String filename = UUID.randomUUID().toString() + ".jpg";

			FileOutputStream os = null;
			boolean success = true;
			try {
				os = getActivity().openFileOutput(filename,
						Context.MODE_PRIVATE);
				os.write(data);
			} catch (Exception e) {
				Log.e("SneakerCameraFragment", "Error writing to file: "
						+ filename, e);
				success = false;
			} finally {
				try {
					if (os != null) {
						os.close();
					}
				} catch (Exception e) {
					Log.e("SneakerCameraFragment", "Error closing file: "
							+ filename, e);
					success = false;
				}
			}

			if (success) {
				// set the photo filename on the result intent
				if (success) {
					Intent i = new Intent();
					i.putExtra(PHOTO_FILENAME_EXTRA, filename);
					getActivity().setResult(Activity.RESULT_OK, i);
				} else {
					getActivity().setResult(Activity.RESULT_CANCELED);
				}
				getActivity().finish();
			}

		}

	};

	@Override
	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.sneaker_camera_fragment, parent,
				false);

		mProgressContainer = v.findViewById(R.id.snkrCamProgressContainer);
		mProgressContainer.setVisibility(View.VISIBLE);

		Button takePictureBtn = (Button) v.findViewById(R.id.btnTakePicture);
		takePictureBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mCamera != null) {
					mCamera.takePicture(mShutterCallback, null, mJpegCallback);
				}

			}
		});

		mSurfaceView = (SurfaceView) v
				.findViewById(R.id.sneaker_camera_surfaceView);
		SurfaceHolder holder = mSurfaceView.getHolder();
		// deprecated but required for pre-3.0 devices
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if (mCamera != null) {
					mCamera.stopPreview();
				}

			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				mCamera.setDisplayOrientation(90);
				try {
					if (mCamera != null) {
						mCamera.setPreviewDisplay(holder);
					}
				} catch (IOException ioe) {
					Log.e("SneakerCameraFragment",
							"Error setting up preview display", ioe);
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				if (mCamera == null) {
					return;
				}

				Camera.Parameters parameters = mCamera.getParameters();
				Size s = getBestSupportedSize(
						parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(s.width, s.height);
				s = getBestSupportedSize(parameters.getSupportedPictureSizes(),
						width, height);
				parameters.setPictureSize(s.width, s.height);
				mCamera.setParameters(parameters);
				mCamera.startPreview();
				try {
					mCamera.startPreview();
				} catch (Exception e) {
					Log.e("SneakerCameraFragment", "Could not start preview", e);
					mCamera.release();
					mCamera = null;
				}
			}
		});

		// setCameraDisplayOrientation(getActivity(), 0, mCamera);
		return v;

	}

	@TargetApi(9)
	@Override
	public void onResume() {
		super.onResume();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			mCamera = Camera.open(0);
		} else {
			mCamera = Camera.open();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	private Size getBestSupportedSize(List<Size> sizes, int width, int height) {
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		for (Size s : sizes) {
			int area = s.width * s.height;
			if (area > largestArea) {
				bestSize = s;
				largestArea = area;
			}
		}
		return bestSize;
	}
}
