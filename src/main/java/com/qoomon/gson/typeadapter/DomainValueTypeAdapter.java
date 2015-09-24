package com.qoomon.gson.typeadapter;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.qoomon.domainvalue.type.DV;

/**
 * Created by b.brodersen on 22/09/15.
 */
public class DomainValueTypeAdapter implements JsonSerializer<DV<?>>, JsonDeserializer<DV<?>> {

    @Override
    public DV<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        @SuppressWarnings("unchecked")
        Class<? extends DV<Object>> targetType = (Class<? extends DV<Object>>) typeOfT;
        Class<?> targetValueType = DV.getValueType(targetType);
        Object sourceValue = context.deserialize(json, targetValueType);
        return DV.of(targetType, sourceValue);
    }

    @Override
    public JsonElement serialize(DV<?> src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.value());
    }
}
