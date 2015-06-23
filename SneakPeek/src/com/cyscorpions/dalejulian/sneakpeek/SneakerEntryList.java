package com.cyscorpions.dalejulian.sneakpeek;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SneakerEntryList extends Activity implements OnItemClickListener{

	private ListView mEntryList;
	private ArrayList<Sneaker> mSneakers;
	private SneakerAdapter mAdapter;
	
	public static final String LIST_NAME_EXTRA = "name";
	public static final String LIST_BRAND_EXTRA = "brand";
	public static final String LIST_SELLING_EXTRA = "selling";
	public static final String LIST_RARITY_EXTRA = "rarity";
	public static final String LIST_CATEGORY_EXTRA = "extra";
	public static final String LIST_DESC_EXTRA = "desc";
	public static final String LIST_IMGID_EXTRA = "img";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sneaker_entry_listview);

		SneakerCategory category = new SneakerCategory();
		category.setName(getIntent()
				.getStringExtra(MainActivity.CATEGORY_EXTRA));
		setTitle("Sneak Peek: " + category.getName());
		// Log.i("SneakerEntryList", category.getName());

		mSneakers = SneakerDirectory.get(getApplicationContext()).getSneakersByCategory(category.getName());
		mAdapter = new SneakerAdapter(this, R.layout.sneaker_entry_listitem, mSneakers);
		mEntryList = (ListView) findViewById(R.id.listSneakerEntries);
		mEntryList.setAdapter(mAdapter);
		mEntryList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Sneaker selectedSneaker = mSneakers.get(position);
		Intent i = new Intent(SneakerEntryList.this, SneakerDetailsActivity.class);
		i.putExtra(LIST_NAME_EXTRA, selectedSneaker.getName());
		i.putExtra(LIST_BRAND_EXTRA, selectedSneaker.getBrand());
		i.putExtra(LIST_SELLING_EXTRA, selectedSneaker.getSellingValue());
		i.putExtra(LIST_RARITY_EXTRA, selectedSneaker.getRarity());
		i.putExtra(LIST_CATEGORY_EXTRA, selectedSneaker.getCategory().getName());
		i.putExtra(LIST_DESC_EXTRA, selectedSneaker.getDescription());
		i.putExtra(LIST_IMGID_EXTRA, selectedSneaker.getThumbnailId());
		
		//Bundle bundle = new Bundle();
		//bundle.putInt(LIST_IMGID_EXTRA, selectedSneaker.getThumbnailId());
		//i.putExtras(bundle);
		startActivity(i);
		
	}
}
