package dev.intelligentcreations.hudium.impl.fabric.mixin;

import dev.intelligentcreations.hudium.Constants;
import dev.intelligentcreations.hudium.api.HudiumPlugin;
import dev.intelligentcreations.hudium.util.PlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.nio.file.Path;
import java.util.List;

@Mixin(value = PlatformHelper.class, remap = false)
public final class PlatformHelperFabricImpl {
    /**
     * @author Flamarine
     * @reason platform helper implementation
     */
    @Overwrite
    public static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    /**
     * @author Flamarine
     * @reason platform helper implementation
     */
    @Overwrite
    public static List<HudiumPlugin> getAllPlugins() {
        return FabricLoader.getInstance()
                .getEntrypoints(Constants.PLUGIN_ENTRYPOINT_KEY, HudiumPlugin.class);
    }
}
