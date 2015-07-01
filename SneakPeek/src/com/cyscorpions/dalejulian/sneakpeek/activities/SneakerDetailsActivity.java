package com.cyscorpions.dalejulian.sneakpeek.activities;

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.models.Sneaker;
import com.cyscorpions.dalejulian.sneakpeek.models.SneakerDirectory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class SneakerDetailsActivity extends Activity {
	public static final int RECEIVE_EDITED_DETAILS_REQUEST = 5;

	public static final String DETAILS_NAME_EXTRA = "name";
	public static final String DETAILS_BRAND_EXTRA = "brand";
	public static final String DETAILS_SELLING_EXTRA = "selling";
	public static final String DETAILS_RARITY_EXTRA = "rarity";
	public static final String DETAILS_CATEGORY_EXTRA = "extra";
	public static final String DETAILS_DESC_EXTRA = "desc";
	public static final String DETAILS_IMGID_EXTRA = "img";
	public static final String DETAILS_TITLE_EXTRA = "title";
	public static final String DETAILS_UUID_EXTRA = "id";
	public static final String DETAILS_FROMDETAILS_EXTRA = "fromDetails";

	private TextView mName, mBrand, mSellValue, mRarity, mCategory, mDesc;
	private ImageView mThumbnail;

	private Sneaker sneaker = new Sneaker();
	private String sneakerId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sneaker_entry_activity);
		sneakerId = getIntent().getStringExtra(
				SneakerEntryListActivity.LIST_ID_EXTRA);
		findResources();
		setupContentByExtras();
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void findResources() {
		mName = (TextView) findViewById(R.id.txtSneakerName);
		mBrand = (TextView) findViewById(R.id.txtSneakerBrand);
		mSellValue = (TextView) findViewById(R.id.txtSellingValue);
		mRarity = (TextView) findViewById(R.id.txtSneakerRarity);
		mCategory = (TextView) findViewById(R.id.txtSneakerCategory);
		mDesc = (TextView) findViewById(R.id.txtSneakerDescription);
		mThumbnail = (ImageView) findViewById(R.id.imgSneakerEntry);
	}

	private void setupContentByExtras() {

		mName.setText(getIntent().getStringExtra(
				SneakerEntryListActivity.LIST_TITLE_EXTRA));
		mBrand.setText(getIntent().getStringExtra(
				SneakerEntryListActivity.LIST_BRAND_EXTRA));
		mSellValue.setText(getIntent().getStringExtra(
				SneakerEntryListActivity.LIST_SELLING_EXTRA));
		mRarity.setText(getIntent().getStringExtra(
				SneakerEntryListActivity.LIST_RARITY_EXTRA));
		mCategory.setText(getIntent().getStringExtra(
				SneakerEntryListActivity.LIST_CATEGORY_EXTRA));
		mDesc.setText(getIntent().getStringExtra(
				SneakerEntryListActivity.LIST_DESC_EXTRA));

		int imgResourceId = getIntent().getIntExtra(
				SneakerEntryListActivity.LIST_IMGID_EXTRA, 0);
		if (imgResourceId != 0) {
			@SuppressWarnings("deprecation")
			Drawable imageDrawable = getResources().getDrawable(imgResourceId);
			mThumbnail.setImageDrawable(imageDrawable);
		}

		sneaker.setName(mName.getText().toString());
		sneaker.setBrand(mBrand.getText().toString());
		sneaker.setSellingValue(mSellValue.getText().toString());
		sneaker.setDescription(mDesc.getText().toString());
		sneaker.setThumbnailId(getIntent().getIntExtra(
				SneakerEntryListActivity.LIST_IMGID_EXTRA, 0));
		sneaker.setRarity(mRarity.getText().toString());
		sneaker.setCategory(mCategory.getText().toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add("Edit details");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getTitle() == "Edit details") {
			Intent i = new Intent(SneakerDetailsActivity.this,
					EditSneakerEntryActivity.class);
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_NAME,
					sneaker.getName());
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_BRAND,
					sneaker.getBrand());
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_SELLVAL,
					sneaker.getSellingValue());
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_RARITY,
					sneaker.getRarity());
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_CATEGORY, sneaker
					.getCategory().getName().toString());
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_DESCRIPTION,
					sneaker.getDescription());
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_THUMBNAILID,
					sneaker.getThumbnailId());
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_ID, sneakerId);
			i.putExtra(DETAILS_FROMDETAILS_EXTRA, true);
			startActivityForResult(i, RECEIVE_EDITED_DETAILS_REQUEST);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int resultCode, int requestCode, Intent data) {
		Log.i("SneakerDetails", "HOHO" + String.valueOf(requestCode));

		if (data == null)
			Log.i("null data", "null data");
		if (requestCode == RECEIVE_EDITED_DETAILS_REQUEST) {
			mName.setText(data
					.getStringExtra(EditSneakerEntryActivity.EDIT_NAME_EXTRA));
			mBrand.setText(data
					.getStringExtra(EditSneakerEntryActivity.EDIT_BRAND_EXTRA));
			mSellValue
					.setText(data
							.getStringExtra(EditSneakerEntryActivity.EDIT_SELLVAL_EXTRA));
			mRarity.setText(data
					.getStringExtra(EditSneakerEntryActivity.EDIT_RARITY_EXTRA));
			mCategory
					.setText(data
							.getStringExtra(EditSneakerEntryActivity.EDIT_CATEGORY_EXTRA));
			mDesc.setText(data
					.getStringExtra(EditSneakerEntryActivity.EDIT_DESC_EXTRA));
		}

	}
}
