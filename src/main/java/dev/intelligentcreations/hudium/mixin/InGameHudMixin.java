package dev.intelligentcreations.hudium.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.intelligentcreations.hudium.client.gui.PlayerHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow public abstract TextRenderer getTextRenderer();
    @Shadow protected abstract PlayerEntity getCameraPlayer();
    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;
    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract BossBarHud getBossBarHud();

    @Inject(method = "render", at = @At("RETURN"))
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        PlayerEntity player = this.getCameraPlayer();
        TextRenderer textRenderer = this.getTextRenderer();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (!client.options.hudHidden) {
            if (!player.isCreative() && !player.isSpectator()) {
                PlayerHud.renderMiscValues(matrices, player, textRenderer, scaledWidth, scaledHeight);
            }
            if (!this.client.options.debugEnabled) {
                if (!PlayerHud.renderEntityInfo(matrices, client, player, tickDelta, textRenderer)) {
                    PlayerHud.renderBlockInfo(matrices, client, player, tickDelta, textRenderer);
                }
                PlayerHud.renderDurabilityInfo(matrices, client, player, textRenderer, scaledHeight);
                PlayerHud.renderCoordinatesAndDirection(matrices, player, textRenderer, scaledWidth, 4 + (19 * ((BossBarHudAccessor)this.getBossBarHud()).getBossBars().size()));
                PlayerHud.renderExtraInfo(matrices, client, player, textRenderer);
            }
        }
        RenderSystem.disableBlend();
    }
}
