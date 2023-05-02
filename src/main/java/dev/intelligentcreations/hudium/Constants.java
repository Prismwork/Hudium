package dev.intelligentcreations.hudium;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Constants {
    public static final Logger LOGGER = LoggerFactory.getLogger("Hudium");
    public static final String MOD_ID = "hudium";
    public static final Path HUD_CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("hudium");
}
