package dev.intelligentcreations.hudium.impl.forge.mixin;

import dev.intelligentcreations.hudium.api.HudiumPlugin;
import dev.intelligentcreations.hudium.impl.DefaultPlugin;
import dev.intelligentcreations.hudium.util.PlatformHelper;
import net.minecraftforge.fml.loading.FMLPaths;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.nio.file.Path;
import java.util.List;

@Mixin(value = PlatformHelper.class, remap = false)
public final class PlatformHelperForgeImpl {
    /**
     * @author Flamarine
     * @reason platform helper implementation
     */
    @Overwrite
    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    /**
     * @author Flamarine
     * @reason platform helper implementation
     */
    @Overwrite
    public static List<HudiumPlugin> getAllPlugins() {
        // TODO: implement plugin discovery on forge
        return List.of(new DefaultPlugin());
    }
}
