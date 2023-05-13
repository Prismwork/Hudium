package dev.intelligentcreations.hudium;

import dev.intelligentcreations.hudium.api.HudiumPlugin;
import dev.intelligentcreations.hudium.impl.hud.HudManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;

public final class HudiumClient implements ClientModInitializer {
	@ApiStatus.Internal
	public static HudManager HUD_MANAGER;
	@ApiStatus.Internal
	static final HudRegistry REGISTRY = new HudRegistry();

	@Override
	public void onInitializeClient() {
		Constants.LOGGER.info("Initializing plugins...");
		FabricLoader.getInstance().getEntrypoints("hudium:plugin", HudiumPlugin.class)
				.forEach(plugin -> plugin.registerComponents(REGISTRY));
		REGISTRY.freeze();
		Constants.LOGGER.info("Registered " + REGISTRY.size() + " component types.");
		HUD_MANAGER = new HudManager();
		HUD_MANAGER.saveConfig();
		Constants.LOGGER.info("Version " + FabricLoader.getInstance().getModContainer(Constants.MOD_ID).get().getMetadata().getVersion() + " initialized.");
	}
}
