package dev.intelligentcreations.hudium.impl.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class AbstractConfigScreen extends Screen {
    protected final Screen parent;

    protected AbstractConfigScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderConfigBackground(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @SuppressWarnings("unused")
    protected void renderConfigBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        fill(matrices, 0, 0, width, height, 0xff303030);
    }
}
