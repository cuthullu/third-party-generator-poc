package com.scottlogic.deg;

import com.scottlogic.deg.generators.FieldValueSource;

import java.util.Iterator;

public class FieldOutput {

    private String name;
    private Iterator valueStream;

    public FieldOutput(String name, FieldValueSource fieldValueSource) {
        this.name = name;
        this.valueStream = fieldValueSource.generateRandomValues().iterator();
    }

    @Override
    public String toString() {
        return name + ": " + valueStream.next();

    }
}
