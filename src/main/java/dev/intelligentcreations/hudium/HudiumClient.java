package dev.intelligentcreations.hudium;

import dev.intelligentcreations.hudium.api.HudiumPlugin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HudiumClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Hudium");
	public static final String MOD_ID = "hudium";
	public static final HudRegistry REGISTRY = new HudRegistry();

	@Override
	public void onInitializeClient() {
		FabricLoader.getInstance().getEntrypoints("hudium:plugin", HudiumPlugin.class).forEach(plugin -> plugin.registerComponents(REGISTRY));
		REGISTRY.freeze();
		LOGGER.info("Version " + FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion() + " initialized with " + REGISTRY.size() + " active plugin(s).");
	}
}
