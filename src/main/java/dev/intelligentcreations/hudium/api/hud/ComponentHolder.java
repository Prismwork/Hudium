package dev.intelligentcreations.hudium.api.hud;

import dev.intelligentcreations.hudium.util.Phys;

public class ComponentHolder {
    private final Component component;
    private Phys.Position pos;

    public ComponentHolder(Component component, Phys.Position pos) {
        this.component = component;
        this.pos = pos;
    }

    public Component getComponent() {
        return component;
    }

    public Phys.Position getPos() {
        return pos;
    }

    public void setPos(Phys.Position pos) {
        this.pos = pos;
    }
}
