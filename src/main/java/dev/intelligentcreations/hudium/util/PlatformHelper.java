package dev.intelligentcreations.hudium.util;

import dev.intelligentcreations.hudium.api.HudiumPlugin;

import java.nio.file.Path;
import java.util.List;

public final class PlatformHelper {
    public static Path getConfigDir() {
        throw new AssertionError("You should never see this");
    }

    public static List<HudiumPlugin> getAllPlugins() {
        throw new AssertionError("You should never see this");
    }
}
