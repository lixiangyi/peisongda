package com.theaty.versionmanagerlibrary.model.GsonAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ThtGosn {

	public ThtGosn() {

	}

	public static Gson genGson() {
		GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(
				Double.class, new BadDoubleDeserializer());
		gsonBuilder.registerTypeAdapter(Integer.class,
				new BadIntegerDeserializer());
		return gsonBuilder.create();

	}
}
