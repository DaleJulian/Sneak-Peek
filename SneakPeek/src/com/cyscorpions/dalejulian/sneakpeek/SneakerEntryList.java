package com.cyscorpions.dalejulian.sneakpeek;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SneakerEntryList extends Activity implements OnItemClickListener {

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
	public static final String LIST_TITLE_EXTRA = "title";
	public static final String LIST_ID_EXTRA = "id";
	public static final String LIST_CATEGORY_EDIT_EXTRA = "editextra";

	private SneakerCategory mCategory = new SneakerCategory();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sneaker_entry_listview);

		mCategory.setName(getIntent().getStringExtra(
				MainActivity.CATEGORY_EXTRA));
		setTitle("Sneak Peek: " + mCategory.getName());

		mSneakers = SneakerDirectory.get(getApplicationContext())
				.getSneakersByCategory(mCategory.getName());
		mAdapter = new SneakerAdapter(this, R.layout.sneaker_entry_listitem,
				mSneakers);
		mEntryList = (ListView) findViewById(R.id.listSneakerEntries);
		mEntryList.setAdapter(mAdapter);
		mEntryList.setOnItemClickListener(this);
		
		registerForContextMenu(mEntryList);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Sneaker selectedSneaker = mSneakers.get(position);
		Intent i = new Intent(SneakerEntryList.this,
				SneakerDetailsActivity.class);
		i.putExtra(LIST_NAME_EXTRA, selectedSneaker.getName());
		i.putExtra(LIST_BRAND_EXTRA, selectedSneaker.getBrand());
		i.putExtra(LIST_SELLING_EXTRA, selectedSneaker.getSellingValue());
		i.putExtra(LIST_RARITY_EXTRA, selectedSneaker.getRarity());
		i.putExtra(LIST_CATEGORY_EXTRA, selectedSneaker.getCategory().getName());
		i.putExtra(LIST_DESC_EXTRA, selectedSneaker.getDescription());
		i.putExtra(LIST_IMGID_EXTRA, selectedSneaker.getThumbnailId());
		i.putExtra(LIST_TITLE_EXTRA, selectedSneaker.getTitleName());
		i.putExtra(LIST_ID_EXTRA, selectedSneaker.getId().toString());
		startActivityForResult(i, 1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Add Sneak Peek Entry");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle() == "Add Sneak Peek Entry") {
			Intent i = new Intent(SneakerEntryList.this,
					EditSneakerEntryActivity.class);
			i.putExtra(LIST_CATEGORY_EDIT_EXTRA, this.mCategory.getName()
					.toString());
			startActivityForResult(i, 1);

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		updateList();
	}

	private void updateList() {
		ArrayList<Sneaker> s = SneakerDirectory.get(getApplicationContext())
				.getSneakersByCategory(mCategory.getName());

		mAdapter.clear();
		mAdapter.addAll(s);
		mAdapter.notifyDataSetChanged();
	}

}
