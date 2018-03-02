package com.theaty.versionmanagerlibrary.model.GsonAdapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class BadIntegerDeserializer implements JsonDeserializer<Integer> {

	@Override
	public Integer deserialize(JsonElement element, Type type,
                               JsonDeserializationContext context) throws JsonParseException {
		try {
			// return element.getAsNumber();

			return Integer.parseInt(element.getAsString());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}