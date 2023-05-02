package dev.intelligentcreations.hudium.impl.hud.player;

import dev.intelligentcreations.hudium.util.ColorUtil;
import dev.intelligentcreations.hudium.util.Phys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlayerStatsComponent {
    public void render(MatrixStack matrices, @NotNull PlayerEntity player, float tickDelta, Phys.Position position) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        //Health
        int health = MathHelper.ceil(player.getHealth());
        int scaledWidth = client.getWindow().getWidth();
        int scaledHeight = client.getWindow().getHeight();
        int m = scaledWidth / 2 - 92;
        int n = scaledHeight - 39;
        int healthTextWidth = textRenderer.getWidth(String.valueOf(health));
        textRenderer.draw(matrices, String.valueOf(health), m - healthTextWidth, n, ColorUtil.convertColor("FFFFFF"));
        //Hunger
        HungerManager hungerManager = player.getHungerManager();
        int hunger = hungerManager.getFoodLevel();
        int o = scaledWidth / 2 + 92;
        int hungerTextWidth = textRenderer.getWidth(String.valueOf(hunger));
        textRenderer.draw(matrices, String.valueOf(hunger), o, n, ColorUtil.convertColor("FFFFFF"));
        //Saturation
        int saturation = MathHelper.ceil(hungerManager.getSaturationLevel());
        textRenderer.draw(matrices, String.valueOf(saturation), o + hungerTextWidth + 4, n, ColorUtil.convertColor("FFFFFF"));
        //Food
        ItemStack mainHandStack = player.getMainHandStack();
        if (mainHandStack.isFood()) {
            int stackFoodLevel = Math.min(Objects.requireNonNull(mainHandStack.getItem().getFoodComponent()).getHunger(), hungerManager.getPrevFoodLevel() - hungerManager.getFoodLevel());
            int stackSaturationLevel = MathHelper.ceil(Math.min((float) (mainHandStack.getItem().getFoodComponent()).getHunger() * Objects.requireNonNull(mainHandStack.getItem().getFoodComponent()).getSaturationModifier() * 2.0f, (float) hunger - (float) saturation));

            textRenderer.draw(matrices, String.valueOf(stackFoodLevel), o, n + 10, 8453920);
            textRenderer.draw(matrices, String.valueOf(stackSaturationLevel), o + hungerTextWidth + 4, n + 10, 8453920);
        }
        //Armor
        int armor = player.getArmor();
        if (armor > 0) {
            int armorTextWidth = textRenderer.getWidth(String.valueOf(armor));
            textRenderer.draw(matrices, String.valueOf(armor), m - armorTextWidth, n - 9, ColorUtil.convertColor("FFFFFF"));
        }
        //Air
        int air = player.getAir();
        if (air < 300) {
            textRenderer.draw(matrices, String.valueOf(air), o, n - 9, ColorUtil.convertColor("FFFFFF"));
        }
    }

    public Phys.Bounds getBounds() {
        return new Phys.Bounds(120, 20);
    }
}
