package dev.intelligentcreations.hudium.api.info.plugin;

import dev.intelligentcreations.hudium.api.info.plugin.context.PluginContext;

public interface InfoPlugin<T extends PluginContext> {
    default boolean enabled() {
        return true;
    }

    void addInfo(T context);
}
