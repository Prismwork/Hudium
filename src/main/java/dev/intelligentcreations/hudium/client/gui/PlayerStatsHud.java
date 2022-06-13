package dev.intelligentcreations.hudium.client.gui;

import dev.intelligentcreations.hudium.HudiumClient;
import dev.intelligentcreations.hudium.api.info.plugin.BlockInfoPlugin;
import dev.intelligentcreations.hudium.api.info.plugin.EntityInfoPlugin;
import dev.intelligentcreations.hudium.api.info.plugin.InfoPluginHandler;
import dev.intelligentcreations.hudium.util.RaycastUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class PlayerStatsHud {
    public static void renderMiscValues(MatrixStack matrices, PlayerEntity player, TextRenderer textRenderer, int scaledWidth, int scaledHeight) {
        if (player != null) {
            int health = MathHelper.ceil(player.getHealth());
            int m = scaledWidth / 2 - 92;
            int n = scaledHeight - 39;
            int healthTextWidth = textRenderer.getWidth(String.valueOf(health));
            if (HudiumClient.CONFIG.displayHealthValue) textRenderer.drawWithShadow(matrices, String.valueOf(health), m - healthTextWidth, n, 16777215);
            HungerManager hungerManager = player.getHungerManager();
            int hunger = hungerManager.getFoodLevel();
            int o = scaledWidth / 2 + 92;
            int hungerTextWidth = textRenderer.getWidth(String.valueOf(hunger));
            if (HudiumClient.CONFIG.displayHungerValue) textRenderer.drawWithShadow(matrices, String.valueOf(hunger), o, n, 16777215);
            int saturation = MathHelper.ceil(hungerManager.getSaturationLevel());
            if (HudiumClient.CONFIG.displaySaturationValue) textRenderer.drawWithShadow(matrices, String.valueOf(saturation), o + hungerTextWidth + 4, n, 16777215);
            ItemStack mainHandStack = player.getMainHandStack();
            if (mainHandStack.isFood()) {
                int stackFoodLevel = Math.min(Objects.requireNonNull(mainHandStack.getItem().getFoodComponent()).getHunger(), hungerManager.getPrevFoodLevel() - hungerManager.getFoodLevel());
                int stackSaturationLevel = MathHelper.ceil(Math.min((float)(mainHandStack.getItem().getFoodComponent()).getHunger() * Objects.requireNonNull(mainHandStack.getItem().getFoodComponent()).getSaturationModifier() * 2.0f, (float)hunger - (float)saturation));
                if (HudiumClient.CONFIG.displayHungerValue) textRenderer.drawWithShadow(matrices, String.valueOf(stackFoodLevel), o, n + 10, 8453920);
                if (HudiumClient.CONFIG.displaySaturationValue) textRenderer.drawWithShadow(matrices, String.valueOf(stackSaturationLevel), o + hungerTextWidth + 4, n + 10, 8453920);
            }
            int armor = player.getArmor();
            if (armor > 0) {
                int armorTextWidth = textRenderer.getWidth(String.valueOf(armor));
                if (HudiumClient.CONFIG.displayArmorValue) textRenderer.drawWithShadow(matrices, String.valueOf(armor), m - armorTextWidth, n - 9, 16777215);
            }
            int air = player.getAir();
            if (air < 300) {
                if (HudiumClient.CONFIG.displayAirValue) textRenderer.drawWithShadow(matrices, String.valueOf(air), o, n - 9, 16777215);
            }
        }
    }

    public static boolean renderBlockInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer) {
        boolean bl = false;
        HitResult result = RaycastUtil.getBlockHitResult(camera, tickDelta);
        int i = 4;
        if (result instanceof BlockHitResult bhr) {
            int m = 9;
            BlockState state = camera.getWorld().getBlockState(bhr.getBlockPos());
            ItemStack stack = state.getBlock().getPickStack(camera.getWorld(), bhr.getBlockPos(), state);
            if (!state.isOf(Blocks.AIR) && !state.isOf(Blocks.CAVE_AIR) && !state.isOf(Blocks.VOID_AIR)) {
                if (HudiumClient.CONFIG.displayBlockInfo) {
                    if (stack != null) {
                        client.getItemRenderer().renderGuiItemIcon(stack, i, i);
                        textRenderer.drawWithShadow(matrices, I18n.translate(state.getBlock().getTranslationKey()), i + 24, i, 16777215);
                        loadBlockInfoPlugins(matrices, client, camera, tickDelta, textRenderer, m);
                        textRenderer.drawWithShadow(matrices, new LiteralText(FabricLoader.getInstance().getModContainer(Registry.BLOCK.getId(state.getBlock()).getNamespace()).get().getMetadata().getName()).formatted(Formatting.BLUE, Formatting.ITALIC), i + 24, i + m, 5592575);
                    } else {
                        textRenderer.drawWithShadow(matrices, I18n.translate(state.getBlock().getTranslationKey()), i, i, 16777215);
                        loadBlockInfoPlugins(matrices, client, camera, tickDelta, textRenderer, m);
                        textRenderer.drawWithShadow(matrices, new LiteralText(FabricLoader.getInstance().getModContainer(Registry.BLOCK.getId(state.getBlock()).getNamespace()).get().getMetadata().getName()).formatted(Formatting.BLUE, Formatting.ITALIC), i, i + m, 5592575);
                    }
                }
                bl = true;
            }
        }
        return bl;
    }

    public static boolean renderEntityInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer) {
        boolean bl = false;
        int i = 4;
        HitResult result = RaycastUtil.getEntityHitResult(camera, tickDelta);
        if (result instanceof EntityHitResult ehr) {
            if (HudiumClient.CONFIG.displayEntityInfo) {
                Entity entity = ehr.getEntity();
                textRenderer.drawWithShadow(matrices, entity.getName(), i, i, 16777215);
                int m = 9;
                if (entity instanceof LivingEntity livingEntity) {
                    textRenderer.drawWithShadow(matrices, "\u2665 " + livingEntity.getHealth() + "/" + livingEntity.getMaxHealth(), i, i + m, 16733525);
                    m = m + 9;
                }
                loadEntityInfoPlugins(matrices, client, camera, tickDelta, textRenderer, m);
                textRenderer.drawWithShadow(matrices, new LiteralText(FabricLoader.getInstance().getModContainer(Registry.ENTITY_TYPE.getId(entity.getType()).getNamespace()).get().getMetadata().getName()).formatted(Formatting.BLUE, Formatting.ITALIC), i, i + m, 5592575);
            }
            bl = true;
        }
        return bl;
    }

    private static void loadBlockInfoPlugins(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer, int m) {
        if (!InfoPluginHandler.getPlugins().isEmpty()) {
            for (int q = 0; q < InfoPluginHandler.getPlugins().size(); q++) {
                if (InfoPluginHandler.getPlugins().get(q) instanceof BlockInfoPlugin plugin) {
                    plugin.addInfo(matrices, client, camera, tickDelta, textRenderer);
                    m = m + 9;
                }
            }
        }
    }

    private static void loadEntityInfoPlugins(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer, int m) {
        if (!InfoPluginHandler.getPlugins().isEmpty()) {
            for (int q = 0; q < InfoPluginHandler.getPlugins().size(); q++) {
                if (InfoPluginHandler.getPlugins().get(q) instanceof EntityInfoPlugin plugin) {
                    plugin.addInfo(matrices, client, camera, tickDelta, textRenderer);
                    m = m + 9;
                }
            }
        }
    }
}
