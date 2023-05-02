package dev.intelligentcreations.hudium;

import com.google.common.collect.Lists;
import dev.intelligentcreations.hudium.api.hud.Component;
import dev.intelligentcreations.hudium.api.hud.ComponentType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

public final class HudRegistry implements Iterable<ComponentType<?>> {
    private final Map<Identifier, ComponentType<?>> components;
    private boolean frozen;

    @ApiStatus.Internal
    HudRegistry() {
        components = new HashMap<>();
        frozen = false;
    }

    public <C extends Component> ComponentType<C> register(@NotNull Identifier id, @NotNull ComponentType<C> component) {
        if (frozen) throw new RuntimeException("HUD component registry already frozen");
        if (components.containsKey(id)) Constants.LOGGER.warn("ComponentType with identifier \""
                + id + "\" is already registered. Hudium will not attempt to register with the same identifier again.");
        components.putIfAbsent(id, component);
        return component;
    }

    @ApiStatus.Internal
    void freeze() {
        if (frozen) {
            Constants.LOGGER.error("Already frozen! Are there someone calling \"freeze()\"?");
            return;
        }
        frozen = true;
    }

    int size() {
        return components.size();
    }

    @NotNull
    @Override
    public Iterator<ComponentType<?>> iterator() {
        return components.values().iterator();
    }

    @NotNull
    @UnmodifiableView
    public static Collection<ComponentType<?>> getRegistryEntries() {
        return Collections.unmodifiableCollection(
                Lists.newArrayList(
                        HudiumClient.REGISTRY.iterator()
                )
        );
    }

    @Nullable
    public static ComponentType<?> getEntry(@NotNull Identifier id) {
        return HudiumClient.REGISTRY.components.get(id);
    }

    @Nullable
    public static Identifier getId(@NotNull ComponentType<?> componentType) {
        for (Map.Entry<Identifier, ComponentType<?>> entry
                : HudiumClient.REGISTRY.components.entrySet()) {
            if (entry.getValue().equals(componentType)) return entry.getKey();
        }
        return null;
    }
}
