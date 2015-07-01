package com.cyscorpions.dalejulian.sneakpeek.activities;

import java.util.ArrayList;

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.models.Sneaker;
import com.cyscorpions.dalejulian.sneakpeek.models.SneakerCategory;
import com.cyscorpions.dalejulian.sneakpeek.models.SneakerDirectory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

	public static final String KEYEXTRA_NAME = "edit_name";
	public static final String KEYEXTRA_BRAND = "edit_brand";
	public static final String KEYEXTRA_RARITY = "edit_rarity";
	public static final String KEYEXTRA_SELLVAL = "edit_selval";
	public static final String KEYEXTRA_THUMBNAILID = "edit_thmbnlid";
	public static final String KEYEXTRA_CATEGORY = "edit_categ";
	public static final String KEYEXTRA_DESCRIPTION = "edit_desc";
	public static final String KEYEXTRA_ID = "edit_id";

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
	}

	private void setupEditView() {
		final boolean isFromDetailActivity = getIntent().getBooleanExtra(
				SneakerDetailsActivity.DETAILS_FROMDETAILS_EXTRA, false);
		final boolean isFromListActivity = getIntent().getBooleanExtra(
				SneakerEntryListActivity.LIST_FROMLIST_EXTRA, false);

		mSneaker.setBrand(getIntent().getStringExtra(KEYEXTRA_BRAND));
		mSneaker.setName(getIntent().getStringExtra(KEYEXTRA_NAME));
		mSneaker.setRarity(getIntent().getStringExtra(KEYEXTRA_RARITY));
		mSneaker.setSellingValue(getIntent().getStringExtra(KEYEXTRA_SELLVAL));
		mSneaker.setThumbnailId(getIntent()
				.getIntExtra(KEYEXTRA_THUMBNAILID, 0));
		mSneaker.setCategory(getIntent().getStringExtra(KEYEXTRA_CATEGORY));
		mSneaker.setDescription(getIntent()
				.getStringExtra(KEYEXTRA_DESCRIPTION));
		id = getIntent().getStringExtra(KEYEXTRA_ID);

		if (isFromDetailActivity || isFromListActivity) {
			mName.setText(mSneaker.getName());
			mBrand.setText(mSneaker.getBrand());
			mSellVal.setText(mSneaker.getSellingValue());
			@SuppressWarnings("deprecation")
			Drawable imgDrawable = getResources().getDrawable(
					mSneaker.getThumbnailId());
			mThumbnail.setImageDrawable(imgDrawable);
			mRarity.setText(mSneaker.getRarity());
			mDesc.setText(mSneaker.getDescription());
			mCategory.setText(mSneaker.getCategory().getName().toString());
		}

		mSaveButton = (Button) findViewById(R.id.btnSaveData);

		mSaveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFromDetailActivity || isFromListActivity) {
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
							i.putExtra(EDIT_NAME_EXTRA, ss.getName().toString());
							i.putExtra(EDIT_BRAND_EXTRA, ss.getBrand()
									.toString());
							i.putExtra(EDIT_SELLVAL_EXTRA, ss.getSellingValue()
									.toString());
							i.putExtra(EDIT_RARITY_EXTRA, ss.getRarity()
									.toString());
							i.putExtra(EDIT_CATEGORY_EXTRA, ss.getCategory()
									.getName().toString());
							i.putExtra(EDIT_DESC_EXTRA, ss.getDescription()
									.toString());
							setResult(RESULT_OK, i);
							finish();
							break;
						}
					}
				} else {
					if (mName.getText().toString().matches("")) {
						Toast.makeText(getApplicationContext(),
								"Sneaker name is required.", Toast.LENGTH_SHORT)
								.show();
					} else {
						saveSneaker();
						Intent intent = new Intent();
						setResult(RESULT_OK, intent);
						finish();
					}
				}
			}
		});
	}

}
