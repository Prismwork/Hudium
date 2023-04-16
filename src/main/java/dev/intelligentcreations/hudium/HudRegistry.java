package dev.intelligentcreations.hudium;

import dev.intelligentcreations.hudium.api.hud.ComponentHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HudRegistry implements Iterable<ComponentHolder> {
    private final Map<Identifier, ComponentHolder> components;
    private boolean frozen;

    HudRegistry() {
        components = new HashMap<>();
        frozen = false;
    }

    public void register(Identifier id, ComponentHolder component) {
        if (frozen) throw new RuntimeException("HUD component registry already frozen");
        components.putIfAbsent(id, component);
    }

    public void freeze() {
        frozen = true;
    }

    public int size() {
        return components.size();
    }

    @NotNull
    @Override
    public Iterator<ComponentHolder> iterator() {
        return components.values().iterator();
    }
}
