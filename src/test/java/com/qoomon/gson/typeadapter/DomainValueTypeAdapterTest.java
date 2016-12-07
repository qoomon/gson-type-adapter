package com.qoomon.gson.typeadapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qoomon.domainvalue.type.DV;
import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * Created by b.brodersen on 22/09/15.
 */
public class DomainValueTypeAdapterTest {

    Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(DV.class, new DomainValueTypeAdapter())
            .setPrettyPrinting()
            .create();

    @Test
    public void deserialize() throws Exception {

        // GIVEN
        String json = "{\n  \"dv\": 123\n}";

        // WHEN
        TestObject testObject = gson.fromJson(json, TestObject.class);

        // THEN
        assertThat(testObject.dv.value(), is(123));
    }

    @Test
    public void serialize() throws Exception {
        // GIVEN
        TestObject testDv = new TestObject(TestDv.of(123));

        // WHEN
        String json = gson.toJson(testDv);

        // THEN
        assertThat(json, is("{\n  \"dv\": 123\n}"));
    }

    public static class TestObject {

        final TestDv dv;

        public TestObject(TestDv dv) {
            this.dv = dv;
        }
    }

    public static class TestDv extends DV<Integer> {

        protected TestDv(Integer value) {
            super(value);
        }

        public static boolean isValid(Integer value) {
            return DV.isValid(value);
        }

        public static  TestDv of(Integer value) {
            return new TestDv(value);
        }
    }
}