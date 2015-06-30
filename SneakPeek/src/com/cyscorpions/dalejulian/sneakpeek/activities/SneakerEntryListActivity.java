package com.cyscorpions.dalejulian.sneakpeek.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.adapters.SneakerAdapter;
import com.cyscorpions.dalejulian.sneakpeek.models.Sneaker;
import com.cyscorpions.dalejulian.sneakpeek.models.SneakerCategory;
import com.cyscorpions.dalejulian.sneakpeek.models.SneakerDirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SneakerEntryListActivity extends Activity implements OnItemClickListener {

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
	public static final String LIST_FROMLIST_EXTRA = "fromlist";

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

		sortEntriesAlphabetically(mSneakers);
		mAdapter = new SneakerAdapter(this, R.layout.sneaker_entry_listitem,
				mSneakers);
		mEntryList = (ListView) findViewById(R.id.listSneakerEntries);
		mEntryList.setAdapter(mAdapter);
		mEntryList.setOnItemClickListener(this);

		registerForContextMenu(mEntryList);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Sneaker selectedSneaker = mSneakers.get(position);
		Intent i = new Intent(SneakerEntryListActivity.this,
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
			Intent i = new Intent(SneakerEntryListActivity.this,
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
		ArrayList<Sneaker> sneakers = SneakerDirectory.get(
				getApplicationContext()).getSneakersByCategory(
				mCategory.getName());
		sortEntriesAlphabetically(sneakers);
		mAdapter.clear();
		mAdapter.addAll(sneakers);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sneaker_list_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;
		Sneaker sneaker = mAdapter.getItem(position);

		switch (item.getItemId()) {
		case R.id.menu_list_edit_sneaker:
			Intent i = new Intent(SneakerEntryListActivity.this,
					EditSneakerEntryActivity.class);
			i.putExtra(LIST_NAME_EXTRA, sneaker.getName());
			i.putExtra(LIST_BRAND_EXTRA, sneaker.getBrand());
			i.putExtra(LIST_SELLING_EXTRA, sneaker.getSellingValue());
			i.putExtra(LIST_RARITY_EXTRA, sneaker.getRarity());
			i.putExtra(LIST_CATEGORY_EXTRA, sneaker.getCategory().getName()
					.toString());
			i.putExtra(LIST_DESC_EXTRA, sneaker.getDescription());
			i.putExtra(LIST_IMGID_EXTRA, sneaker.getThumbnailId());
			i.putExtra(LIST_TITLE_EXTRA, sneaker.getTitleName());
			i.putExtra(LIST_ID_EXTRA, sneaker.getId().toString());
			i.putExtra(LIST_FROMLIST_EXTRA, true);
			startActivityForResult(i, 1);
			break;
		case R.id.menu_list_delete_sneaker:
			SneakerDirectory.get(getApplicationContext())
					.deleteSneaker(sneaker);
			updateList();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void sortEntriesAlphabetically(List<Sneaker> sneakerList) {
		Collections.sort(sneakerList, new Comparator<Sneaker>() {
			public int compare(Sneaker obj1, Sneaker obj2) {
				return obj1.getTitleName().compareToIgnoreCase(
						obj2.getTitleName());
			}
		});
	}
}
