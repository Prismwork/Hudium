package dev.intelligentcreations.hudium.impl.forge;

import dev.intelligentcreations.hudium.Constants;
import dev.intelligentcreations.hudium.HudiumClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = Constants.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class HudiumClientForgeImpl {
    @SubscribeEvent
    private void clientSetup(final FMLClientSetupEvent event) {
        HudiumClient.onInitializeClient();
    }
}
