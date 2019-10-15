package com.scottlogic.deg.generators.faker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class FakerFieldValueSourceFactory {

    /**
     * For the types I've bother to manually map it safely creates a FakerCall to construct the fieldValueSource
     * For a faker YAML expression #{<expression/>} it calls faker to resolve the expression
     * For anything else it parses the type and does some ugly reflection to try and find a faker method to call
     * @param type should match on of the Faker method calls, nested calls are dot (.) delimited
     * @return a fieldValueSource to produce values of the desired type from faker
     */
    public static FakerFieldValueSource createFieldValueSource(String type) {
        switch (type) {
            case "name.firstName":
                return new FakerFieldValueSource<>(faker -> faker.name().firstName());
            case "name.lastName":
                return new FakerFieldValueSource<>(faker -> faker.name().lastName());
            case "number.randomDigit":
                return new FakerFieldValueSource<>(faker -> faker.number().randomDigit());
            default:
                if(type.matches("#\\{.*}"))
                    return new FakerFieldValueSource<>(faker -> faker.expression(type));
                return new FakerFieldValueSource<>(faker -> {
                    try {
                        return genericFakerCall(type.split("\\."), faker);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        System.out.println("Invalid faker type declaration " + type);
                        return null;
                    }
                });
        }
    }

    private static Object genericFakerCall(String[] subTypes, Object invokee) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = invokee.getClass();
        Method method = clazz.getMethod(subTypes[0]);
        Object returnedValue = method.invoke(invokee);
        if(subTypes.length == 1) {
           return returnedValue;
        }
        String[] tail = Arrays.copyOfRange(subTypes, 1, subTypes.length);
        return genericFakerCall(tail, returnedValue);
    };
}
