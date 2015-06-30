package com.cyscorpions.dalejulian.sneakpeek.models;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class SneakerDirectory {
	private static final String TAG = "SneakerDirectory";
	private static final String FILENAME = "sneakers.json";

	private ArrayList<Sneaker> mSneakers;
	private SneakerJSONSerializer mSerializer;

	private static SneakerDirectory sSneakerDirectory;
	private Context mAppContext;

	private SneakerDirectory(Context appContext) {
		mAppContext = appContext;
		mSerializer = new SneakerJSONSerializer(mAppContext, FILENAME);

		try {
			mSneakers = mSerializer.loadSneakers();

		} catch (Exception e) {
			mSneakers = new ArrayList<Sneaker>();
			Log.e(TAG, "Error loading sneakers");
		}
	}

	public static SneakerDirectory get(Context appContext) {
		if (sSneakerDirectory == null) {
			sSneakerDirectory = new SneakerDirectory(
					appContext.getApplicationContext());
		}
		return sSneakerDirectory;
	}

	public Sneaker getSneaker(UUID sneakerId) {
		for (Sneaker sneaker : mSneakers) {
			if (sneaker.getId().equals(sneakerId)) {
				return sneaker;
			}
		}
		return null;
	}
	
	public void deleteSneaker(Sneaker sneaker) {
		mSneakers.remove(sneaker);
		saveSneakers();
	}

	public void addSneaker(Sneaker sneaker) {
		mSneakers.add(sneaker);
		saveSneakers();
	}

	public boolean saveSneakers() {
		try {
			mSerializer.saveSneakers(mSneakers);
			Log.d(TAG, "Sneakers saved to file.");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving sneakers");
			return false;
		}
	}

	public ArrayList<Sneaker> getAllSneakers() {
		return this.mSneakers;
	}

	public ArrayList<Sneaker> getSneakersByCategory(String categoryName) {
		ArrayList<Sneaker> temp = new ArrayList<Sneaker>();
		for (Sneaker sneak : mSneakers) {
			if (sneak.getCategory().getName().equals(categoryName)) {
				temp.add(sneak);
			}
		}
		return temp;
	}
}
