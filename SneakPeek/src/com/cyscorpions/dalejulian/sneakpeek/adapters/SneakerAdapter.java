package com.cyscorpions.dalejulian.sneakpeek.adapters;

import java.util.ArrayList;

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.models.Sneaker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SneakerAdapter extends ArrayAdapter<Sneaker> {
	private Context mContext;
	private ArrayList<Sneaker> data;
	private static int layoutResourceId = R.layout.sneaker_entry_listitem;
	
	// view holder
	static class SneakerEntryHolder {
		ImageView sneakerIcon;
		TextView sneakerName;
		TextView sneakerDescription;
	}

	public SneakerAdapter(Context appContext, ArrayList<Sneaker> data) {
		super(appContext, layoutResourceId, data);
		this.mContext = appContext;
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
			row.setTag(R.id.TAG_SNEAKER, holder);
		} else {
			holder = (SneakerEntryHolder) row.getTag(R.id.TAG_SNEAKER);
		}

		Sneaker sneaker = data.get(position);
		holder.sneakerName.setText(sneaker.getTitleName());
		if (sneaker.getThumbnailId() != 0) {
			Drawable imgDrawable = ContextCompat.getDrawable(mContext,
					sneaker.getThumbnailId());
			holder.sneakerIcon.setImageDrawable(imgDrawable);
		}

		holder.sneakerDescription.setText(sneaker.getDescription());
		row.setTag(R.id.TAG_SNEAKER_OBJECT, sneaker);
		return row;
	}
}
