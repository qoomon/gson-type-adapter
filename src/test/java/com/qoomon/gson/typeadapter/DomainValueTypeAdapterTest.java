package com.qoomon.gson.typeadapter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qoomon.domainvalue.type.DV;

/**
 * Created by b.brodersen on 22/09/15.
 */
public class DomainValueTypeAdapterTest {

    private Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(DV.class, new DomainValueTypeAdapter())
            .setPrettyPrinting()
            .create();

    @Test
    public void deserialize() throws Exception {

        // GIVEN
        String json = "100001";

        // WHEN
        BankAccountNumber bankAccount = gson.fromJson(json, BankAccountNumber.class);

        // THEN
        assertThat(bankAccount.value(), is(100001));
    }

    @Test
    public void serialize() throws Exception {
        // GIVEN
        BankAccountNumber bankAccount = BankAccountNumber.of(100001);

        // WHEN
        String json = gson.toJson(bankAccount);

        // THEN
        assertThat(json, is("100001"));

    }

    public static class BankAccountNumber extends DV<Integer> {

        protected BankAccountNumber(Integer value) {
            super(value);
        }

        public static boolean isValid(Integer value) {
            return DV.isValid(value) &&
                    value > 100_000;
        }

        public static BankAccountNumber of(Integer value) {
            return new BankAccountNumber(value);
        }
    }
}