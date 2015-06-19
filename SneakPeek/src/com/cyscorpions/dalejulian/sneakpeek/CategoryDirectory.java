package com.cyscorpions.dalejulian.sneakpeek;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class CategoryDirectory {

	private static final String TAG = "CategoryDirectory";
	private static final String FILENAME = "category.json";

	private ArrayList<SneakerCategory> mCategories;
	private CategoryJSONSerializer mSerializer;

	private static CategoryDirectory sCategoryDirectory;
	private Context mAppContext;

	private CategoryDirectory(Context appContext) {
		mAppContext = appContext;
		mSerializer = new CategoryJSONSerializer(mAppContext, FILENAME);

		try {
			mCategories = mSerializer.loadCategories();
		} catch (Exception e) {
			mCategories = new ArrayList<SneakerCategory>();
			Log.e(TAG, "Error loading categories");
		}
	}

	public static CategoryDirectory get(Context appContext) {
		if (sCategoryDirectory == null) {
			sCategoryDirectory = new CategoryDirectory(
					appContext.getApplicationContext());

		}
		return sCategoryDirectory;
	}

	public SneakerCategory getCategory(UUID categoryId) {
		for (SneakerCategory category : mCategories) {
			if (category.getId().equals(categoryId)) {
				return category;
			}
		}
		return null;
	}
	
	public void addCategory(SneakerCategory category){
		mCategories.add(category);
		saveCategories();
	}
	
	public boolean saveCategories() {
		try {
			mSerializer.saveCategories(mCategories);
			Log.d(TAG, "Categories saved to file.");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving categories.");
			return false;
		}
	}
	
	public ArrayList<SneakerCategory> getCategories(){
		return this.mCategories;
	}
}
