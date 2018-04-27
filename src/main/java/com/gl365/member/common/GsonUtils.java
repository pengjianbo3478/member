package com.gl365.member.common;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonUtils {

	public static Gson getGson() {
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			@Override
			public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
				return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			}
		}).registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
			@Override
			public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
				return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			}
		}).registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				String datetime = json.getAsJsonPrimitive().getAsString();
				return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}
		}).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
			@Override
			public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				String datetime = json.getAsJsonPrimitive().getAsString();
				return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}
		}).create();

		return gson;
	}
	
	public static String toJson(Object src) {
		Gson gson = GsonUtils.getGson();
		return gson.toJson(src);
	}

}
