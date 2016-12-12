package com.qoomon.gson.typeadapter;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by qoomon on 07/12/2016.
 */
public class GenericsUtil {

    /**
     * Get the actual type arguments a child class has used to extend a generic
     * base class.
     *
     * @param baseClass  the base class
     * @param childClass the child class
     * @param <T>        The type of the base class against which the type arguments
     *                   relate
     * @return a list of the raw classes for the actual type arguments.
     */
    public static <T> List<Type> getTypeParameters(final Class<T> baseClass, final Class<? extends T> childClass) {
        return getTypeParameters(baseClass, (Type) childClass);
    }

    public static List<Type> getTypeParameters(final Class<?> baseClass, final Type childType) {

        if (baseClass.getTypeParameters().length <= 0) {
            throw new IllegalArgumentException(baseClass + " needs to be generic.");
        }

        List<Type> typeParameters = findTypeParameters(baseClass, childType);

        if (typeParameters.isEmpty()) {
            throw new IllegalArgumentException(childType + " needs to extend " + baseClass);
        }

        return typeParameters;

    }

    private static List<Type> findTypeParameters(final Class<?> baseClass, final Type childType) {

        if (childType instanceof Class<?>) {
            Class<?> childClass = (Class<?>) childType;

            // super class
            {
                List<Type> result = findTypeParameters(baseClass, childClass.getGenericSuperclass());
                if (!result.isEmpty()) {
                    return result;
                }
            }

            // interfaces
            Type[] genericInterfaces = childClass.getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                List<Type> result = findTypeParameters(baseClass, genericInterface);
                if (!result.isEmpty()) {
                    return result;
                }
            }

            return Collections.emptyList();
        } else if (childType instanceof ParameterizedType) {
            ParameterizedType childParameterizedType = (ParameterizedType) childType;
            return Arrays.asList(childParameterizedType.getActualTypeArguments());
        } else if (childType instanceof GenericArrayType) {
            GenericArrayType childGenericArrayType = (GenericArrayType) childType;
            return findTypeParameters(baseClass, childGenericArrayType.getGenericComponentType());
        } else {
            return Collections.emptyList();
        }
    }
}
