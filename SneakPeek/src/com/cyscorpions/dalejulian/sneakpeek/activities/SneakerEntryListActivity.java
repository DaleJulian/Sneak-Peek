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
import android.util.Log;
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

public class SneakerEntryListActivity extends Activity implements
		OnItemClickListener {
	public static final int RECEIVE_NEW_ENTRY_REQUEST = 3;
	public static final int RECEIVE_UPDATED_ENTRY_REQUEST = 2;

	public static final String EXTRA_SER_SNEAKER = "sneakerentrylistactivity.sneakerbundle";

	private ListView mEntryList;
	private ArrayList<Sneaker> mSneakers;
	private SneakerAdapter mAdapter;

	public static final String LIST_FROMLIST_EXTRA = "fromlist";

	public static final String BUNDLETAG_SNEAKER = "detailsneaker";

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
		Sneaker selectedSneaker = (Sneaker) view
				.getTag(R.id.TAG_SNEAKER_OBJECT);
		Intent i = new Intent(SneakerEntryListActivity.this,
				SneakerDetailsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(BUNDLETAG_SNEAKER, selectedSneaker);
		i.putExtras(bundle);
		startActivityForResult(i, RECEIVE_UPDATED_ENTRY_REQUEST);
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
			i.putExtra(EditSneakerEntryActivity.KEYEXTRA_CATEGORY,
					this.mCategory.getName().toString());
			startActivityForResult(i, RECEIVE_NEW_ENTRY_REQUEST);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("SneakerList", String.valueOf(requestCode));

		sortEntriesAlphabetically(mSneakers);
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
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("editsneaker", sneaker);
			i.putExtras(mBundle);
			i.putExtra(LIST_FROMLIST_EXTRA, true);
			startActivityForResult(i, RECEIVE_NEW_ENTRY_REQUEST);
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
