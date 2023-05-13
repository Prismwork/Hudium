package dev.intelligentcreations.hudium;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public final class Constants {
    private Constants() {}

    public static final Logger LOGGER = LoggerFactory.getLogger("Hudium");
    public static final String MOD_ID = "hudium";
    public static Path HUD_CONFIG_DIR;
    public static final Identifier CONFIG_BUTTON_TEXTURE
            = new Identifier(MOD_ID, "textures/gui/config_button.png");
    public static final Identifier WIDGETS_TEXTURE = new Identifier(MOD_ID, "textures/gui/config/widgets.png");
}
