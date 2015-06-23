package com.cyscorpions.dalejulian.sneakpeek;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class SneakerDetailsActivity extends Activity {

	private TextView mName, mBrand, mSellValue, mRarity, mCategory, mDesc;
	private ImageView mThumbnail;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sneaker_entry_activity);

		mName = (TextView) findViewById(R.id.txtSneakerName);
		mBrand = (TextView) findViewById(R.id.txtSneakerBrand);
		mSellValue = (TextView) findViewById(R.id.txtSellingValue);
		mRarity = (TextView) findViewById(R.id.txtSneakerRarity);
		mCategory = (TextView) findViewById(R.id.txtSneakerCategory);
		mDesc = (TextView) findViewById(R.id.txtSneakerDescription);

		mThumbnail = (ImageView) findViewById(R.id.imgSneakerEntry);

		mName.setText(getIntent().getStringExtra(
				SneakerEntryList.LIST_NAME_EXTRA));
		mBrand.setText(getIntent().getStringExtra(
				SneakerEntryList.LIST_BRAND_EXTRA));
		mSellValue.setText(getIntent().getStringExtra(
				SneakerEntryList.LIST_SELLING_EXTRA));
		mRarity.setText(getIntent().getStringExtra(
				SneakerEntryList.LIST_RARITY_EXTRA));
		mCategory.setText(getIntent().getStringExtra(
				SneakerEntryList.LIST_CATEGORY_EXTRA));
		mDesc.setText(getIntent().getStringExtra(
				SneakerEntryList.LIST_DESC_EXTRA));
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				getIntent().getIntExtra(SneakerEntryList.LIST_IMGID_EXTRA, 0));
		if (bitmap != null) {
			mThumbnail.setImageBitmap(bitmap);
		}
	}
}
