package dev.intelligentcreations.hudium.mixin;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.SimpleRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SimpleRegistry.class)
public interface SimpleRegistryAccessor<T> {
    @Nullable
    @Accessor("unfrozenValueToEntry")
    Map<T, RegistryEntry.Reference<T>> getUnfrozenValueToEntry();
}
