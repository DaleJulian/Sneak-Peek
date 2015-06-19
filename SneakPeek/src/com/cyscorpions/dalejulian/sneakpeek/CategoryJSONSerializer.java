package com.cyscorpions.dalejulian.sneakpeek;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class CategoryJSONSerializer {

	private Context mContext;
	private String mFileName;

	public CategoryJSONSerializer(Context c, String f) {
		this.mContext = c;
		this.mFileName = f;
	}

	public ArrayList<SneakerCategory> loadCategories() throws JSONException,
			IOException {
		ArrayList<SneakerCategory> categories = new ArrayList<SneakerCategory>();
		categories.add(new SneakerCategory("Basketball",
				"Air Jordans, Lebrons, Kobes.."));
		categories.add(new SneakerCategory("Running",
				"Nike Air Max, Adidas Pure Boost, Under Armour.."));
		categories.add(new SneakerCategory("Skateboarding",
				"NikeSB, Emerica, DC Shoes.."));
		categories.add(new SneakerCategory("Designer",
				"Balenciaga, Maison Martin,  Saint Laurent.."));
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFileName);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			JSONArray jsonArray = (JSONArray) new JSONTokener(
					jsonString.toString()).nextValue();
			for (int i = 0; i < jsonArray.length(); i++) {
				categories.add(new SneakerCategory(jsonArray.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {

		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return categories;
	}

	public void saveCategories(ArrayList<SneakerCategory> categories)
			throws JSONException, IOException {
		JSONArray jsonArray = new JSONArray();
		for (SneakerCategory category : categories) {
			jsonArray.put(category.toJSON());
		}

		// write file to disk
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFileName,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(jsonArray.toString());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}
}
