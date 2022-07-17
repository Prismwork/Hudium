package dev.intelligentcreations.hudium.plugin.info;

import dev.intelligentcreations.hudium.api.info.plugin.BlockInfoPlugin;
import dev.intelligentcreations.hudium.api.info.plugin.context.BlockInfoPluginContext;
import dev.intelligentcreations.hudium.util.ToolHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.Optional;

// Again from cimtb, licensed under MIT.
// (God, maybe I should really include a full copy of MIT License in every file I take from cimtb...)
public class BlockMiningLevelPlugin implements BlockInfoPlugin {
    @Override
    public void addInfo(BlockInfoPluginContext context) {
        BlockState state = context.getState();
        PlayerEntity camera = context.getPlayer();
        Optional<Pair<ToolHandler, Integer>> optionalEffectiveTool = ToolHandler.TOOL_HANDLERS.stream()
                .map(handler -> (Pair<ToolHandler, Integer>) new MutablePair<>(handler, handler.supportsBlock(state, camera)))
                .min(Comparator.comparing(Pair::getRight, Comparator.nullsLast(Comparator.naturalOrder())));
        boolean hasToolSuitable = optionalEffectiveTool.isPresent();
        if (hasToolSuitable) {
            Pair<ToolHandler, Integer> entry = optionalEffectiveTool.get();
            if (!(entry.getRight() == null)) {
                ToolHandler handler = entry.getLeft();
                int level = entry.getRight();
                ItemStack stack = camera.getMainHandStack();
                boolean mineable = !state.isToolRequired() || (!stack.isEmpty() && ToolHandler.isEffective(stack, state));
                context.renderText(Text.translatable("info.hudium.mineable").formatted(Formatting.DARK_GRAY).append(Text.literal(mineable ? "\u2714" : "\u2718").formatted(mineable ? Formatting.GREEN : Formatting.RED)), 5592405);
                context.renderText(Text.translatable("info.hudium.suitableTool").formatted(Formatting.DARK_GRAY).append(handler.getToolDisplay()), 5592405);
                 boolean requiresLevel = level > 0;
                if (requiresLevel) {
                    String text = String.valueOf(level);
                    if (I18n.hasTranslation("info.hudium.miningLevel.level." + text)) {
                        String translate = I18n.translate("info.hudium.miningLevel.level." + text);
                        if (translate.contains(":")) {
                            translate = translate.substring(translate.indexOf(':') + 1);
                        }
                        text = I18n.translate("info.hudium.miningLevel.level", translate, level);
                    }
                    context.renderText(Text.translatable("info.hudium.miningLevel").formatted(Formatting.DARK_GRAY).append(Text.literal(text)), 5592405);
                }
            }
        }
    }
}
