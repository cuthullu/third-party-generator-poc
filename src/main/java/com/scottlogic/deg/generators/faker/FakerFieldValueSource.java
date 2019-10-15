package com.scottlogic.deg.generators.faker;

import com.github.javafaker.Faker;
import com.scottlogic.deg.generators.FieldValueSource;

import java.util.stream.Stream;

public class FakerFieldValueSource<T> implements FieldValueSource<T> {
    private Faker randomFaker;
    private FakerCall<T> fakerCall;

    FakerFieldValueSource(FakerCall<T> fakerCall) {
        this.randomFaker = new Faker();
        this.fakerCall = fakerCall;
    }

    @Override
    public Stream<T> generateInterestingValues() {
        return null;
    }

    @Override
    public Stream<T> generateAllValues() {
        return null;
    }

    @Override
    public Stream<T> generateRandomValues() {
        return Stream.generate(() -> fakerCall.generate(this.randomFaker));
    }
}
