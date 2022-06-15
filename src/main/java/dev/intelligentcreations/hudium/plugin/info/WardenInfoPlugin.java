package dev.intelligentcreations.hudium.plugin.info;

import dev.intelligentcreations.hudium.api.info.plugin.EntityInfoPlugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;

public class WardenInfoPlugin implements EntityInfoPlugin {
    private boolean isWarden;

    @Override
    public void addInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer, Entity target, int renderX, int renderY) {
        isWarden = (target instanceof WardenEntity);
        if (target instanceof WardenEntity warden) {
            boolean isAgitated = warden.getAnger() > 40;
            boolean isAngry = warden.getAnger() > 80;
            String wardenAnger = isAngry ? "info.hudium.wardenAngerLevel.angry" : isAgitated ? "info.hudium.wardenAngerLevel.agitated" : "info.hudium.wardenAngerLevel.calm";
            textRenderer.drawWithShadow(matrices, I18n.translate("info.hudium.wardenAnger") + I18n.translate(wardenAnger), renderX, renderY, 5592405);
        }
    }

    @Override
    public boolean occupySpace() {
        return isWarden;
    }
}
