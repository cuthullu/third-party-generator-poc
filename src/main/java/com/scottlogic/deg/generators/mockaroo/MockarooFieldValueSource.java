package com.scottlogic.deg.generators.mockaroo;

import com.scottlogic.deg.Field;
import com.scottlogic.deg.generators.FieldValueSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class MockarooFieldValueSource<T> implements FieldValueSource {
    private final Field field;

    public MockarooFieldValueSource(Field field) {
        this.field = field;
    }

    @Override
    public Stream generateInterestingValues() {
        return null;
    }

    @Override
    public Stream generateAllValues() {
        return null;
    }

    @Override
    public Stream<String> generateRandomValues() {
        try {
            MockarooResponse[] values =  MockarooService.get(field);
            return Arrays.stream(values).map(m -> m.value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
