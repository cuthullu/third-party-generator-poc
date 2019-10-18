package com.scottlogic.deg.generators.mockaroo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.deg.Field;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MockarooService {

    public static MockarooResponse[] get(Field field ) throws IOException {
        String apiKey = System.getenv("mockaroo_key");
        URL url = new URL("https://api.mockaroo.com/api/generate.json?count=100&key=" + apiKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        MockarooRequestField[] mockarooRequests = new MockarooRequestField[] {new MockarooRequestField(field.type)};

        try(OutputStream os = connection.getOutputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(os, mockarooRequests);
            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            return mapper.readValue(content.toString(), MockarooResponse[].class);
        } finally {
            connection.disconnect();
        }
    }
}
