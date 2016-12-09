package com.qoomon.gson.typeadapter;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

/**
 * Created by qoomon on 08/12/2016.
 */
public class GenericsUtilTest {

    @Test
    public void getGenericType() throws Exception {
        // Given


        Type genericSuperclass = TestGenericObject.class.getGenericSuperclass();
        System.out.println(genericSuperclass.getTypeName());

        Optional<String> optional = Optional.empty();

        // When
        Type genericType = GenericsUtil.getTypeParameters(Callable.class, TestGenericObject.class).get(0);

        // Then

        assertTrue(genericType.equals(String.class));

    }

    class TestGenericObject implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "foo";
        }
    }

}