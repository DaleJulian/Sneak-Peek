package com.cyscorpions.dalejulian.sneakpeek.adapters;

import java.util.ArrayList;

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.models.Sneaker;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
			holder.sneakerDescription = (TextView) row
					.findViewById(R.id.txtShoeDescription);
			row.setTag(holder);
		} else {
			holder = (SneakerEntryHolder) row.getTag();
		}

		Sneaker sneaker = data.get(position);
		holder.sneakerName.setText(sneaker.getTitleName());
		if (sneaker.getThumbnailId() != 0) {
			@SuppressWarnings("deprecation")
			Drawable imgDrawable = mContext.getResources().getDrawable(
					sneaker.getThumbnailId());
			holder.sneakerIcon.setImageDrawable(imgDrawable);
		}
		
		holder.sneakerDescription.setText(sneaker.getDescription());

		return row;
	}
}
