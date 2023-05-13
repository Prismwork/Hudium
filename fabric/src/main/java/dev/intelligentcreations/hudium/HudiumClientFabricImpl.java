package dev.intelligentcreations.hudium;

import dev.intelligentcreations.hudium.api.HudiumPlugin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class HudiumClientFabricImpl implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Constants.HUD_CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("hudium");
		Constants.LOGGER.info("Initializing plugins...");
		FabricLoader.getInstance().getEntrypoints("hudium:plugin", HudiumPlugin.class)
				.forEach(plugin -> plugin.registerComponents(HudiumClient.REGISTRY));
		HudiumClient.REGISTRY.freeze();
		Constants.LOGGER.info("Registered " + HudiumClient.REGISTRY.size() + " component types.");
		HudiumClient.onInitializeClient();
	}
}
