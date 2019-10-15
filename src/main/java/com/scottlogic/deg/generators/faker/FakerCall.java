package com.scottlogic.deg.generators.faker;

import com.github.javafaker.Faker;

public interface FakerCall<T> {
    T generate(Faker faker);
}
