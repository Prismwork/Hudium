package dev.intelligentcreations.hudium.config.gui;

import dev.intelligentcreations.hudium.HudiumClient;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class HudiumConfigScreen extends SpruceScreen {
    private final Screen parent;
    private SpruceOptionListWidget list;

    public HudiumConfigScreen(@Nullable Screen parent) {
        super(Text.translatable("config.hudium.hudium-config"));
        this.parent = parent;
    }

    private int getTextHeight() {
        return (5 + this.textRenderer.fontHeight) * 3 + 5;
    }

    @Override
    protected void init() {
        super.init();
        this.list = ConfigScreenBase.get().buildOptionList(Position.of(0, 22), this.width, this.height - 35 - 22);
        ConfigScreenBase.get().resetConsumer = btn -> this.init(this.client, this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight());
        this.addDrawableChild(this.list);
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2 - 155, this.height - 29), 150, 20, Text.translatable("config.hudium-config.save"),
                btn -> {
            HudiumClient.CONFIG.save();
            this.init(this.client, this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight());
        }));
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2 - 155 + 160, this.height - 29), 150, 20, SpruceTexts.GUI_DONE,
                btn -> this.client.setScreen(this.parent)).asVanilla());
    }

    @Override
    public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
    }
}
