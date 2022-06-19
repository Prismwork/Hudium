package dev.intelligentcreations.hudium.config;

import dev.intelligentcreations.hudium.HudiumClient;
import dev.intelligentcreations.hudium.config.misc.FloatAndDoubleShowMode;
import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
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

    @Comment(value = "Specify the x coordinate the info is going to be placed")
    public int displayInfoX = 4;

    @Comment(value = "Specify the y coordinate the info is going to be placed")
    public int displayInfoY = 4;

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
}
