package com.cyscorpions.dalejulian.sneakpeek.adapters;

import java.util.ArrayList;

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.models.SneakerCategory;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SneakerCategoryAdapter extends ArrayAdapter<SneakerCategory> {
	private Context mContext;
	private int layoutResourceId;
	private ArrayList<SneakerCategory> data;

	// view holder
	static class CategoryHolder {
		TextView categoryName, categoryDesc;
	}

	public SneakerCategoryAdapter(Context appContext, int layoutResourceId,
			ArrayList<SneakerCategory> data) {
		super(appContext, layoutResourceId, data);
		this.mContext = appContext;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CategoryHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new CategoryHolder();
			holder.categoryName = (TextView) row
					.findViewById(R.id.txtCategoryName);
			holder.categoryDesc = (TextView) row
					.findViewById(R.id.txtCategoryDesc);

			//row.setTag(holder);
			row.setTag(R.id.TAG_CATEGORY, holder);
		} else {
			holder = (CategoryHolder) row.getTag(R.id.TAG_CATEGORY);
		}

		SneakerCategory category = data.get(position);
		holder.categoryName.setText(category.getName());
		holder.categoryDesc.setText(category.getDescription());

		return row;
	}
}
