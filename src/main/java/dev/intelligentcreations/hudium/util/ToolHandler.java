package dev.intelligentcreations.hudium.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import dev.intelligentcreations.hudium.mixin.SimpleRegistryAccessor;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

// From cimtb, licensed under MIT
// Grabbed because I have no clue about how to implement mining level detection
public final class ToolHandler {
    public static final List<ToolHandler> TOOL_HANDLERS = Lists.newArrayList();

    public final TagKey<Block> tag;
    public final Item defaultTool;
    private final Supplier<Integer> maximumLevel = Suppliers.memoize(() -> {
        int highest = 4;
        for (Item item : Registry.ITEM) {
            if (item instanceof ToolItem toolItem) {
                int miningLevel = toolItem.getMaterial().getMiningLevel();
                if (miningLevel > highest) {
                    highest = miningLevel;
                }
            }
        }
        return highest;
    });

    public ToolHandler(Map.Entry<TagKey<Block>, Item> entry) {
        this.tag = entry.getKey();
        this.defaultTool = entry.getValue();
    }

    public Integer supportsBlock(BlockState state, PlayerEntity user) {
        ItemStack itemStack = new ItemStack(defaultTool);
        if (defaultToolSupportsMutableLevel()) {
            for (int level = 0; level <= maximumLevel.get(); level++) {
                setDefaultToolSupportsMutableLevel(level);
                boolean effective = isEffective(itemStack, state);
                if (effective) {
                    return level;
                }
            }
        }
        boolean effective = isEffective(itemStack, state);
        if (effective) {
            return getToolMiningLevel(itemStack, state, user);
        }
        return null;
    }

    private boolean defaultToolSupportsMutableLevel() {
        return defaultTool instanceof ToolItem && ((ToolItem) defaultTool).getMaterial() instanceof MutableToolMaterial;
    }

    private void setDefaultToolSupportsMutableLevel(int level) {
        ((MutableToolMaterial) ((ToolItem) defaultTool).getMaterial()).miningLevel = level;
    }

    private int getToolMiningLevel(ItemStack stack, BlockState state, LivingEntity user) {
        Item item = stack.getItem();
        if (item instanceof ToolItem)
            return ((ToolItem) item).getMaterial().getMiningLevel();
        return 0;
    }

    public Text getToolDisplay() {
        return Text.translatable("info.hudium.suitableTool." + tag.id().getNamespace() + "." + tag.id().getPath().replace('/', '.')).formatted(Formatting.GRAY);
    }

    public static boolean isEffective(ItemStack stack, BlockState state) {
        return stack.isSuitableFor(state) || (!state.isToolRequired() && stack.getMiningSpeedMultiplier(state) > 1.0F);
    }

    private static void registerTool(TagKey<Block> tag, Item item) {
        ToolHandler.TOOL_HANDLERS.add(new ToolHandler(new AbstractMap.SimpleImmutableEntry<>(tag, item)));
        if (Registry.ITEM.getKey(item).isEmpty()) {
            ((SimpleRegistryAccessor<Item>) Registry.ITEM).getUnfrozenValueToEntry().remove(item);
        }
    }

    public static void initialize() {
        registerTool(FabricMineableTags.SHEARS_MINEABLE, Items.SHEARS);
        registerTool(FabricMineableTags.SWORD_MINEABLE, new SwordItem(new MutableToolMaterial(0), 0, 0, new Item.Settings()) {});
        registerTool(BlockTags.PICKAXE_MINEABLE, new PickaxeItem(new MutableToolMaterial(0), 0, 0, new Item.Settings()) {});
        registerTool(BlockTags.AXE_MINEABLE, new AxeItem(new MutableToolMaterial(0), 0, 0, new Item.Settings()) {});
        registerTool(BlockTags.SHOVEL_MINEABLE, new ShovelItem(new MutableToolMaterial(0), 0, 0, new Item.Settings()) {});
        registerTool(BlockTags.HOE_MINEABLE, new HoeItem(new MutableToolMaterial(0), 0, 0, new Item.Settings()) {});
    }
}
