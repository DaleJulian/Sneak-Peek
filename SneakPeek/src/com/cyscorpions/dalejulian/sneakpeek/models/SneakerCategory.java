package com.cyscorpions.dalejulian.sneakpeek.models;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class SneakerCategory {

	// JSON TAGS
	private static final String JSON_ID = "id";
	private static final String JSON_NAME = "name";
	private static final String JSON_DESCRIPTION = "description";

	private UUID mId;
	private String mName;
	private String mDescription;

	// not yet implemented(low prio)
	// private SneakPeekPhoto mIcon;

	public SneakerCategory(String name, String desc) {
		mName = name;
		mDescription = desc;
	}

	public SneakerCategory() {
		mId = UUID.randomUUID();
	}

	public UUID getId() {
		return this.mId;
	}

	public String getName() {
		return this.mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getDescription() {
		return this.mDescription;
	}

	public void setDescription(String desc) {
		this.mDescription = desc;
	}

	public SneakerCategory(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		mName = json.getString(JSON_NAME);
		mDescription = json.getString(JSON_DESCRIPTION);
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_NAME, mName);
		json.put(JSON_DESCRIPTION, mDescription);
		return json;
	}

}
