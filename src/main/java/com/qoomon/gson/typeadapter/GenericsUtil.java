package com.qoomon.gson.typeadapter;

import com.qoomon.domainvalue.type.DV;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by qoomon on 07/12/2016.
 */
public class GenericsUtil {

    private static Map<Class<? extends DV>, Class<?>> valueTypeCache = new WeakHashMap<>();

    public static Type getGenericType(Type type) {

        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments()[0];
        }

        Type superType = ((Class<?>) type).getGenericSuperclass();

        if (superType == null) {
            throw new IllegalArgumentException("Could not find any generic type");
        }

        return getGenericType(superType);


    }
}
