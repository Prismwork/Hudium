package dev.intelligentcreations.hudium.impl;

import dev.intelligentcreations.hudium.HudRegistry;
import dev.intelligentcreations.hudium.api.HudiumPlugin;
import dev.intelligentcreations.hudium.api.hud.ComponentHolder;
import dev.intelligentcreations.hudium.impl.hud.player.PlayerStatsComponent;
import dev.intelligentcreations.hudium.util.Phys;
import net.minecraft.util.Identifier;

public class DefaultPlugin implements HudiumPlugin {
    @Override
    public void registerComponents(HudRegistry registry) {
        registry.register(new Identifier("hudium", "player_stats"), new ComponentHolder(new PlayerStatsComponent(), new Phys.Position(0, 0)));
    }
}
