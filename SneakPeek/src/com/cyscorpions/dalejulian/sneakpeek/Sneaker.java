package com.cyscorpions.dalejulian.sneakpeek;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Sneaker {

	// JSON TAGS
	private static final String JSON_ID = "id";
	private static final String JSON_NAME = "name";
	private static final String JSON_BRAND = "brand";
	private static final String JSON_RARITY = null;
	private static final String JSON_SELLINGVALUE = "sellingvalue";
	private static final String JSON_DESCRIPTION = "description";

	private UUID mId;
	// private SneakerPhoto mPhoto;
	private String mName;
	private String mBrand;
	// private String[] mColorway;
	private SneakerRarity mRarity;
	private String mSellingValue;
	private String mDescription;

	public Sneaker(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		mName = json.getString(JSON_NAME);
		mBrand = json.getString(JSON_BRAND);
		mRarity = SneakerRarity.valueOf(json.getString(JSON_RARITY));
		mSellingValue = json.getString(JSON_SELLINGVALUE);
		mDescription = json.getString(JSON_DESCRIPTION);

	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_NAME, mName);
		json.put(JSON_BRAND, mBrand);
		json.put(JSON_RARITY, mRarity.toString());
		json.put(JSON_SELLINGVALUE, mSellingValue);
		json.put(JSON_DESCRIPTION, mDescription);
		return json;
	}

	public Sneaker() {
		mId = UUID.randomUUID();
	}

	public UUID getId() {
		return this.mId;
	}

	public String getName() {
		return this.mName;
	}

	public void setname(String name) {
		this.mName = name;
	}

	public String getBrand() {
		return this.mBrand;
	}

	public void setBrand(String brand) {
		this.mBrand = brand;
	}

	public SneakerRarity getRarity() {
		return this.mRarity;
	}

	public void setRarity(SneakerRarity rarity) {
		this.mRarity = rarity;
	}

	public String getSellingValue() {
		return this.mSellingValue;
	}

	public void setSellingValue(String value) {
		this.mSellingValue = value;
	}

	private String getDescription() {
		return this.mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}

}
