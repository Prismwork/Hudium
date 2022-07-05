package dev.intelligentcreations.hudium.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerInteractionManager.class)
public interface ClientPlayerInteractionManagerAccessor {
    @Accessor(value = "currentBreakingProgress")
    float getCurrentBreakingProgress();

    @Accessor(value = "gameMode")
    GameMode getCurrentGameMode();
}
