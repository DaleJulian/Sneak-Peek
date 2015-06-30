package com.cyscorpions.dalejulian.sneakpeek.activities;

import java.util.ArrayList;

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.adapters.SneakerCategoryAdapter;
import com.cyscorpions.dalejulian.sneakpeek.models.CategoryDirectory;
import com.cyscorpions.dalejulian.sneakpeek.models.SneakerCategory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(MainActivity.this, SneakerEntryListActivity.class);
		i.putExtra(CATEGORY_EXTRA, mCategories.get(position).getName());
		startActivity(i);

	}
}
