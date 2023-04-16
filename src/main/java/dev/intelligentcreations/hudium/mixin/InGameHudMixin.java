package dev.intelligentcreations.hudium.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.intelligentcreations.hudium.HudiumClient;
import dev.intelligentcreations.hudium.api.hud.ComponentHolder;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Inject(method = "render", at = @At("RETURN"))
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        PlayerEntity player = this.getCameraPlayer();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (player != null) {
            for (ComponentHolder holder : HudiumClient.REGISTRY) {
                holder.getComponent().render(matrices, player, tickDelta, holder.getPos());
            }
        }
        RenderSystem.disableBlend();
    }
}
