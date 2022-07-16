package dev.intelligentcreations.hudium.api.info.plugin.context;

import dev.intelligentcreations.hudium.HudiumClient;
import dev.intelligentcreations.hudium.config.HudiumConfig;
import dev.intelligentcreations.hudium.util.TextRendererUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class EntityInfoPluginContext implements PluginContext {
    private final MatrixStack matrices;
    private final MinecraftClient client;
    private final PlayerEntity player;
    private final float tickDelta;
    private final TextRenderer textRenderer;
    private final Entity target;
    private int lines;

    private final HudiumConfig cfg = HudiumClient.CONFIG;

    protected EntityInfoPluginContext(MatrixStack matrices,
                                      MinecraftClient client,
                                      PlayerEntity player,
                                      float tickDelta,
                                      TextRenderer textRenderer,
                                      Entity target) {
        this.matrices = matrices;
        this.client = client;
        this.player = player;
        this.tickDelta = tickDelta;
        this.textRenderer = textRenderer;
        this.target = target;
        this.lines = 1;
    }

    public static EntityInfoPluginContext of(MatrixStack matrices,
                                             MinecraftClient client,
                                             PlayerEntity player,
                                             float tickDelta,
                                             TextRenderer textRenderer,
                                             Entity target) {
        return new EntityInfoPluginContext(matrices, client, player, tickDelta, textRenderer, target);
    }

    public MatrixStack getMatrices() {
        return matrices;
    }

    public MinecraftClient getClient() {
        return client;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public float getTickDelta() {
        return tickDelta;
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public Entity getTarget() {
        return target;
    }

    public void renderText(Text text, int color) {
        TextRendererUtil.renderText(textRenderer, matrices, text, cfg.displayInfoX, cfg.displayInfoY + 9 * lines, color);
        lines += 1;
    }

    public void renderText(String text, int color) {
        TextRendererUtil.renderText(textRenderer, matrices, text, cfg.displayInfoX, cfg.displayInfoY + 9 * lines, color);
        lines += 1;
    }

    public int getLines() {
        return lines;
    }
}
