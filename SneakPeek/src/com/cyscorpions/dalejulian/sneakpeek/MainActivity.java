package com.cyscorpions.dalejulian.sneakpeek;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView categoryList;
	private ArrayList<SneakerCategory> mCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCategories = CategoryDirectory.get(getApplicationContext())
				.getCategories();
		mCategories.add(new SneakerCategory("Basketball",
				"Jordan Brand, Kobe, Lebrons.."));
		mCategories.add(new SneakerCategory("Basketball",
				"Jordan Brand, Kobe, Lebrons.."));
		mCategories.add(new SneakerCategory("Basketball",
				"Jordan Brand, Kobe, Lebrons.."));
		mCategories.add(new SneakerCategory("Basketball",
				"Jordan Brand, Kobe, Lebrons.."));
		mCategories.add(new SneakerCategory("Basketball",
				"Jordan Brand, Kobe, Lebrons.."));
		mCategories.add(new SneakerCategory("Basketball",
				"Jordan Brand, Kobe, Lebrons.."));

		SneakerCategoryAdapter adapter = new SneakerCategoryAdapter(this,
				R.layout.sneaker_category_layout, mCategories);
		categoryList = (ListView) findViewById(R.id.listSneakercategory);
		categoryList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
