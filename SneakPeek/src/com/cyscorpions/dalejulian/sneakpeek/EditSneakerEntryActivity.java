package com.cyscorpions.dalejulian.sneakpeek;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditSneakerEntryActivity extends Activity {

	private EditText mCategory;
	private Button mSaveButton;
	private EditText mName, mBrand, mRarity, mSellVal, mDesc;
	private Sneaker mSneaker = new Sneaker();
	private ImageView mThumbnail;

	public static final String EDIT_NAME_EXTRA = "name";
	public static final String EDIT_BRAND_EXTRA = "brand";
	public static final String EDIT_SELLVAL_EXTRA = "sellval";
	public static final String EDIT_CATEGORY_EXTRA = "categ";
	public static final String EDIT_DESC_EXTRA = "desc";
	public static final String EDIT_RARITY_EXTRA = "rare";

	private String id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sneaker_editentry_activity);

		findResourceIds();
		setupEditView();
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public interface DataChangedListener {
		void onDataChanged();
	}

	private void findResourceIds() {
		mCategory = (EditText) findViewById(R.id.txtSneakerCategory);
		mSaveButton = (Button) findViewById(R.id.btnSaveData);
		mName = (EditText) findViewById(R.id.txtSneakerName);
		mBrand = (EditText) findViewById(R.id.txtSneakerBrand);
		mRarity = (EditText) findViewById(R.id.txtSneakerRarity);
		mSellVal = (EditText) findViewById(R.id.txtSellingValue);
		mDesc = (EditText) findViewById(R.id.txtSneakerDescription);
		mThumbnail = (ImageView) findViewById(R.id.imgSneakerEntry);
	}

	private void saveSneaker() {
		SneakerCategory category = new SneakerCategory();
		category.setName(mCategory.getText().toString());
		Sneaker sneaker = new Sneaker(mName.getText().toString(), mBrand
				.getText().toString(), mRarity.getText().toString(), mSellVal
				.getText().toString(), mDesc.getText().toString(), category, 0);

		SneakerDirectory.get(this).addSneaker(sneaker);
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}

	private void setupEditView() {
		boolean isFromDetailActivity = getIntent().getBooleanExtra(
				SneakerDetailsActivity.DETAILS_FROMDETAILS_EXTRA, false);
		boolean isFromListActivity = getIntent().getBooleanExtra(
				SneakerEntryList.LIST_FROMLIST_EXTRA, false);

		if (isFromDetailActivity) {
			mSneaker.setBrand(getIntent().getStringExtra(
					SneakerDetailsActivity.DETAILS_BRAND_EXTRA));
			mSneaker.setName(getIntent().getStringExtra(
					SneakerDetailsActivity.DETAILS_NAME_EXTRA));
			mSneaker.setRarity(getIntent().getStringExtra(
					SneakerDetailsActivity.DETAILS_RARITY_EXTRA));
			mSneaker.setSellingValue(getIntent().getStringExtra(
					SneakerDetailsActivity.DETAILS_SELLING_EXTRA));
			mSneaker.setThumbnailId(getIntent().getIntExtra(
					SneakerDetailsActivity.DETAILS_IMGID_EXTRA, 0));
			mSneaker.setDescription(getIntent().getStringExtra(
					SneakerDetailsActivity.DETAILS_DESC_EXTRA));
			mSneaker.setCategory(getIntent().getStringExtra(
					SneakerDetailsActivity.DETAILS_CATEGORY_EXTRA));
			id = getIntent().getStringExtra(
					SneakerDetailsActivity.DETAILS_UUID_EXTRA);
		} else if (isFromListActivity) {
			mSneaker.setBrand(getIntent().getStringExtra(
					SneakerEntryList.LIST_BRAND_EXTRA));
			mSneaker.setName(getIntent().getStringExtra(
					SneakerEntryList.LIST_NAME_EXTRA));
			mSneaker.setRarity(getIntent().getStringExtra(
					SneakerEntryList.LIST_RARITY_EXTRA));
			mSneaker.setSellingValue(getIntent().getStringExtra(
					SneakerEntryList.LIST_SELLING_EXTRA));
			mSneaker.setThumbnailId(getIntent().getIntExtra(
					SneakerEntryList.LIST_IMGID_EXTRA, 0));
			mSneaker.setDescription(getIntent().getStringExtra(
					SneakerEntryList.LIST_DESC_EXTRA));
			mSneaker.setCategory(getIntent().getStringExtra(
					SneakerEntryList.LIST_CATEGORY_EXTRA));
			id = getIntent().getStringExtra(SneakerEntryList.LIST_ID_EXTRA);
		} else {
			mCategory.setText(getIntent().getStringExtra(
					SneakerEntryList.LIST_CATEGORY_EDIT_EXTRA));
			mSaveButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					saveSneaker();
				}
			});
			return;
		}

		mName.setText(mSneaker.getName());
		mBrand.setText(mSneaker.getBrand());
		mSellVal.setText(mSneaker.getSellingValue());
		@SuppressWarnings("deprecation")
		Drawable imgDrawable = getResources().getDrawable(mSneaker.getThumbnailId());
		mThumbnail.setImageDrawable(imgDrawable);
		mRarity.setText(mSneaker.getRarity());
		mDesc.setText(mSneaker.getDescription());
		mCategory.setText(mSneaker.getCategory().getName().toString());

		mSaveButton = (Button) findViewById(R.id.btnSaveData);
		mSaveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<Sneaker> s = SneakerDirectory.get(
						getApplicationContext()).getAllSneakers();
				for (Sneaker ss : s) {

					if (id.equals(ss.getId().toString())) {
						Log.i("Edit Activity", "Sneaker found");
						ss.setName(mName.getText().toString());
						ss.setBrand(mBrand.getText().toString());
						ss.setSellingValue(mSellVal.getText().toString());
						ss.setRarity(mRarity.getText().toString());
						ss.setDescription(mDesc.getText().toString());
						ss.setCategory(mCategory.getText().toString());
						SneakerDirectory.get(getApplicationContext())
								.saveSneakers();
						Intent i = new Intent();
						i.putExtra(EDIT_NAME_EXTRA, ss.getName());
						i.putExtra(EDIT_BRAND_EXTRA, ss.getBrand());
						i.putExtra(EDIT_SELLVAL_EXTRA, ss.getSellingValue());
						i.putExtra(EDIT_RARITY_EXTRA, ss.getRarity());
						i.putExtra(EDIT_CATEGORY_EXTRA, ss.getCategory()
								.getName().toString());
						i.putExtra(EDIT_DESC_EXTRA, ss.getDescription());
						setResult(RESULT_OK, i);
						finish();
						break;
					}
				}
				Log.i("Edit Activity", "Done searching");
			}
		});
	}

}
