package dev.intelligentcreations.hudium.config;

import dev.intelligentcreations.hudium.HudiumClient;
import dev.intelligentcreations.hudium.config.misc.FloatAndDoubleShowMode;
import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HudiumConfig implements Config {
    @Comment(value = "Specify whether the health value will be displayed.")
    public boolean displayHealthValue = true;

    @Comment(value = "Specify whether the hunger value will be displayed.")
    public boolean displayHungerValue = true;

    @Comment(value = "Specify whether the saturation value will be displayed.")
    public boolean displaySaturationValue = true;

    @Comment(value = "Specify whether the armor value will be displayed.")
    public boolean displayArmorValue = true;

    @Comment(value = "Specify whether the air value (oxygen in the water) will be displayed.")
    public boolean displayAirValue = true;

    @Comment(value = "Specify the hex value of the info text colour.")
    public String displayTextColor = "FFFFFF";

    @Comment(value = "Specify the x coordinate the info is going to be placed.")
    public int displayInfoX = 4;

    @Comment(value = "Specify the y coordinate the info is going to be placed.")
    public int displayInfoY = 48;

    @Comment(value = "Specify whether the block info will be displayed.")
    public boolean displayBlockInfo = true;

    @Comment(value = "Specify whether the fluid info will be displayed.")
    public boolean displayFluidInfo = true;

    @Comment(value = "Specify whether the entity info will be displayed.")
    public boolean displayEntityInfo = true;

    @Comment(value = "Specify whether the durability info will be displayed.")
    public boolean displayDurabilityInfo = true;

    @Comment(value = "Specify whether the player coordinates and direction will be displayed.")
    public boolean displayCoordinatesAndDirection = true;

    @Comment(value = "Specify whether FPS will be displayed.")
    public boolean displayFrameRate = true;

    @Comment(value = "Specify whether ping will be displayed.")
    public boolean displayNetworkLatency = true;

    @Comment(value = "Specify whether the biome info will be displayed.")
    public boolean displayBiomeInfo = true;

    @Comment(value = "Specify whether the in-game time will be displayed.")
    public boolean displayGameTime = true;

    @Comment(value = "Specify whether the BGM info will be displayed.")
    public boolean displayBGMInfo = true;

    @Comment(value = "Specify whether specific tools are required holding in hands for some info.")
    public boolean holdToolsForInfo = false;

    @Comment(value = "Specify whether coordinates and direction will be rendered in an alternative way.")
    public boolean alternateBossBarFix = false;

    @Comment(value = "Specify whether the shadow of strings will be rendered.")
    public boolean renderShadowForText = true;

    @Comment(value = "Specify the show mode of float values and double values. Can only be ACCURATE, SEMI_ACCURATE or INTEGER. (For now only used in entity health)")
    public FloatAndDoubleShowMode floatAndDoubleShowMode = FloatAndDoubleShowMode.ACCURATE;

    @Override
    public String getName() {
        return "hudium-config";
    }

    @Override
    public @Nullable String getModid() {
        return HudiumClient.MOD_ID;
    }

    @Override
    public String getExtension() {
        return "json5";
    }

    public static void reset(@NotNull HudiumConfig cfg) {
        cfg.displayHealthValue = true;
        cfg.displayHungerValue = true;
        cfg.displaySaturationValue = true;
        cfg.displayArmorValue = true;
        cfg.displayAirValue = true;
        cfg.displayBlockInfo = true;
        cfg.displayFluidInfo = true;
        cfg.displayEntityInfo = true;
        cfg.displayDurabilityInfo = true;
        cfg.displayCoordinatesAndDirection = true;
        cfg.displayFrameRate = true;
        cfg.displayNetworkLatency = true;
        cfg.displayBiomeInfo = true;
        cfg.displayGameTime = true;
        cfg.displayBGMInfo = true;
        cfg.holdToolsForInfo = false;
        cfg.alternateBossBarFix = false;
        cfg.renderShadowForText = true;
        cfg.displayTextColor = "FFFFFF";
        cfg.displayInfoX = 4;
        cfg.displayInfoY = 48;
        cfg.floatAndDoubleShowMode = FloatAndDoubleShowMode.ACCURATE;
    }
}
