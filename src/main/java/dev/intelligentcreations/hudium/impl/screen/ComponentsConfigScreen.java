package dev.intelligentcreations.hudium.impl.screen;

import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public final class ComponentsConfigScreen extends AbstractConfigScreen {
    static final Text TITLE = Text.translatable("config.hudium.hudium-config.components");
    private final RotatingCubeMapRenderer backgroundRenderer = new RotatingCubeMapRenderer(TitleScreen.PANORAMA_CUBE_MAP);

    ComponentsConfigScreen(Screen parent) {
        super(TITLE, parent);
    }

    @Override
    protected void renderConfigBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.backgroundRenderer.render(delta, MathHelper.clamp(1.0f, 0.0f, 1.0f));
    }
}
