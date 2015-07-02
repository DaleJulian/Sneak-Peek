package com.cyscorpions.dalejulian.sneakpeek.activities;

import java.util.ArrayList;
import java.util.UUID;

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.models.Sneaker;
import com.cyscorpions.dalejulian.sneakpeek.models.SneakerCategory;
import com.cyscorpions.dalejulian.sneakpeek.models.SneakerDirectory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

	public static final String KEYEXTRA_CATEGORY = "edit_categ";
	public static final String KEYEXTRA_ID = "edit_id";

	public static final String KEYEXTRA_SNK = "edit_snk";

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

	private void setSneakerValues(Sneaker sneaker) {
		sneaker.setName(mName.getText().toString());
		sneaker.setBrand(mBrand.getText().toString());
		sneaker.setSellingValue(mSellVal.getText().toString());
		sneaker.setRarity(mRarity.getText().toString());
		sneaker.setDescription(mDesc.getText().toString());
		sneaker.setCategory(mCategory.getText().toString());
	}

	private void setupEditView() {
		final boolean isFromDetailActivity = getIntent().getBooleanExtra(
				SneakerDetailsActivity.DETAILS_FROMDETAILS_EXTRA, false);
		final boolean isFromListActivity = getIntent().getBooleanExtra(
				SneakerEntryListActivity.LIST_FROMLIST_EXTRA, false);

		Bundle mBundle = new Bundle();
		mBundle = getIntent().getExtras();
		mSneaker = (Sneaker) mBundle.getSerializable(SneakerDetailsActivity.BUNDLETAG_SNEAKEROBJ);

		if (isFromDetailActivity || isFromListActivity) {
			id = mSneaker.getId().toString();
			Drawable imgDrawable = ContextCompat.getDrawable(
					getApplicationContext(), mSneaker.getThumbnailId());
			mThumbnail.setImageDrawable(imgDrawable);
			mName.setText(mSneaker.getName());
			mBrand.setText(mSneaker.getBrand());
			mSellVal.setText(mSneaker.getSellingValue());
			mRarity.setText(mSneaker.getRarity());
			mDesc.setText(mSneaker.getDescription());
			mCategory.setText(mSneaker.getCategory().getName().toString());
			
		} else {
			mCategory.setText(getIntent().getStringExtra(KEYEXTRA_CATEGORY));
		}
		mCategory.setEnabled(false);
		mCategory.setCursorVisible(false);
		mCategory.setKeyListener(null);
		mCategory.setBackgroundColor(Color.TRANSPARENT);

		mSaveButton = (Button) findViewById(R.id.btnSaveData);
		mSaveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFromDetailActivity || isFromListActivity) {
					ArrayList<Sneaker> s = SneakerDirectory.get(
							getApplicationContext()).getAllSneakers();
					for (Sneaker ss : s) {
						if (id.equals(ss.getId().toString())) {
							setSneakerValues(ss);
							SneakerDirectory.get(getApplicationContext())
									.saveSneakers();
							Intent i = new Intent();
							Bundle sneakerBundle = new Bundle();
							sneakerBundle.putSerializable("updatedsneaker", ss);
							i.putExtra("updatedsneaker", sneakerBundle);
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
