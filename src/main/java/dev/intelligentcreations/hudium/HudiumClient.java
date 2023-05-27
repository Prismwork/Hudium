package dev.intelligentcreations.hudium;

import dev.intelligentcreations.hudium.impl.hud.HudManager;
import dev.intelligentcreations.hudium.util.PlatformHelper;
import org.jetbrains.annotations.ApiStatus;

public final class HudiumClient {
    @ApiStatus.Internal
    static final HudRegistry REGISTRY = new HudRegistry();
    @ApiStatus.Internal
    public static HudManager HUD_MANAGER;

    public static void onInitializeClient() {
        Constants.LOGGER.info("Initializing plugins...");
        PlatformHelper.getAllPlugins()
                .forEach(plugin -> plugin.registerComponents(HudiumClient.REGISTRY));
        HudiumClient.REGISTRY.freeze();
        Constants.LOGGER.info("Registered " + HudiumClient.REGISTRY.size() + " component types.");
        HudiumClient.HUD_MANAGER = new HudManager();
        HudiumClient.HUD_MANAGER.saveConfig();
        Constants.LOGGER.info("Initialized.");
    }
}
