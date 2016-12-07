package com.qoomon.gson.typeadapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Created by qoomon on 07/12/2016.
 */
public class OptionalTypeAdapter implements JsonDeserializer<Optional<?>>, JsonSerializer<Optional<?>> {

    @Override
    public Optional<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Type targetValueType = GenericsUtil.getGenericType(typeOfT);
        Object value = context.deserialize(json, targetValueType);
        return Optional.ofNullable(value);
    }

    @Override
    public JsonElement serialize(Optional<?> src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.orElse(null));
    }
}