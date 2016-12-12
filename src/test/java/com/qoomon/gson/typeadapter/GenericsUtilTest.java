package com.qoomon.gson.typeadapter;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

/**
 * Created by qoomon on 08/12/2016.
 */
public class GenericsUtilTest {

    @Test
    public void getTypeParameters() throws Exception {
        // Given

        // When
        Type genericType = GenericsUtil.getTypeParameters(Callable.class, TestGenericObject.class).get(0);

        // Then

        assertTrue(genericType.equals(String.class));

    }

    @Test(expected = IllegalArgumentException.class)
    public void getTypeParameters_THROW_exception_WHEN_base_class_is_not_generic() throws Exception {
        // Given

        // When
        Type genericType = GenericsUtil.getTypeParameters(Object.class, Object.class).get(0);

        // Then
        fail();

    }

    @Test(expected = IllegalArgumentException.class)
    public void getTypeParameters_THROW_exception_WHEN_child_type_does_not_extend_base_class() throws Exception {
        // Given

        // When
        Type genericType = GenericsUtil.getTypeParameters(Callable.class, Object.class).get(0);

        // Then
        fail();

    }

    class TestGenericObject implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "foo";
        }
    }

}