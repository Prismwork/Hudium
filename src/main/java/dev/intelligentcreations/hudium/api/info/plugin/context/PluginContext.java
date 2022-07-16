package dev.intelligentcreations.hudium.api.info.plugin.context;

import net.minecraft.text.Text;

public interface PluginContext {
    void renderText(Text text, int color);

    void renderText(String text, int color);
}
