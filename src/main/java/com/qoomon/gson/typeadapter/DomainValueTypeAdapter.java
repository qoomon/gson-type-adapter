package com.qoomon.gson.typeadapter;

import com.google.gson.*;
import com.qoomon.domainvalue.type.DV;

import java.lang.reflect.Type;

/**
 * Created by b.brodersen on 22/09/15.
 */
public class DomainValueTypeAdapter implements JsonSerializer<DV<?>>, JsonDeserializer<DV<?>> {

    @Override
    @SuppressWarnings("unchecked")
    public DV<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Type targetValueType = GenericsUtil.getTypeParameters(DV.class, typeOfT).get(0);
        Object value = context.deserialize(json, targetValueType);
        return DV.of((Class<? extends DV<Object>>) typeOfT, value);
    }

    @Override
    public JsonElement serialize(DV<?> src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.value());
    }
}
