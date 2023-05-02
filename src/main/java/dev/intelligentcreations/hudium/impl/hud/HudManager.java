package dev.intelligentcreations.hudium.impl.hud;

import dev.intelligentcreations.hudium.Constants;
import dev.intelligentcreations.hudium.HudRegistry;
import dev.intelligentcreations.hudium.api.hud.Component;
import dev.intelligentcreations.hudium.api.hud.ComponentType;
import dev.intelligentcreations.hudium.util.NumberUtil;
import dev.intelligentcreations.hudium.util.Phys;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.json5.JsonReader;
import org.quiltmc.json5.JsonWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ApiStatus.Internal
public final class HudManager {
    private final Map<Phys.Position, Component> components;

    public HudManager() {
        this.components = new HashMap<>();
        reload();
    }

    public void reload() {
        if (!CONFIG_FILE.toFile().exists()) return;
        try (JsonReader reader = JsonReader.json5(CONFIG_FILE)) {
            reader.beginObject();
            while (reader.hasNext()) {
                // Keeps the switch statement here in case there are more entries to add
                switch (reader.nextName()) {
                    case "components" -> {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            ComponentType<?> componentType = null;
                            Phys.Position pos = null;
                            reader.beginObject();
                            while (reader.hasNext()) {
                                switch (reader.nextName()) {
                                    case "type" ->
                                            componentType = HudRegistry.getEntry(new Identifier(reader.nextString()));
                                    case "pos" -> {
                                        pos = new Phys.Position(0, 0);
                                        reader.beginObject();
                                        while (reader.hasNext()) {
                                            switch (reader.nextName()) {
                                                case "x" -> pos.setX(NumberUtil.requireNonNegative(reader.nextInt()));
                                                case "y" -> pos.setY(NumberUtil.requireNonNegative(reader.nextInt()));
                                                default -> reader.skipValue();
                                            }
                                        }
                                        reader.endObject();
                                    }
                                    default -> reader.skipValue();
                                }
                            }
                            reader.endObject();
                            components.putIfAbsent(
                                    Objects.requireNonNull(pos, "The position is null"),
                                    Objects.requireNonNull(
                                            componentType,
                                            "The component type cannot be queued from the registry"
                                    ).create()
                            );
                        }
                        reader.endArray();
                    }
                    default -> reader.skipValue();
                }
            }
            reader.endObject();
        } catch (IOException e) {
            Constants.LOGGER.error("Failed to parse component config", e);
        }
    }

    public void saveConfig() {
        if (!Constants.HUD_CONFIG_DIR.toFile().exists())
            Constants.HUD_CONFIG_DIR.toFile().mkdirs();
        if (!CONFIG_FILE.toFile().exists()) try {
            CONFIG_FILE.toFile().createNewFile();
        } catch (IOException e) {
            Constants.LOGGER.error("Failed to create config file - Config will not be saved!", e);
            return;
        }

        try (JsonWriter writer = JsonWriter.json5(CONFIG_FILE)) {
            writer.setIndent("    ");
            writer.beginObject();
            writer.comment("The components serialized into JSON5.")
                    .name("components");
            writer.beginArray();
            components.forEach((pos, component) -> {
                Identifier id = HudRegistry.getId(component.getType());
                if (id != null) {
                    try {
                        writer.beginObject();
                        writer.name("type")
                                .value(id.toString());
                        writer.name("pos")
                                .beginObject()
                                .name("x").value(pos.x())
                                .name("y").value(pos.y())
                                .endObject();
                        writer.endObject();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            writer.endArray();
            writer.endObject();
        } catch (IOException e) {
            Constants.LOGGER.error("Failed to save component config", e);
        }
    }

    public void render(MatrixStack matrices, @NotNull PlayerEntity camera, float tickDelta) {
        components.forEach((pos, component) -> component.render(matrices, camera, tickDelta, pos));
    }

    public static final Path CONFIG_FILE = Constants.HUD_CONFIG_DIR.resolve("components.json5");
}
