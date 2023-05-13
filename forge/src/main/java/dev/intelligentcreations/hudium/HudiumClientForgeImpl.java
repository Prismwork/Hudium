package dev.intelligentcreations.hudium;

import dev.intelligentcreations.hudium.impl.forge.HudiumPluginRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("hudium")
public class HudiumClientForgeImpl {
    public HudiumClientForgeImpl() {
        IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        MOD_BUS.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        Constants.HUD_CONFIG_DIR = FMLPaths.CONFIGDIR.get().resolve("hudium");
        Constants.LOGGER.info("Initializing plugins...");
        MinecraftForge.EVENT_BUS.post(new HudiumPluginRegistryEvent());
        HudiumPluginRegistryEvent.getPlugins().forEach(plugin -> plugin.registerComponents(HudiumClient.REGISTRY));
        HudiumClient.REGISTRY.freeze();
        Constants.LOGGER.info("Registered " + HudiumClient.REGISTRY.size() + " component types.");
        HudiumClient.onInitializeClient();
    }
}
