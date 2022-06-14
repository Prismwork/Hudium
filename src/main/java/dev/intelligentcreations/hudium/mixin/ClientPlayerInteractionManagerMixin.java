package dev.intelligentcreations.hudium.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerInteractionManager.class)
public interface ClientPlayerInteractionManagerMixin {
    @Accessor(value = "currentBreakingProgress")
    float getCurrentBreakingProgress();
}
