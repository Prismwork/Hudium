package dev.intelligentcreations.hudium.plugin.info;

import dev.intelligentcreations.hudium.api.info.plugin.EntityInfoPlugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityHealthPlugin implements EntityInfoPlugin {
    @Override
    public void addInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer, Entity entity, int renderX, int renderY) {
        if (entity instanceof LivingEntity livingEntity) {
            textRenderer.drawWithShadow(matrices, "\u2665 " + livingEntity.getHealth() + "/" + livingEntity.getMaxHealth(), renderX, renderY, 16733525);
        }
    }
}
