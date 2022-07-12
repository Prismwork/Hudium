package dev.intelligentcreations.hudium.util;

import dev.intelligentcreations.hudium.HudiumClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextRendererUtil {
    public static void renderText(TextRenderer renderer, MatrixStack matrices, Text text, float x, float y, int color) {
        if (HudiumClient.CONFIG.renderShadowForText) {
            renderer.drawWithShadow(matrices, text, x, y, color);
        } else {
            renderer.draw(matrices, text, x, y, color);
        }
    }

    public static void renderText(TextRenderer renderer, MatrixStack matrices, String text, float x, float y, int color) {
        if (HudiumClient.CONFIG.renderShadowForText) {
            renderer.drawWithShadow(matrices, text, x, y, color);
        } else {
            renderer.draw(matrices, text, x, y, color);
        }
    }
}
