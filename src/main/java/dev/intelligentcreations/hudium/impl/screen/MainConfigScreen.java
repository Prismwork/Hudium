package dev.intelligentcreations.hudium.impl.screen;

import dev.intelligentcreations.hudium.impl.screen.widget.HudiumButtonWidget;
import dev.intelligentcreations.hudium.impl.screen.widget.HudiumIconWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.ScreenTexts;
import net.minecraft.text.Text;

public final class MainConfigScreen extends AbstractConfigScreen {
    private static final Text TITLE = Text.translatable("config.hudium.hudium-config");

    public MainConfigScreen(Screen parent) {
        super(TITLE, parent);
    }

    @Override
    protected void init() {
        addDrawable(new HudiumIconWidget((width / 2) - 180, (height / 2) - 65));
        Text modName = Text.literal("Hudium");
        int modNameWidth = textRenderer.getWidth(modName);
        addDrawable(new TextWidget(
                ((width - modNameWidth) / 2) - 116,
                (height / 2) + 62,
                modNameWidth,
                textRenderer.fontHeight,
                modName,
                textRenderer)
        );
        addDrawableChild(new HudiumButtonWidget(
                (width / 2),
                (height / 2) - 35,
                180,
                20,
                GeneralConfigScreen.TITLE,
                btn -> client.setScreen(new GeneralConfigScreen(this))
        ));
        addDrawableChild(new HudiumButtonWidget(
                (width / 2),
                (height / 2) - 10,
                180,
                20,
                ComponentsConfigScreen.TITLE,
                btn -> client.setScreen(new ComponentsConfigScreen(this))
        ));
        addDrawableChild(new HudiumButtonWidget(
                (width / 2),
                (height / 2) + 15,
                180,
                20,
                ScreenTexts.DONE,
                btn -> client.setScreen(parent)
        ));
    }
}
