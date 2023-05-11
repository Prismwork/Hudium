package dev.intelligentcreations.hudium.api.hud.config;

import dev.intelligentcreations.hudium.Constants;
import org.quiltmc.json5.JsonReader;
import org.quiltmc.json5.JsonToken;
import org.quiltmc.json5.JsonWriter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface ComponentConfig {
    Map<String, ConfigValue<?>> asMap();

    default Optional<ConfigValue<?>> getValue(String key) {
        return Optional.ofNullable(asMap().get(key));
    }

    default void read(JsonReader reader) throws IOException {
        boolean isWrappedInObject = reader.peek().equals(JsonToken.BEGIN_OBJECT);
        if (isWrappedInObject) reader.beginObject();
        while (reader.hasNext()) {
            getValue(reader.nextName()).ifPresentOrElse(
                    value -> {
                        try {
                            value.read(reader);
                        } catch (IOException e) {
                            Constants.LOGGER.error("Failed to read config value", e);
                        }
                    },
                    () -> {
                        try {
                            reader.skipValue();
                        } catch (IOException e) {
                            Constants.LOGGER.error("Failed to read config value", e);
                        }
                    }
            );
        }
        if (isWrappedInObject) reader.endObject();
    }

    default void save(JsonWriter writer) throws IOException {
        writer.beginObject();
        for (Map.Entry<String, ConfigValue<?>> entry: asMap().entrySet()) {
            entry.getValue().write(writer.name(entry.getKey()));
        }
        writer.endObject();
    }
}
