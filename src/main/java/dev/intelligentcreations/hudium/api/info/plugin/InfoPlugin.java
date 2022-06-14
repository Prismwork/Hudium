package dev.intelligentcreations.hudium.api.info.plugin;

public interface InfoPlugin {
    default boolean occupySpace() {
        return true;
    }
}
