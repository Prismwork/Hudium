package dev.intelligentcreations.hudium;

import dev.intelligentcreations.hudium.api.info.plugin.InfoPluginHandler;
import dev.intelligentcreations.hudium.config.HudiumConfig;
import dev.intelligentcreations.hudium.config.gui.ConfigScreenBase;
import dev.intelligentcreations.hudium.util.ToolHandler;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HudiumClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Hudium");
	public static final String MOD_ID = "hudium";
	public static final HudiumConfig CONFIG = OmegaConfig.register(HudiumConfig.class);
	public static final ConfigScreenBase configScreenBase = ConfigScreenBase.get();

	@Override
	public void onInitializeClient() {
		ToolHandler.initialize();
		InfoPluginHandler.loadPlugins();
		LOGGER.info("Version " + FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion() + " initialized with " + InfoPluginHandler.getPlugins().size() + " active info plugin(s).");
	}
}
