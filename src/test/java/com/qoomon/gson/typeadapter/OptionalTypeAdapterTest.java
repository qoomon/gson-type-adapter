package com.qoomon.gson.typeadapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * Created by qoomon on 07/12/2016.
 */
public class OptionalTypeAdapterTest {

    Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Optional.class, new OptionalTypeAdapter())
            .setPrettyPrinting()
            .create();

    @Test
    public void deserialize() throws Exception {

        // GIVEN
        String json = "{\n  \"value\": 123\n}";

        // WHEN
        TestObject testObject = gson.fromJson(json, TestObject.class);

        // THEN
        assertThat(testObject.value.get(), is(123));
    }

    @Test
    public void serialize() throws Exception {
        // GIVEN
        TestObject testObject = new TestObject(123);

        // WHEN
        String json = gson.toJson(testObject);

        // THEN
        assertThat(json, is("{\n  \"value\": 123\n}"));

    }


    public static class TestObject {

        final Optional<Integer> value;

        TestObject(Integer value) {
            this.value = Optional.ofNullable(value);
        }
    }

}