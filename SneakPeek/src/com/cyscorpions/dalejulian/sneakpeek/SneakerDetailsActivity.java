package com.cyscorpions.dalejulian.sneakpeek;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class SneakerDetailsActivity extends Activity {

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
		sneakerId = getIntent().getStringExtra(SneakerEntryList.LIST_ID_EXTRA);
		Log.i("Sneaker Entry", sneakerId);
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
				SneakerEntryList.LIST_TITLE_EXTRA));
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

		@SuppressWarnings("deprecation")
		Drawable imageDrawable = getResources().getDrawable(
				getIntent().getIntExtra(SneakerEntryList.LIST_IMGID_EXTRA, 0));
		mThumbnail.setImageDrawable(imageDrawable);
		sneaker.setName(mName.getText().toString());
		sneaker.setBrand(mBrand.getText().toString());
		sneaker.setSellingValue(mSellValue.getText().toString());
		sneaker.setDescription(mDesc.getText().toString());
		sneaker.setThumbnailId(getIntent().getIntExtra(
				SneakerEntryList.LIST_IMGID_EXTRA, 0));
		sneaker.setRarity(mRarity.getText().toString());
		sneaker.setCategory(mCategory.getText().toString());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {

			return true;
		}

		if (item.getTitle() == "Edit details") {
			Intent i = new Intent(SneakerDetailsActivity.this,
					EditSneakerEntryActivity.class);
			i.putExtra(DETAILS_NAME_EXTRA, sneaker.getName());
			i.putExtra(DETAILS_BRAND_EXTRA, sneaker.getBrand());
			i.putExtra(DETAILS_SELLING_EXTRA, sneaker.getSellingValue());
			i.putExtra(DETAILS_RARITY_EXTRA, sneaker.getRarity());
			i.putExtra(DETAILS_CATEGORY_EXTRA, sneaker.getCategory().getName()
					.toString());
			i.putExtra(DETAILS_DESC_EXTRA, sneaker.getDescription());
			i.putExtra(DETAILS_IMGID_EXTRA, sneaker.getThumbnailId());
			i.putExtra(DETAILS_TITLE_EXTRA, sneaker.getTitleName());
			i.putExtra(DETAILS_UUID_EXTRA, sneakerId);
			i.putExtra(DETAILS_FROMDETAILS_EXTRA, true);
			startActivityForResult(i, 1);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add("Edit details");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onActivityResult(int resultCode, int requestCode, Intent data) {
		mName.setText(data
				.getStringExtra(EditSneakerEntryActivity.EDIT_NAME_EXTRA));
		mBrand.setText(data
				.getStringExtra(EditSneakerEntryActivity.EDIT_BRAND_EXTRA));
		mSellValue.setText(data
				.getStringExtra(EditSneakerEntryActivity.EDIT_SELLVAL_EXTRA));
		mRarity.setText(data
				.getStringExtra(EditSneakerEntryActivity.EDIT_RARITY_EXTRA));
		mCategory.setText(data
				.getStringExtra(EditSneakerEntryActivity.EDIT_CATEGORY_EXTRA));
		mDesc.setText(data
				.getStringExtra(EditSneakerEntryActivity.EDIT_DESC_EXTRA));
	}
}
