package dev.intelligentcreations.hudium.client.gui;

import dev.intelligentcreations.hudium.HudiumClient;
import dev.intelligentcreations.hudium.api.info.plugin.*;
import dev.intelligentcreations.hudium.mixin.ClientFrameRateAccessor;
import dev.intelligentcreations.hudium.util.RaycastUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class PlayerHud {
    //Misc Info
    public static void renderMiscValues(MatrixStack matrices, PlayerEntity player, TextRenderer textRenderer, int scaledWidth, int scaledHeight) {
        if (player != null) {
            //Heath
            int health = MathHelper.ceil(player.getHealth());
            int m = scaledWidth / 2 - 92;
            int n = scaledHeight - 39;
            int healthTextWidth = textRenderer.getWidth(String.valueOf(health));
            if (HudiumClient.CONFIG.displayHealthValue)
                textRenderer.drawWithShadow(matrices, String.valueOf(health), m - healthTextWidth, n, convertColor(HudiumClient.CONFIG.displayTextColor));
            //Hunger
            HungerManager hungerManager = player.getHungerManager();
            int hunger = hungerManager.getFoodLevel();
            int o = scaledWidth / 2 + 92;
            int hungerTextWidth = textRenderer.getWidth(String.valueOf(hunger));
            if (HudiumClient.CONFIG.displayHungerValue)
                textRenderer.drawWithShadow(matrices, String.valueOf(hunger), o, n, convertColor(HudiumClient.CONFIG.displayTextColor));
            //Saturation
            int saturation = MathHelper.ceil(hungerManager.getSaturationLevel());
            if (HudiumClient.CONFIG.displaySaturationValue)
                textRenderer.drawWithShadow(matrices, String.valueOf(saturation), o + hungerTextWidth + 4, n, convertColor(HudiumClient.CONFIG.displayTextColor));
            //Food
            ItemStack mainHandStack = player.getMainHandStack();
            if (mainHandStack.isFood()) {
                int stackFoodLevel = Math.min(Objects.requireNonNull(mainHandStack.getItem().getFoodComponent()).getHunger(), hungerManager.getPrevFoodLevel() - hungerManager.getFoodLevel());
                int stackSaturationLevel = MathHelper.ceil(Math.min((float) (mainHandStack.getItem().getFoodComponent()).getHunger() * Objects.requireNonNull(mainHandStack.getItem().getFoodComponent()).getSaturationModifier() * 2.0f, (float) hunger - (float) saturation));
                if (HudiumClient.CONFIG.displayHungerValue)
                    textRenderer.drawWithShadow(matrices, String.valueOf(stackFoodLevel), o, n + 10, 8453920);
                if (HudiumClient.CONFIG.displaySaturationValue)
                    textRenderer.drawWithShadow(matrices, String.valueOf(stackSaturationLevel), o + hungerTextWidth + 4, n + 10, 8453920);
            }
            //Armor
            int armor = player.getArmor();
            if (armor > 0) {
                int armorTextWidth = textRenderer.getWidth(String.valueOf(armor));
                if (HudiumClient.CONFIG.displayArmorValue)
                    textRenderer.drawWithShadow(matrices, String.valueOf(armor), m - armorTextWidth, n - 9, convertColor(HudiumClient.CONFIG.displayTextColor));
            }
            //Air
            int air = player.getAir();
            if (air < 300) {
                if (HudiumClient.CONFIG.displayAirValue)
                    textRenderer.drawWithShadow(matrices, String.valueOf(air), o, n - 9, convertColor(HudiumClient.CONFIG.displayTextColor));
            }
        }
    }

    //Block Info
    public static boolean renderBlockInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer) {
        boolean bl = false;
        HitResult result = RaycastUtil.getBlockHitResult(camera, tickDelta);
        int i = HudiumClient.CONFIG.displayInfoX;
        int j = HudiumClient.CONFIG.displayInfoY;
        if (result instanceof BlockHitResult bhr) {
            int m = 9;
            BlockState state = camera.getWorld().getBlockState(bhr.getBlockPos());
            ItemStack stack = state.getBlock().getPickStack(camera.getWorld(), bhr.getBlockPos(), state);
            if (!state.isOf(Blocks.AIR) && !state.isOf(Blocks.CAVE_AIR) && !state.isOf(Blocks.VOID_AIR)) {
                if (HudiumClient.CONFIG.displayBlockInfo) {
                    if (stack != null && !(state.getBlock() instanceof FluidBlock)) {
                        client.getItemRenderer().renderGuiItemIcon(stack, i, j);
                        i += 17;
                    }
                    textRenderer.drawWithShadow(matrices, I18n.translate(state.getBlock().getTranslationKey()), i, j, convertColor(HudiumClient.CONFIG.displayTextColor));
                    if (!InfoPluginHandler.getPlugins().isEmpty()) {
                        for (int q = 0; q < InfoPluginHandler.getPlugins().size(); q++) {
                            if (InfoPluginHandler.getPlugins().get(q) instanceof BlockInfoPlugin plugin) {
                                if (plugin.enabled()) {
                                    plugin.addInfo(matrices, client, camera, tickDelta, textRenderer, state, bhr.getBlockPos(), i, j + m);
                                    if (plugin.occupySpace()) m += 9 * plugin.occupySpaceLines();
                                }
                            }
                        }
                    }
                    textRenderer.drawWithShadow(matrices, Text.literal(FabricLoader.getInstance().getModContainer(Registry.BLOCK.getId(state.getBlock()).getNamespace()).get().getMetadata().getName()).formatted(Formatting.BLUE, Formatting.ITALIC), i, j + m, 5592575);
                }
                bl = true;
            }
        }
        return bl;
    }

    //Entity Info
    public static boolean renderEntityInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer) {
        boolean bl = false;
        int i = HudiumClient.CONFIG.displayInfoX;
        int j = HudiumClient.CONFIG.displayInfoY;
        HitResult result = RaycastUtil.getEntityHitResult(camera, tickDelta);
        if (result instanceof EntityHitResult ehr) {
            if (HudiumClient.CONFIG.displayEntityInfo) {
                Entity entity = ehr.getEntity();
                textRenderer.drawWithShadow(matrices, entity.getName(), i, j, convertColor(HudiumClient.CONFIG.displayTextColor));
                int m = 9;
                if (!InfoPluginHandler.getPlugins().isEmpty()) {
                    for (int q = 0; q < InfoPluginHandler.getPlugins().size(); q++) {
                        if (InfoPluginHandler.getPlugins().get(q) instanceof EntityInfoPlugin plugin) {
                            if (plugin.enabled()) {
                                plugin.addInfo(matrices, client, camera, tickDelta, textRenderer, entity, i, j + m);
                                if (plugin.occupySpace()) m += 9 * plugin.occupySpaceLines();
                            }
                        }
                    }
                }
                textRenderer.drawWithShadow(matrices, Text.literal(FabricLoader.getInstance().getModContainer(Registry.ENTITY_TYPE.getId(entity.getType()).getNamespace()).get().getMetadata().getName()).formatted(Formatting.BLUE, Formatting.ITALIC), i, j + m, 5592575);
            }
            bl = true;
        }
        return bl;
    }

    //Durability Info
    public static void renderDurabilityInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity player, TextRenderer textRenderer, int scaledHeight) {
        ItemStack headStack = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestStack = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legsStack = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feetStack = player.getEquippedStack(EquipmentSlot.FEET);
        ItemStack mainHandStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();
        int i = 4;
        if (HudiumClient.CONFIG.displayDurabilityInfo) {
            if (!headStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(headStack, i, scaledHeight - i - 64);
                if (headStack.isDamageable()) {
                    textRenderer.drawWithShadow(matrices, String.valueOf(headStack.getMaxDamage() - headStack.getDamage()), i + 17, scaledHeight - i - 60, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!chestStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(chestStack, i, scaledHeight - i - 48);
                if (chestStack.isDamageable()) {
                    textRenderer.drawWithShadow(matrices, String.valueOf(chestStack.getMaxDamage() - chestStack.getDamage()), i + 17, scaledHeight - i - 44, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!legsStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(legsStack, i, scaledHeight - i - 32);
                if (legsStack.isDamageable()) {
                    textRenderer.drawWithShadow(matrices, String.valueOf(legsStack.getMaxDamage() - legsStack.getDamage()), i + 17, scaledHeight - i - 28, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!feetStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(feetStack, i, scaledHeight - i - 16);
                if (feetStack.isDamageable()) {
                    textRenderer.drawWithShadow(matrices, String.valueOf(feetStack.getMaxDamage() - feetStack.getDamage()), i + 17, scaledHeight - i - 12, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!mainHandStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(mainHandStack, i, scaledHeight - i - 96);
                if (mainHandStack.isDamageable()) {
                    textRenderer.drawWithShadow(matrices, String.valueOf(mainHandStack.getMaxDamage() - mainHandStack.getDamage()), i + 17, scaledHeight - i - 92, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!offHandStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(offHandStack, i, scaledHeight - i - 80);
                if (offHandStack.isDamageable()) {
                    textRenderer.drawWithShadow(matrices, String.valueOf(offHandStack.getMaxDamage() - offHandStack.getDamage()), i + 17, scaledHeight - i - 76, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
        }
    }

    //Coordinates and Direction
    public static void renderCoordinatesAndDirection(MatrixStack matrices, PlayerEntity player, TextRenderer textRenderer, int scaledWidth) {
        Vec3d pos = player.getPos();
        Direction direction = player.getHorizontalFacing();
        if (HudiumClient.CONFIG.displayCoordinatesAndDirection) {
            double x = ((double) Math.round(pos.x * 10)) / 10;
            double y = ((double) Math.round(pos.y * 10)) / 10;
            double z = ((double) Math.round(pos.z * 10)) / 10;
            String posString = "( " + x + " , " + y + " , " + z + " )";
            int posStringWidth = textRenderer.getWidth(posString);
            String directionString;
            switch (direction) {
                default -> directionString = "info.hudium.direction.north";
                case NORTH -> directionString = "info.hudium.direction.north";
                case EAST -> directionString = "info.hudium.direction.east";
                case WEST -> directionString = "info.hudium.direction.west";
                case SOUTH -> directionString = "info.hudium.direction.south";
            }
            String directionStringTranslated = "-= " + I18n.translate(directionString) + " =-";
            int directionStringWidth = textRenderer.getWidth(directionStringTranslated);
            textRenderer.drawWithShadow(matrices, posString, ((float) scaledWidth / 2) - ((float) posStringWidth / 2), 4, convertColor(HudiumClient.CONFIG.displayTextColor));
            textRenderer.drawWithShadow(matrices, directionStringTranslated, ((float) scaledWidth / 2) - ((float) directionStringWidth / 2), 13, convertColor(HudiumClient.CONFIG.displayTextColor));
        }
    }

    //Extra Info
    public static void renderExtraInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity player, TextRenderer textRenderer, int scaledWidth, int scaledHeight) {
        List<String> extraInfo = new ArrayList<>();

        //FrameRate
        if (player != null) {
            int frameRate = ((ClientFrameRateAccessor) MinecraftClient.getInstance()).getClientFrameRate();
            if (HudiumClient.CONFIG.displayFrameRate) extraInfo.add(String.valueOf(frameRate) + " fps");
        }

        //Ping Info
        if (player != null) {
            PlayerListEntry playerInfo = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(MinecraftClient.getInstance().player.getUuid());
            if (playerInfo != null) {
                int networkLatency = playerInfo.getLatency();
                if (HudiumClient.CONFIG.displayNetworkLatency) extraInfo.add("Ping: " + String.valueOf(networkLatency) + " ms");
            }
        }

        //Biome Info and Game Time
        if (player != null && client.world != null) {
            //Biome
            RegistryEntry<Biome> biome = client.world.getBiome(player.getBlockPos());
            Identifier biomeIdentifier = client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome.value());
            if (biomeIdentifier != null) {
                String biomeName = PascalCase(Text.translatable("biome." + biomeIdentifier.getNamespace() + "." + biomeIdentifier.getPath()).getString());
                if (HudiumClient.CONFIG.displayBiomeInfo) extraInfo.add("Biome: " + biomeName);
            }
            //Time
            String gameTime = formatTime((int)client.world.getTimeOfDay());
            if (HudiumClient.CONFIG.displayGameTime) extraInfo.add(gameTime);
        }

        //Render Extra Info
        int x = 4;
        int y = 4;
        for (String info : extraInfo) {
            textRenderer.drawWithShadow(matrices, info, x, y, convertColor(HudiumClient.CONFIG.displayTextColor));
            y += 9;
        }
    }

    //Convert to PascalCase
    public static String PascalCase(String inputString) {
        // Capitalize first letter of a String
        if (inputString == null) return null;
        return inputString.substring(0, 1).toUpperCase() + inputString.substring(1);
    }

    //Format Time
    public static String formatTime(int time) {
        String period = "AM";

        int hours = (time / 1000 + 6) % 24;
        int minutes = (time % 1000) * 60 / 1000;

        if (hours >= 12) {
            hours -= 12;
            period = "PM";
        }
        if (hours == 0) hours = 12;
        String hh = Integer.toString(hours);

        String mm = "0" + minutes;
        mm = mm.substring(mm.length() - 2);

        return hh + ":" + mm + " " + period;
    }

    //Colour Converter
    public static int convertColor(String hexColor) {
        Color color;
        if (hexColor != null) {
            try {
                color = Color.decode("#" + hexColor);
            }
            catch (NumberFormatException e) {
                color = Color.WHITE;
            }
        }
        else {
            color = Color.WHITE;
        }

        int redValue = color.getRed();
        int greenValue = color.getGreen();
        int blueValue = color.getBlue();

        return (256 * 256 * redValue) + (256 * greenValue) + blueValue;
    }
}