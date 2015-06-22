package com.cyscorpions.dalejulian.sneakpeek;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SneakerAdapter extends ArrayAdapter<Sneaker> {
	private Context mContext;
	private int layoutResourceId;
	private ArrayList<Sneaker> data;

	// view holder
	static class SneakerEntryHolder {
		ImageView sneakerIcon;
		TextView sneakerName;
		TextView sneakerDescription;
	}

	public SneakerAdapter(Context appContext, int layoutResourceId,
			ArrayList<Sneaker> data) {
		super(appContext, layoutResourceId, data);
		this.mContext = appContext;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		SneakerEntryHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new SneakerEntryHolder();
			holder.sneakerIcon = (ImageView) row
					.findViewById(R.id.imgSneakerThumbnail);
			holder.sneakerName = (TextView) row.findViewById(R.id.txtShoeName);
			holder.sneakerDescription = (TextView) row.findViewById(R.id.txtShoeDescription);
			row.setTag(holder);
		} else {
			holder = (SneakerEntryHolder) row.getTag();
		}

		Sneaker sneaker = data.get(position);
		String formattedName = sneaker.getBrand() + " " + sneaker.getName();
		holder.sneakerName.setText(formattedName);
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), sneaker.getThumbnailId());
		holder.sneakerIcon.setImageBitmap(bitmap);
		holder.sneakerDescription.setText(sneaker.getDescription());

		return row;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// raw height and width of the image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// calculate the largest inSampleSize value that is power of 2 and
			// keeps both
			// height and width larger than the requested height and width
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {
		// decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);

	}
}
