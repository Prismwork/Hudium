package dev.intelligentcreations.hudium;

import dev.intelligentcreations.hudium.impl.hud.HudManager;
import org.jetbrains.annotations.ApiStatus;

public final class HudiumClient {
    @ApiStatus.Internal
    static final HudRegistry REGISTRY = new HudRegistry();
    @ApiStatus.Internal
    public static HudManager HUD_MANAGER;

    public static void onInitializeClient() {
        HudiumClient.HUD_MANAGER = new HudManager();
        HudiumClient.HUD_MANAGER.saveConfig();
        Constants.LOGGER.info("Initialized.");
    }
}
