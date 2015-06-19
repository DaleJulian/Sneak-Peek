package com.cyscorpions.dalejulian.sneakpeek;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SneakerEntryList extends Activity {

	private ListView mEntryList;
	private ArrayList<Sneaker> mSneakers;
	private SneakerAdapter adapter;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sneaker_entry_listview);
		
		mSneakers = SneakerDirectory.get(getApplicationContext()).getSneakers();
	
		adapter = new SneakerAdapter(this, R.layout.sneaker_category_listitem, mSneakers);
		
		mEntryList = (ListView) findViewById(R.id.listSneakerEntries);
		mEntryList.setAdapter(adapter);
	}
}
