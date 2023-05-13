package dev.intelligentcreations.hudium.impl.forge;

import dev.intelligentcreations.hudium.api.HudiumPlugin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class HudiumPluginRegistryEvent extends Event {
    private static final List<HudiumPlugin> PLUGINS = new ArrayList<>();

    public <T extends HudiumPlugin> void registerPlugin(Class<T> pluginClass) {
        try {
            PLUGINS.add(pluginClass.getDeclaredConstructor().newInstance());
        } catch (InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException("[Hudium] Error occurred during plugin registration!", e);
        }
    }

    public static List<HudiumPlugin> getPlugins() {
        return PLUGINS;
    }
}
