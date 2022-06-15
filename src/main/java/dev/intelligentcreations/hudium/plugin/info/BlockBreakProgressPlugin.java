package dev.intelligentcreations.hudium.plugin.info;

import dev.intelligentcreations.hudium.api.info.plugin.BlockInfoPlugin;
import dev.intelligentcreations.hudium.mixin.ClientPlayerInteractionManagerAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class BlockBreakProgressPlugin implements BlockInfoPlugin {
    private boolean occupySpace = false;

    @Override
    public void addInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer, BlockState state, BlockPos pos, int renderX, int renderY) {
        int progress = (int) (((ClientPlayerInteractionManagerAccessor) client.interactionManager).getCurrentBreakingProgress() * 100);
        occupySpace = progress > 0;
        if (occupySpace) {
            textRenderer.drawWithShadow(matrices, I18n.translate("info.hudium.breakProgress") + progress + "%", renderX, renderY, 5592405);
        }
    }

    @Override
    public boolean occupySpace() {
        return occupySpace;
    }
}
