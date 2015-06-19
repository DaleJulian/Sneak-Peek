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

public class SneakerJSONSerializer {

	private Context mContext;
	private String mFileName;

	public SneakerJSONSerializer(Context c, String f) {
		this.mContext = c;
		this.mFileName = f;
	}

	public ArrayList<Sneaker> loadSneakers() throws IOException, JSONException {
		ArrayList<Sneaker> sneakers = new ArrayList<Sneaker>();
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
				sneakers.add(new Sneaker(jsonArray.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {

		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return sneakers;
	}

	public void saveSneakers(ArrayList<Sneaker> sneakers) throws JSONException,
			IOException {
		JSONArray jsonArray = new JSONArray();
		for (Sneaker sneaker : sneakers) {
			jsonArray.put(sneaker.toJSON());
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
