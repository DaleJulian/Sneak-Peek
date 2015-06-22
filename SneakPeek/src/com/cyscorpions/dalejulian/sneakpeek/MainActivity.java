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

public class MainActivity extends Activity implements OnItemClickListener {
	public static final String CATEGORY_EXTRA = "Category Extra";
	private ListView mCategoryList;
	private ArrayList<SneakerCategory> mCategories;
	private SneakerCategoryAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Sneak Peek: Categories");

		mCategories = CategoryDirectory.get(getApplicationContext())
				.getCategories();

		mAdapter = new SneakerCategoryAdapter(this,
				R.layout.sneaker_category_listitem, mCategories);
		mCategoryList = (ListView) findViewById(R.id.listSneakercategory);
		mCategoryList.setAdapter(mAdapter);
		mCategoryList.setOnItemClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
		CategoryDirectory.get(getApplicationContext()).saveCategories();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			CategoryDirectory.get(getApplicationContext()).addCategory(
					new SneakerCategory("Fancy", "Margiela.."));
			mAdapter.notifyDataSetChanged();
			Intent i = new Intent(MainActivity.this, SneakerEntryList.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(MainActivity.this, SneakerEntryList.class);
		i.putExtra(CATEGORY_EXTRA, mCategories.get(position).getName());
		//Log.i("MainActivity", mCategories.get(position).getName());
		startActivity(i);

	}
}
