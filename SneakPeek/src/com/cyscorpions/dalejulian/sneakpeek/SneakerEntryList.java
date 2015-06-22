package com.cyscorpions.dalejulian.sneakpeek;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class SneakerEntryList extends Activity {

	private ListView mEntryList;
	private ArrayList<Sneaker> mSneakers;
	private SneakerAdapter mAdapter;

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
	}
}
