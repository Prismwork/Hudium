package dev.intelligentcreations.hudium.plugin.info;

import dev.intelligentcreations.hudium.api.info.plugin.BlockInfoPlugin;
import dev.intelligentcreations.hudium.api.info.plugin.context.BlockInfoPluginContext;
import dev.intelligentcreations.hudium.mixin.ClientPlayerInteractionManagerAccessor;
import net.minecraft.client.resource.language.I18n;

public class BlockBreakProgressPlugin implements BlockInfoPlugin {
    @Override
    public void addInfo(BlockInfoPluginContext context) {
        int progress = (int) (((ClientPlayerInteractionManagerAccessor) context.getClient().interactionManager).getCurrentBreakingProgress() * 100);
        if (progress > 0) {
            context.renderText(I18n.translate("info.hudium.breakProgress") + progress + "%", 5592405);
        }
    }
}
