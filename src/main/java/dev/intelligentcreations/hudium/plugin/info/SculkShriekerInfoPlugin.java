package dev.intelligentcreations.hudium.plugin.info;

import dev.intelligentcreations.hudium.api.info.plugin.BlockInfoPlugin;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.block.entity.SculkShriekerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SculkShriekerInfoPlugin implements BlockInfoPlugin {
    private boolean isSculkShrieker;
    private boolean hasRiskSpawnWarden;

    @Override
    public void addInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer, BlockState state, BlockPos pos, int renderX, int renderY) {
        isSculkShrieker = state.getBlock() instanceof SculkShriekerBlock;
        if (isSculkShrieker) {
            int warningLevel = camera.getSculkShriekerWarningManager().getWarningLevel();
            hasRiskSpawnWarden = warningLevel >= 4;
            textRenderer.drawWithShadow(matrices, I18n.translate("info.hudium.shriekerWarningLevel") + warningLevel, renderX, renderY, 5592405);
            if (hasRiskSpawnWarden) {
                textRenderer.drawWithShadow(matrices, Text.translatable("info.hudium.shriekerHasRiskSpawnWarden"), renderX, renderY + 9, 5592405);
            }
        }
    }

    @Override
    public boolean occupySpace() {
        return isSculkShrieker;
    }

    @Override
    public int occupySpaceLines() {
        return hasRiskSpawnWarden ? 2 : 1;
    }
}
