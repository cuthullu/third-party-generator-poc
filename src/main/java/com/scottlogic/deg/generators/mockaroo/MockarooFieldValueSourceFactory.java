package com.scottlogic.deg.generators.mockaroo;

import com.scottlogic.deg.Field;
import com.scottlogic.deg.generators.FieldValueSource;

public class MockarooFieldValueSourceFactory {

    public static FieldValueSource createFieldValueSource(Field field) {
        return new MockarooFieldValueSource<String>(field);
    }
}
