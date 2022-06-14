package dev.intelligentcreations.hudium.plugin.info;

import dev.intelligentcreations.hudium.api.info.plugin.BlockInfoPlugin;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import team.reborn.energy.api.EnergyStorage;

public class BlockEnergyInfo implements BlockInfoPlugin {
    private boolean isEnergyStorage = false;

    @Override
    public void addInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer, BlockState state, BlockPos pos, int renderX, int renderY) {
        EnergyStorage maybeStorage = EnergyStorage.SIDED.find(camera.getWorld(), pos, Direction.getEntityFacingOrder(camera)[0].getOpposite());
        if (maybeStorage != null) {
            isEnergyStorage = true;
            textRenderer.drawWithShadow(matrices, I18n.translate("info.hudium.storedEnergy") + maybeStorage.getAmount() + "E / " + maybeStorage.getCapacity() + "E", renderX, renderY, 5592405);
        } else {
            isEnergyStorage = false;
        }
    }

    @Override
    public boolean occupySpace() {
        return isEnergyStorage;
    }
}
