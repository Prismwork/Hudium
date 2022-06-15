package dev.intelligentcreations.hudium.api.info.plugin;

public interface InfoPlugin {
    default boolean occupySpace() {
        return true;
    }

    default int occupySpaceLines() {
        return 1;
    }

    default boolean enabled() {
        return true;
    }
}
