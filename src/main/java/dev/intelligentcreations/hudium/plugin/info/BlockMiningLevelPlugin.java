package dev.intelligentcreations.hudium.plugin.info;

import dev.intelligentcreations.hudium.api.info.plugin.BlockInfoPlugin;
import dev.intelligentcreations.hudium.util.ToolHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.Optional;

// Again from cimtb, licensed under MIT.
// (God, maybe I should really include a full copy of MIT License in every file I take from cimtb...)
public class BlockMiningLevelPlugin implements BlockInfoPlugin {
    private boolean absolutelyNotMineable = false;
    private boolean hasToolSuitable = false;
    private boolean requiresLevel = false;

    @Override
    public void addInfo(MatrixStack matrices, MinecraftClient client, PlayerEntity camera, float tickDelta, TextRenderer textRenderer, BlockState state, BlockPos pos, int renderX, int renderY) {
        absolutelyNotMineable = state.isAir() || state.getHardness(camera.getWorld(), pos) == -1 || state.getBlock() instanceof FluidBlock || state.getBlock() == Blocks.REINFORCED_DEEPSLATE || state.getBlock() == Blocks.POWDER_SNOW;
        Optional<Pair<ToolHandler, Integer>> optionalEffectiveTool = ToolHandler.TOOL_HANDLERS.stream()
                .map(handler -> (Pair<ToolHandler, Integer>) new MutablePair<>(handler, handler.supportsBlock(state, camera)))
                .min(Comparator.comparing(Pair::getRight, Comparator.nullsLast(Comparator.naturalOrder())));
        hasToolSuitable = optionalEffectiveTool.isPresent();
        if (hasToolSuitable) {
            Pair<ToolHandler, Integer> entry = optionalEffectiveTool.get();
            if (!(entry.getRight() == null)) {
                ToolHandler handler = entry.getLeft();
                int level = entry.getRight();
                ItemStack stack = camera.getMainHandStack();
                boolean mineable = !state.isToolRequired() || (!stack.isEmpty() && ToolHandler.isEffective(stack, state));
                textRenderer.drawWithShadow(matrices, Text.translatable("info.hudium.mineable").formatted(Formatting.DARK_GRAY).append(Text.literal(mineable ? "\u2714" : "\u2718").formatted(mineable ? Formatting.GREEN : Formatting.RED)), renderX, renderY, 5592405);
                textRenderer.drawWithShadow(matrices, Text.translatable("info.hudium.suitableTool").formatted(Formatting.DARK_GRAY).append(handler.getToolDisplay()), renderX, renderY + 9, 5592405);
                requiresLevel = level > 0;
                if (requiresLevel) {
                    String text = String.valueOf(level);
                    if (I18n.hasTranslation("info.hudium.miningLevel.level." + text)) {
                        String translate = I18n.translate("info.hudium.miningLevel.level." + text);
                        if (translate.contains(":")) {
                            translate = translate.substring(translate.indexOf(':') + 1);
                        }
                        text = I18n.translate("info.hudium.miningLevel.level", translate, level);
                    }
                    textRenderer.drawWithShadow(matrices, Text.translatable("info.hudium.miningLevel").formatted(Formatting.DARK_GRAY).append(Text.literal(text)), renderX, renderY + 18, 5592405);
                }
            }
        }
    }

    @Override
    public boolean occupySpace() {
        return !absolutelyNotMineable;
    }

    @Override
    public int occupySpaceLines() {
        return hasToolSuitable ? (requiresLevel ? 3 : 2) : 0;
    }
}
