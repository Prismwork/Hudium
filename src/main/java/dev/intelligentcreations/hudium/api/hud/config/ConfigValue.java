package dev.intelligentcreations.hudium.api.hud.config;

import org.quiltmc.json5.JsonReader;
import org.quiltmc.json5.JsonWriter;

import java.io.IOException;

public abstract class ConfigValue<T> {
    protected T value;

    public ConfigValue(T defaultValue) {
        this.value = defaultValue;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public abstract void read(JsonReader reader) throws IOException;

    public abstract void write(JsonWriter writer) throws IOException;

    public static class BooleanValue extends ConfigValue<Boolean> {
        public BooleanValue(Boolean defaultValue) {
            super(defaultValue);
        }

        @Override
        public void read(JsonReader reader) throws IOException {
            this.set(reader.nextBoolean());
        }

        @Override
        public void write(JsonWriter writer) throws IOException {
            writer.value(value);
        }
    }

    public static abstract class NumberValue<N extends Number>
            extends ConfigValue<N> {
        public NumberValue(N defaultValue) {
            super(defaultValue);
        }

        @Override
        public void write(JsonWriter writer) throws IOException {
            writer.value(value);
        }
    }

    public static class IntegerValue extends NumberValue<Integer> {
        public IntegerValue(Integer defaultValue) {
            super(defaultValue);
        }

        @Override
        public void read(JsonReader reader) throws IOException {
            this.set(reader.nextInt());
        }
    }

    public static class DoubleValue extends NumberValue<Double> {
        public DoubleValue(Double defaultValue) {
            super(defaultValue);
        }

        @Override
        public void read(JsonReader reader) throws IOException {
            this.set(reader.nextDouble());
        }
    }

    public static class StringValue extends ConfigValue<String> {
        public StringValue(String defaultValue) {
            super(defaultValue);
        }

        @Override
        public void read(JsonReader reader) throws IOException {
            this.set(reader.nextString());
        }

        @Override
        public void write(JsonWriter writer) throws IOException {
            writer.value(value);
        }
    }

    public abstract static class EnumValue<E extends Enum<E>> extends ConfigValue<E> {
        public EnumValue(E defaultValue) {
            super(defaultValue);
        }

        @Override
        public void write(JsonWriter writer) throws IOException {
            writer.value(value.toString());
        }
    }
}
