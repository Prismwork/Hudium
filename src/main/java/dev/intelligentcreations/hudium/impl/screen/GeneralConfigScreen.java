package dev.intelligentcreations.hudium.impl.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class GeneralConfigScreen extends AbstractConfigScreen {
    static final Text TITLE = Text.translatable("config.hudium.hudium-config.general");

    GeneralConfigScreen(Screen parent) {
        super(TITLE, parent);
    }

    @Override
    protected void init() {
        super.init();
    }
}
