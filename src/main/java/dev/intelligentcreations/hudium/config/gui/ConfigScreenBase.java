package dev.intelligentcreations.hudium.config.gui;

import dev.intelligentcreations.hudium.HudiumClient;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.option.SpruceIntegerInputOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.option.SpruceSimpleActionOption;
import dev.lambdaurora.spruceui.option.SpruceToggleBooleanOption;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class ConfigScreenBase {
    private final SpruceOption displayHealthValue;
    private final SpruceOption displayHungerValue;
    private final SpruceOption displaySaturationValue;
    private final SpruceOption displayArmorValue;
    private final SpruceOption displayAirValue;
    private final SpruceOption displayBlockInfo;
    private final SpruceOption displayFluidInfo;
    private final SpruceOption displayEntityInfo;
    private final SpruceOption displayDurabilityInfo;
    private final SpruceOption displayCoordinatesAndDirection;
    private final SpruceOption displayInfoX;
    private final SpruceOption displayInfoY;
    private final SpruceOption reset;
    private static final ConfigScreenBase INSTANCE = new ConfigScreenBase();

    public Consumer<SpruceButtonWidget> resetConsumer;

    public ConfigScreenBase() {
        this.displayHealthValue = new SpruceToggleBooleanOption("configEntry.hudium-config.displayHealthValue",
                () -> HudiumClient.CONFIG.displayHealthValue,
                newValue -> HudiumClient.CONFIG.displayHealthValue = newValue,
                Text.translatable("entryInfo.hudium-config.displayHealthValue"));
        this.displayHungerValue = new SpruceToggleBooleanOption("configEntry.hudium-config.displayHungerValue",
                () -> HudiumClient.CONFIG.displayHungerValue,
                newValue -> HudiumClient.CONFIG.displayHungerValue = newValue,
                Text.translatable("entryInfo.hudium-config.displayHungerValue"));
        this.displaySaturationValue = new SpruceToggleBooleanOption("configEntry.hudium-config.displaySaturationValue",
                () -> HudiumClient.CONFIG.displaySaturationValue,
                newValue -> HudiumClient.CONFIG.displaySaturationValue = newValue,
                Text.translatable("entryInfo.hudium-config.displaySaturationValue"));
        this.displayArmorValue = new SpruceToggleBooleanOption("configEntry.hudium-config.displayArmorValue",
                () -> HudiumClient.CONFIG.displayArmorValue,
                newValue -> HudiumClient.CONFIG.displayArmorValue = newValue,
                Text.translatable("entryInfo.hudium-config.displayArmorValue"));
        this.displayAirValue = new SpruceToggleBooleanOption("configEntry.hudium-config.displayAirValue",
                () -> HudiumClient.CONFIG.displayAirValue,
                newValue -> HudiumClient.CONFIG.displayAirValue = newValue,
                Text.translatable("entryInfo.hudium-config.displayAirValue"));
        this.displayBlockInfo = new SpruceToggleBooleanOption("configEntry.hudium-config.displayBlockInfo",
                () -> HudiumClient.CONFIG.displayBlockInfo,
                newValue -> HudiumClient.CONFIG.displayBlockInfo = newValue,
                Text.translatable("entryInfo.hudium-config.displayBlockInfo"));
        this.displayFluidInfo = new SpruceToggleBooleanOption("configEntry.hudium-config.displayFluidInfo",
                () -> HudiumClient.CONFIG.displayFluidInfo,
                newValue -> HudiumClient.CONFIG.displayFluidInfo = newValue,
                Text.translatable("entryInfo.hudium-config.displayFluidInfo"));
        this.displayEntityInfo = new SpruceToggleBooleanOption("configEntry.hudium-config.displayEntityInfo",
                () -> HudiumClient.CONFIG.displayEntityInfo,
                newValue -> HudiumClient.CONFIG.displayEntityInfo = newValue,
                Text.translatable("entryInfo.hudium-config.displayEntityInfo"));
        this.displayDurabilityInfo = new SpruceToggleBooleanOption("configEntry.hudium-config.displayDurabilityInfo",
                () -> HudiumClient.CONFIG.displayDurabilityInfo,
                newValue -> HudiumClient.CONFIG.displayDurabilityInfo = newValue,
                Text.translatable("entryInfo.hudium-config.displayDurabilityInfo"));
        this.displayCoordinatesAndDirection = new SpruceToggleBooleanOption("configEntry.hudium-config.displayCoordinatesAndDirection",
                () -> HudiumClient.CONFIG.displayCoordinatesAndDirection,
                newValue -> HudiumClient.CONFIG.displayCoordinatesAndDirection = newValue,
                Text.translatable("entryInfo.hudium-config.displayCoordinatesAndDirection"));
        this.displayInfoX = new SpruceIntegerInputOption("configEntry.hudium-config.displayInfoX",
                () -> HudiumClient.CONFIG.displayInfoX,
                newValue -> HudiumClient.CONFIG.displayInfoX = newValue,
                Text.translatable("entryInfo.hudium-config.displayInfoX"));
        this.displayInfoY = new SpruceIntegerInputOption("configEntry.hudium-config.displayInfoY",
                () -> HudiumClient.CONFIG.displayInfoY,
                newValue -> HudiumClient.CONFIG.displayInfoY = newValue,
                Text.translatable("entryInfo.hudium-config.displayInfoY"));
        this.reset = SpruceSimpleActionOption.reset(btn -> {
            HudiumClient.CONFIG.displayHealthValue = true;
            HudiumClient.CONFIG.displayHungerValue = true;
            HudiumClient.CONFIG.displaySaturationValue = true;
            HudiumClient.CONFIG.displayArmorValue = true;
            HudiumClient.CONFIG.displayAirValue = true;
            HudiumClient.CONFIG.displayBlockInfo = true;
            HudiumClient.CONFIG.displayFluidInfo = true;
            HudiumClient.CONFIG.displayEntityInfo = true;
            HudiumClient.CONFIG.displayDurabilityInfo = true;
            HudiumClient.CONFIG.displayCoordinatesAndDirection = true;
            HudiumClient.CONFIG.displayInfoX = 4;
            HudiumClient.CONFIG.displayInfoY = 4;
            if (this.resetConsumer != null) this.resetConsumer.accept(btn);
        });
    }

    public static ConfigScreenBase get() {
        return INSTANCE;
    }

    public SpruceOptionListWidget buildOptionList(Position position, int width, int height) {
        SpruceOptionListWidget list = new SpruceOptionListWidget(position, width, height);
        list.addSingleOptionEntry(displayHealthValue);
        list.addSingleOptionEntry(displayHungerValue);
        list.addSingleOptionEntry(displaySaturationValue);
        list.addSingleOptionEntry(displayArmorValue);
        list.addSingleOptionEntry(displayAirValue);
        list.addSingleOptionEntry(displayBlockInfo);
        list.addSingleOptionEntry(displayFluidInfo);
        list.addSingleOptionEntry(displayEntityInfo);
        list.addSingleOptionEntry(displayDurabilityInfo);
        list.addSingleOptionEntry(displayCoordinatesAndDirection);
        list.addOptionEntry(displayInfoX, displayInfoY);
        list.addSingleOptionEntry(reset);
        return list;
    }
}
