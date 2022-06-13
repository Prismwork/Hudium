package dev.intelligentcreations.hudium.api.info.plugin;

import dev.intelligentcreations.hudium.HudiumClient;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class InfoPluginHandler {
    private static final List<InfoPlugin> plugins = new ArrayList<>();

    public static <T extends InfoPlugin> void register(Class<T> pluginClass) {
        try {
            T plugin = pluginClass.getDeclaredConstructor().newInstance();
            plugins.add(plugin);
            HudiumClient.LOGGER.info("Registered plugin " + pluginClass.getName());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("No valid constructor found for " + pluginClass.getName());
        }
    }

    public static List<InfoPlugin> getPlugins() {
        return plugins;
    }
}
