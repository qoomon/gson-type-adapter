package com.qoomon.gson.typeadapter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;

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
public class DomainValueTypeAdapter implements JsonSerializer<DV>, JsonDeserializer<DV> {

    private static final String OF_METHOD_NAME = "of";

    private static Map<Class<? extends DV>, Method> ofMethodCache = new WeakHashMap<>();
    private static Map<Class<? extends DV>, Class<?>> valueTypeCache = new WeakHashMap<>();

    @Override
    public DV deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Class<? extends DV> targetType = (Class<? extends DV>) typeOfT;

        Class<?> targetValueType = getValueType(targetType);
        Object sourceValue = context.deserialize(json, targetValueType);
        try {
            Method ofMethod = getOfMethod(targetType, targetValueType);
            return (DV) ofMethod.invoke(targetType, sourceValue);
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }


    @Override
    public JsonElement serialize(DV src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.value());
    }


    private Class<?> getValueType(Class<? extends DV> dvType) {
        Class<?> valueType = valueTypeCache.get(dvType);
        
        if (valueType == null) {
            while (valueType == null && dvType != null) {
                if (dvType.getSuperclass().equals(DV.class)) {
                    ParameterizedType genericSuperclass = (ParameterizedType) dvType.getGenericSuperclass();
                    valueType = (Class<?>) genericSuperclass.getActualTypeArguments()[0];
                }
                dvType = (Class<? extends DV>) dvType.getSuperclass();
            }
            valueTypeCache.put(dvType, valueType);
        }
        return valueType;
    }

    private Method getOfMethod(Class<? extends DV> targetType, Class<?> targetValueType) throws NoSuchMethodException {
        Method ofMethod = ofMethodCache.get(targetType);
        if (ofMethod == null) {
            ofMethod = targetType.getMethod(OF_METHOD_NAME, targetValueType);
            ofMethodCache.put(targetType, ofMethod);
        }
        return ofMethod;
    }

}
