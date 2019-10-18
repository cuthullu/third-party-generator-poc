package com.scottlogic.deg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.deg.generators.FieldValueSource;
import com.scottlogic.deg.generators.Generator;
import com.scottlogic.deg.generators.faker.FakerFieldValueSourceFactory;
import com.scottlogic.deg.generators.mockaroo.MockarooFieldValueSourceFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Profile p = mapper.readValue(new File("profile.json"), Profile.class);

        List<FieldOutput> outFields = Arrays.stream(p.fields).map(field -> new FieldOutput(
                field.name,
                createValueSource(field)
        )).collect(Collectors.toList());

        int MAX_OUT = 100;
        for (int i = 0; i < MAX_OUT; i++) {
            String row = outFields.stream().map(FieldOutput::toString).collect(Collectors.joining(", "));
            System.out.println(row);
        }
    }

    static FieldValueSource createValueSource(Field field) {
        return field.generator == Generator.FAKER ?
                FakerFieldValueSourceFactory.createFieldValueSource(field.type) :
                MockarooFieldValueSourceFactory.createFieldValueSource(field);
    }

}
