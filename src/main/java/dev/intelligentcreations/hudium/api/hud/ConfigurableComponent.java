package dev.intelligentcreations.hudium.api.hud;

import me.shedaniel.autoconfig.ConfigData;

public interface ConfigurableComponent<C extends ConfigData>
        extends Component {
    C getConfig();
}
