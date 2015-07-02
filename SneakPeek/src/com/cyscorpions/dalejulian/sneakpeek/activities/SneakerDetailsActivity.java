package com.cyscorpions.dalejulian.sneakpeek.activities;

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.models.Sneaker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class SneakerDetailsActivity extends Activity {
	public static final int RECEIVE_EDITED_DETAILS_REQUEST = 5;

	public static final String DETAILS_FROMDETAILS_EXTRA = "fromDetails";

	private TextView mName, mBrand, mSellValue, mRarity, mCategory, mDesc;
	private ImageView mThumbnail;

	private Sneaker sneaker = new Sneaker();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sneaker_entry_activity);
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

	@SuppressLint("NewApi")
	private void setupContentByExtras() {

		Bundle bundle = getIntent().getExtras();
		sneaker = (Sneaker) bundle
				.getSerializable(SneakerEntryListActivity.BUNDLETAG_SNEAKER);
		mName.setText(sneaker.getName());
		mBrand.setText(sneaker.getBrand());
		mSellValue.setText(sneaker.getSellingValue());
		mRarity.setText(sneaker.getRarity());
		mCategory.setText(sneaker.getCategory().getName().toString());
		mDesc.setText(sneaker.getDescription());
		int imgResourceId = sneaker.getThumbnailId();
		if (imgResourceId != 0) {
			Drawable imageDrawable = ContextCompat.getDrawable(
					getApplicationContext(), imgResourceId);
			mThumbnail.setImageDrawable(imageDrawable);
		}
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
			Bundle bundle = new Bundle();
			bundle.putSerializable("editsneaker", sneaker);
			i.putExtras(bundle);
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_ID, sneaker.getId()
					.toString());
			i.putExtra(DETAILS_FROMDETAILS_EXTRA, true);
			startActivityForResult(i, RECEIVE_EDITED_DETAILS_REQUEST);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int resultCode, int requestCode, Intent data) {

		if (requestCode == RECEIVE_EDITED_DETAILS_REQUEST) {
			Sneaker s = (Sneaker) getIntent().getSerializableExtra(
					"updatedsneaker");
			mName.setText(s.getName());
			mBrand.setText(s.getBrand());
			mSellValue.setText(s.getSellingValue());
			mRarity.setText(s.getRarity());
			mCategory.setText(s.getCategory().getName().toString());
			mDesc.setText(s.getDescription());
		}

	}
}
