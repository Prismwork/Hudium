package dev.intelligentcreations.hudium.api.hud;

import dev.intelligentcreations.hudium.util.Phys;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface Component {
    void render(MatrixStack matrices, @NotNull PlayerEntity camera, float tickDelta, Phys.Position position);

    Phys.Bounds getBounds();

    ComponentType<?> getType();
}
