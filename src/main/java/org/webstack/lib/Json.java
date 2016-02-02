package org.webstack.lib;

import javax.json.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Json {

    public static class Pair {
        public final String name;
        public final Object value;
        public Pair(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }

    public static String stringify(JsonStructure value) {
        StringWriter stWriter = new StringWriter();
        try (JsonWriter jsonWriter = javax.json.Json.createWriter(stWriter)) {
            jsonWriter.write(value);
        }
        return stWriter.toString();
    }

    public static JsonStructure parse(String value) {
        return javax.json.Json.createReader(new StringReader(value)).read();
    }

    public static Pair pair(String name, JsonValue value) {
        return new Pair(name, value);
    }
    public static Pair pair(String name, JsonStructure value) {
        return new Pair(name, value);
    }
    public static Pair pair(String name, String value) {
        return new Pair(name, value);
    }
    public static Pair pair(String name, BigInteger value){
        return new Pair(name, value);
    }
    public static Pair pair(String name, BigDecimal value){
        return new Pair(name, value);
    }
    public static Pair pair(String name, int value){
        return new Pair(name, value);
    }
    public static Pair pair(String name, long value){
        return new Pair(name, value);
    }
    public static Pair pair(String name, double value){
        return new Pair(name, value);
    }
    public static Pair pair(String name, boolean value){
        return new Pair(name, value);
    }
    public static Pair nullPair(String name){
        return new Pair(name, null);
    }

    public static JsonObject obj(Pair... pairs) {
        JsonObjectBuilder objectBuilder = javax.json.Json.createObjectBuilder();
        for (Pair o : pairs) {
            if (o.value == null) {
                objectBuilder.addNull(o.name);
            } else {
                if (JsonValue.class.isAssignableFrom(o.value.getClass())) {
                    objectBuilder.add(o.name, (JsonValue) o.value);
                } else if (o.value.getClass().equals(String.class)) {
                    objectBuilder.add(o.name, (String) o.value);
                } else if (o.value.getClass().equals(BigInteger.class)) {
                    objectBuilder.add(o.name, (BigInteger) o.value);
                } else if (o.value.getClass().equals(BigDecimal.class)) {
                    objectBuilder.add(o.name, (BigDecimal) o.value);
                } else if (o.value.getClass().equals(Integer.class)) {
                    objectBuilder.add(o.name, (Integer) o.value);
                } else if (o.value.getClass().equals(Long.class)) {
                    objectBuilder.add(o.name, (Long) o.value);
                } else if (o.value.getClass().equals(Double.class)) {
                    objectBuilder.add(o.name, (Double) o.value);
                } else if (o.value.getClass().equals(Boolean.class)) {
                    objectBuilder.add(o.name, (Boolean) o.value);
                } else {
                    throw new RuntimeException("Unknown type");
                }
            }
        }
        return objectBuilder.build();
    }

    public static JsonArray arr(Iterable<Object> iterable) {
        JsonArrayBuilder arrayBuilder = javax.json.Json.createArrayBuilder();
        for (Object o : iterable) {
            if (o == null) {
                arrayBuilder.addNull();
            } else {
                if (JsonValue.class.isAssignableFrom(o.getClass())) {
                    arrayBuilder.add((JsonValue) o);
                } else if (o.getClass().equals(String.class)) {
                    arrayBuilder.add((String) o);
                } else if (o.getClass().equals(BigInteger.class)) {
                    arrayBuilder.add((BigInteger) o);
                } else if (o.getClass().equals(BigDecimal.class)) {
                    arrayBuilder.add((BigDecimal) o);
                } else if (o.getClass().equals(Integer.class)) {
                    arrayBuilder.add((Integer) o);
                } else if (o.getClass().equals(Long.class)) {
                    arrayBuilder.add((Long) o);
                } else if (o.getClass().equals(Double.class)) {
                    arrayBuilder.add((Double) o);
                } else if (o.getClass().equals(Boolean.class)) {
                    arrayBuilder.add((Boolean) o);
                } else {
                    throw new RuntimeException("Unknown type");
                }
            }
        }
        return arrayBuilder.build();
    }

}
