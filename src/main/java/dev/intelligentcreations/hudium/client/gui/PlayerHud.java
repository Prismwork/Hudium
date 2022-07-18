package dev.intelligentcreations.hudium.client.gui;

import dev.intelligentcreations.hudium.HudiumClient;
import dev.intelligentcreations.hudium.api.info.plugin.BlockInfoPlugin;
import dev.intelligentcreations.hudium.api.info.plugin.EntityInfoPlugin;
import dev.intelligentcreations.hudium.api.info.plugin.InfoPluginHandler;
import dev.intelligentcreations.hudium.api.info.plugin.context.BlockInfoPluginContext;
import dev.intelligentcreations.hudium.api.info.plugin.context.EntityInfoPluginContext;
import dev.intelligentcreations.hudium.mixin.ClientFrameRateAccessor;
import dev.intelligentcreations.hudium.mixin.ClientPlayerInteractionManagerAccessor;
import dev.intelligentcreations.hudium.mixin.MusicTrackerAccessor;
import dev.intelligentcreations.hudium.util.RaycastUtil;
import dev.intelligentcreations.hudium.util.TextRendererUtil;
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
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.GameMode;
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
            //Health
            int health = MathHelper.ceil(player.getHealth());
            int m = scaledWidth / 2 - 92;
            int n = scaledHeight - 39;
            int healthTextWidth = textRenderer.getWidth(String.valueOf(health));
            if (HudiumClient.CONFIG.displayHealthValue)
                TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(health), m - healthTextWidth, n, convertColor(HudiumClient.CONFIG.displayTextColor));
            //Hunger
            HungerManager hungerManager = player.getHungerManager();
            int hunger = hungerManager.getFoodLevel();
            int o = scaledWidth / 2 + 92;
            int hungerTextWidth = textRenderer.getWidth(String.valueOf(hunger));
            if (HudiumClient.CONFIG.displayHungerValue)
                TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(hunger), o, n, convertColor(HudiumClient.CONFIG.displayTextColor));
            //Saturation
            int saturation = MathHelper.ceil(hungerManager.getSaturationLevel());
            if (HudiumClient.CONFIG.displaySaturationValue)
                TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(saturation), o + hungerTextWidth + 4, n, convertColor(HudiumClient.CONFIG.displayTextColor));
            //Food
            ItemStack mainHandStack = player.getMainHandStack();
            if (mainHandStack.isFood()) {
                int stackFoodLevel = Math.min(Objects.requireNonNull(mainHandStack.getItem().getFoodComponent()).getHunger(), hungerManager.getPrevFoodLevel() - hungerManager.getFoodLevel());
                int stackSaturationLevel = MathHelper.ceil(Math.min((float) (mainHandStack.getItem().getFoodComponent()).getHunger() * Objects.requireNonNull(mainHandStack.getItem().getFoodComponent()).getSaturationModifier() * 2.0f, (float) hunger - (float) saturation));
                if (HudiumClient.CONFIG.displayHungerValue)
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(stackFoodLevel), o, n + 10, 8453920);
                if (HudiumClient.CONFIG.displaySaturationValue)
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(stackSaturationLevel), o + hungerTextWidth + 4, n + 10, 8453920);
            }
            //Armor
            int armor = player.getArmor();
            if (armor > 0) {
                int armorTextWidth = textRenderer.getWidth(String.valueOf(armor));
                if (HudiumClient.CONFIG.displayArmorValue)
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(armor), m - armorTextWidth, n - 9, convertColor(HudiumClient.CONFIG.displayTextColor));
            }
            //Air
            int air = player.getAir();
            if (air < 300) {
                if (HudiumClient.CONFIG.displayAirValue)
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(air), o, n - 9, convertColor(HudiumClient.CONFIG.displayTextColor));
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
                    TextRendererUtil.renderText(textRenderer, matrices, I18n.translate(state.getBlock().getTranslationKey()), i, j, convertColor(HudiumClient.CONFIG.displayTextColor));
                    if (!InfoPluginHandler.getPlugins().isEmpty()) {
                        for (int q = 0; q < InfoPluginHandler.getPlugins().size(); q++) {
                            if (InfoPluginHandler.getPlugins().get(q) instanceof BlockInfoPlugin plugin) {
                                if (plugin.enabled()) {
                                    BlockInfoPluginContext context = BlockInfoPluginContext.of(matrices, client, camera, tickDelta, textRenderer, state, bhr.getBlockPos(), m);
                                    plugin.addInfo(context);
                                    m += 9 * (context.getLines() - 1);
                                }
                            }
                        }
                    }
                    TextRendererUtil.renderText(textRenderer, matrices, Text.literal(FabricLoader.getInstance().getModContainer(Registry.BLOCK.getId(state.getBlock()).getNamespace()).get().getMetadata().getName()).formatted(Formatting.BLUE, Formatting.ITALIC), i, j + m, 5592575);
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
                TextRendererUtil.renderText(textRenderer, matrices, entity.getName(), i, j, convertColor(HudiumClient.CONFIG.displayTextColor));
                int m = 9;
                if (!InfoPluginHandler.getPlugins().isEmpty()) {
                    for (int q = 0; q < InfoPluginHandler.getPlugins().size(); q++) {
                        if (InfoPluginHandler.getPlugins().get(q) instanceof EntityInfoPlugin plugin) {
                            EntityInfoPluginContext context = EntityInfoPluginContext.of(matrices, client, camera, tickDelta, textRenderer, entity, m);
                            plugin.addInfo(context);
                            m += 9 * (context.getLines() - 1);
                        }
                    }
                }
                TextRendererUtil.renderText(textRenderer, matrices, Text.literal(FabricLoader.getInstance().getModContainer(Registry.ENTITY_TYPE.getId(entity.getType()).getNamespace()).get().getMetadata().getName()).formatted(Formatting.BLUE, Formatting.ITALIC), i, j + m, 5592575);
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
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(headStack.getMaxDamage() - headStack.getDamage()), i + 17, scaledHeight - i - 60, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!chestStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(chestStack, i, scaledHeight - i - 48);
                if (chestStack.isDamageable()) {
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(chestStack.getMaxDamage() - chestStack.getDamage()), i + 17, scaledHeight - i - 44, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!legsStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(legsStack, i, scaledHeight - i - 32);
                if (legsStack.isDamageable()) {
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(legsStack.getMaxDamage() - legsStack.getDamage()), i + 17, scaledHeight - i - 28, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!feetStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(feetStack, i, scaledHeight - i - 16);
                if (feetStack.isDamageable()) {
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(feetStack.getMaxDamage() - feetStack.getDamage()), i + 17, scaledHeight - i - 12, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!mainHandStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(mainHandStack, i, scaledHeight - i - 96);
                if (mainHandStack.isDamageable()) {
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(mainHandStack.getMaxDamage() - mainHandStack.getDamage()), i + 17, scaledHeight - i - 92, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
            if (!offHandStack.isEmpty()) {
                client.getItemRenderer().renderGuiItemIcon(offHandStack, i, scaledHeight - i - 80);
                if (offHandStack.isDamageable()) {
                    TextRendererUtil.renderText(textRenderer, matrices, String.valueOf(offHandStack.getMaxDamage() - offHandStack.getDamage()), i + 17, scaledHeight - i - 76, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
        }
    }

    //Coordinates and Direction
    public static void renderCoordinatesAndDirection(MatrixStack matrices, MinecraftClient client, PlayerEntity player, TextRenderer textRenderer, int scaledWidth, int scaledHeight, int renderHeight, boolean bossBarEnabled) {
        Vec3d pos = player.getPos();
        Direction direction = player.getHorizontalFacing();
        if (HudiumClient.CONFIG.displayCoordinatesAndDirection) {
            double x = ((double)Math.round(pos.x * 10)) / 10;
            double y = ((double)Math.round(pos.y * 10)) / 10;
            double z = ((double)Math.round(pos.z * 10)) / 10;
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
            int posY = renderHeight;
            int dirY = posY + 9;
            if (HudiumClient.CONFIG.alternateBossBarFix) {
                posY = 4;
                dirY = posY + 9;
                if (!bossBarEnabled) {
                    GameMode gameMode = ((ClientPlayerInteractionManagerAccessor) client.interactionManager).getCurrentGameMode();
                    int offsetY;
                    switch (gameMode) {
                        default -> offsetY = 50;
                        case SURVIVAL -> offsetY = 50;
                        case ADVENTURE -> offsetY = 50;
                        case CREATIVE -> offsetY = 33;
                        case SPECTATOR -> offsetY = 12;
                    }
                    posY = scaledHeight - offsetY;
                    dirY = posY - 9;
                }
            }
            String directionStringTranslated = "-= " + I18n.translate(directionString) + " =-";
            int directionStringWidth = textRenderer.getWidth(directionStringTranslated);
            if (!HudiumClient.CONFIG.holdToolsForInfo) {
                TextRendererUtil.renderText(textRenderer, matrices, posString, ((float) scaledWidth / 2) - ((float) posStringWidth / 2), posY, convertColor(HudiumClient.CONFIG.displayTextColor));
                TextRendererUtil.renderText(textRenderer, matrices, directionStringTranslated, ((float) scaledWidth / 2) - ((float) directionStringWidth / 2), dirY, convertColor(HudiumClient.CONFIG.displayTextColor));
            } else {
                if (player.getMainHandStack().getItem() instanceof CompassItem || player.getOffHandStack().getItem() instanceof CompassItem) {
                    TextRendererUtil.renderText(textRenderer, matrices, posString, ((float) scaledWidth / 2) - ((float) posStringWidth / 2), posY, convertColor(HudiumClient.CONFIG.displayTextColor));
                    TextRendererUtil.renderText(textRenderer, matrices, directionStringTranslated, ((float) scaledWidth / 2) - ((float) directionStringWidth / 2), dirY, convertColor(HudiumClient.CONFIG.displayTextColor));
                }
            }
        }
    }

    //Extra Info
    public static void renderExtraInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity player, TextRenderer textRenderer) {
        List<String> extraInfo = new ArrayList<>();

        //FrameRate
        if (player != null) {
            int frameRate = ((ClientFrameRateAccessor) MinecraftClient.getInstance()).getClientFrameRate();
            if (HudiumClient.CONFIG.displayFrameRate) extraInfo.add(frameRate + " fps");
        }

        //Ping Info
        if (player != null) {
            PlayerListEntry playerInfo = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(MinecraftClient.getInstance().player.getUuid());
            if (playerInfo != null) {
                int networkLatency = playerInfo.getLatency();
                if (HudiumClient.CONFIG.displayNetworkLatency && !client.isInSingleplayer() /* Do not render in Singleplayer */) extraInfo.add(I18n.translate("info.hudium.ping") + networkLatency + " ms");
            }
        }

        //Biome Info and Game Time
        if (player != null && client.world != null) {
            //Biome
            RegistryEntry<Biome> biome = client.world.getBiome(player.getBlockPos());
            Identifier biomeIdentifier = client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome.value());
            if (biomeIdentifier != null) {
                String biomeName = pascalCase(Text.translatable("biome." + biomeIdentifier.getNamespace() + "." + biomeIdentifier.getPath()).getString());
                if (HudiumClient.CONFIG.displayBiomeInfo) extraInfo.add(I18n.translate("info.hudium.biome") + biomeName);
            }
            //Time
            String gameTime = formatTime((int)client.world.getTimeOfDay());
            if (HudiumClient.CONFIG.displayGameTime) {
                if (!HudiumClient.CONFIG.holdToolsForInfo) extraInfo.add(gameTime);
                else if (player.getMainHandStack().getItem() == Items.CLOCK || player.getOffHandStack().getItem() == Items.CLOCK) extraInfo.add(gameTime);
            }
        }

        // Current playing BGM
        if (((MusicTrackerAccessor) client.getMusicTracker()).getCurrent() != null) {
            SoundInstance current = ((MusicTrackerAccessor) client.getMusicTracker()).getCurrent();
            if (HudiumClient.CONFIG.displayBGMInfo) extraInfo.add(I18n.translate("info.hudium.currentBGM", I18n.translate(current.getSound().getIdentifier().toTranslationKey().replace("/", "."))));
        }

        //Render Extra Info
        int x = 4;
        int y = 4;
        for (String info : extraInfo) {
            TextRendererUtil.renderText(textRenderer, matrices, info, x, y, convertColor(HudiumClient.CONFIG.displayTextColor));
            y += 9;
        }
    }

    //Convert to PascalCase
    public static String pascalCase(String inputString) {
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