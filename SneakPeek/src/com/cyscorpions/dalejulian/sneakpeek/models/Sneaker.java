package com.cyscorpions.dalejulian.sneakpeek.models;

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
	private static final String JSON_CATEGORY = null;

	private UUID mId;
	// private SneakerPhoto mPhoto;
	private String mName;
	private String mBrand;
	// private String[] mColorway;
	private String mRarity;
	private String mSellingValue;
	private String mDescription;
	private SneakerCategory mCategory = new SneakerCategory();
	private int mThumbnailId;

	public Sneaker(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		mName = json.getString(JSON_NAME);
		mBrand = json.getString(JSON_BRAND);
		mRarity = json.getString(JSON_RARITY);
		mSellingValue = json.getString(JSON_SELLINGVALUE);
		mDescription = json.getString(JSON_DESCRIPTION);
		mCategory.setName(json.getString(JSON_CATEGORY));
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_NAME, mName);
		json.put(JSON_BRAND, mBrand);
		json.put(JSON_RARITY, mRarity.toString());
		json.put(JSON_SELLINGVALUE, mSellingValue);
		json.put(JSON_DESCRIPTION, mDescription);
		json.put(JSON_CATEGORY, mCategory.getName());
		return json;
	}

	public Sneaker(String name) {
		mName = name;
		mId = UUID.randomUUID();

	}

	public Sneaker() {
		mId = UUID.randomUUID();
	}

	public Sneaker(String name, String brand, String rarity, String sellValue,
			String desc, SneakerCategory category, int imageResId) {
		mName = name;
		mBrand = brand;
		mRarity = rarity;
		mSellingValue = sellValue;
		mDescription = desc;
		mId = UUID.randomUUID();
		mCategory = category;
		mThumbnailId = imageResId;
	}

	public UUID getId() {
		return this.mId;
	}

	public void setId(String id) {
		mId = UUID.fromString(id);
	}

	public String getName() {
		return this.mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getBrand() {
		return this.mBrand;
	}

	public void setBrand(String brand) {
		this.mBrand = brand;
	}

	public String getRarity() {
		return this.mRarity;
	}

	public void setRarity(String rarity) {
		this.mRarity = rarity;
	}

	public String getSellingValue() {
		return this.mSellingValue;
	}

	public void setSellingValue(String value) {
		this.mSellingValue = value;
	}

	public String getDescription() {
		return this.mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}

	public int getThumbnailId() {
		return this.mThumbnailId;
	}

	public void setThumbnailId(int thumbnailId) {
		this.mThumbnailId = thumbnailId;
	}

	public SneakerCategory getCategory() {
		return this.mCategory;
	}

	public void setCategory(String category) {
		this.mCategory.setName(category);
	}

	public String getTitleName() {
		String s = this.mBrand + " " + this.mName;
		return s;
	}

	public void copyValuesFromSneaker(Sneaker sneaker) {
		this.mName = sneaker.mName;
		this.mBrand = sneaker.mBrand;
		this.mRarity = sneaker.mRarity;
		this.mSellingValue = sneaker.mSellingValue;
		this.mCategory = sneaker.mCategory;
		this.mDescription = sneaker.mDescription;
		this.mThumbnailId = sneaker.mThumbnailId;
	}
}
