package com.cyscorpions.dalejulian.sneakpeek.models;

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

import com.cyscorpions.dalejulian.sneakpeek.R;
import com.cyscorpions.dalejulian.sneakpeek.R.drawable;

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
		sneakers.add(new Sneaker(
				"IV Undefeated",
				"Air Jordan",
				"Hyperstrike",
				"$5,000",
				"A very limited release made by Jordan Brand in collaboration with UNDFTD.",
				CategoryDirectory.get(mContext).getCategory("Basketball"),
				R.drawable.jordanivundefeated));

		sneakers.add(new Sneaker(
				"X What the MVP",
				"Lebron",
				"Hyperstrike",
				"$3000",
				"A 'What The' sneaker that incorporates designs from other Lebron sneakers",
				CategoryDirectory.get(mContext).getCategory("Basketball"),
				R.drawable.lbj10whatthemvp));

		sneakers.add(new Sneaker("Max 90 Beaches of Rio", "Nike Air",
				"General Release", "$45,000",
				"One of AM90's neckbreakers in terms of color combination.",
				CategoryDirectory.get(mContext).getCategory("Running"),
				R.drawable.nikeairmax90beachesofrio));

		sneakers.add(new Sneaker("Diamond Dunk Lows", "Nike SB",
				"Hyperstrike", "$45,000",
				"One of the most sought after dunks released in 2005.",
				CategoryDirectory.get(mContext).getCategory("Skateboarding"),
				R.drawable.nikesbdunklowtiffany));

		sneakers.add(new Sneaker("Paris", "Nike SB", "Hyperstrike",
				"$2,000", "The most wanted pair among the 'City Pack' Series",
				CategoryDirectory.get(mContext).getCategory("Skateboarding"),
				R.drawable.nikesbparis));

		sneakers.add(new Sneaker(
				"Stefan Janoski Michael Lau",
				"Nike SB",
				"500 pairs worldwide",
				"$1,000",
				"Designed by a Toy Creator, Michael Lau, this Stefan Janoski shoe is the most expensive SJ of all time.",
				CategoryDirectory.get(mContext).getCategory("Skateboarding"),
				R.drawable.nikesbjanoskimichaellau));

		sneakers.add(new Sneaker(
				"Max 1 Atmos Elephant",
				"Nike Air",
				"100 pairs made",
				"$2,000",
				"A part of the 'Animal Series' Pack of the Atmos Collection, this sneaker is inspired by elephant tusks.",
				CategoryDirectory.get(mContext).getCategory("Running"),
				R.drawable.nikeairmax1atmoselephant));

		sneakers.add(new Sneaker(
				"Burger",
				"Saucony x End",
				"Quickstrike",
				"$2,000",
				"A collaboration between Saucony and END, this sneaker incorporates the colors of an old classic American hamburger.",
				CategoryDirectory.get(mContext).getCategory("Running"),
				R.drawable.sauconyendburger));

		sneakers.add(new Sneaker(
				"VI Doernbecher",
				"Air Jordan",
				"Hyperstrike",
				"$2,000",
				"A glow-in-the-dark sneaker which is a part of the Doernbecher series in which all sales will go to a Hospital for children with chronic illness.",
				CategoryDirectory.get(mContext).getCategory("Basketball"),
				R.drawable.airjordan6db));

		sneakers.add(new Sneaker(
				"Heinekens",
				"Nike SB",
				"Hyperstrike",
				"$2,000",
				"A discontinued sneaker due to copyright infringment case filed by Heineken against Nike. Only a few pairs got sold before the case has been filed.",
				CategoryDirectory.get(mContext).getCategory("Skateboarding"),
				R.drawable.nikesbheineken));

		sneakers.add(new Sneaker(
				"Supreme Dunk Lows",
				"Nike SB",
				"Hyperstrike",
				"$5,000",
				"A very rare sneaker and the most sought after among the Supreme Dunk Low series.",
				CategoryDirectory.get(mContext).getCategory("Skateboarding"),
				R.drawable.nikesbsupremedunklows));

		sneakers.add(new Sneaker(
				"Arenas Red",
				"Balenciagas",
				"Made by order",
				"$5,000",
				"A sneaker often seen worn by Kanye West, Usher and Jaden Smith.",
				CategoryDirectory.get(mContext).getCategory("Designer"),
				R.drawable.balenciagaarenared));

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
			int jsonArrayLength = jsonArray.length();
			for (int i = 0; i < jsonArrayLength; i++) {
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
