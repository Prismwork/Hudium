package dev.intelligentcreations.hudium.impl.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.intelligentcreations.hudium.Constants;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class HudiumIconWidget extends ClickableWidget {
    public static final Identifier ICON = new Identifier(Constants.MOD_ID, "textures/gui/icon.png");

    public HudiumIconWidget(int x, int y) {
        super(x, y, 128, 128, Text.empty());
    }

    @Override
    public void drawWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShaderTexture(0, ICON);
        int width = this.getWidth();
        int height = this.getHeight();
        drawTexture(matrices, getX(), getY(), 0, 0, width, height, width, height);
    }

    @Override
    protected void updateNarration(NarrationMessageBuilder builder) {}
}
