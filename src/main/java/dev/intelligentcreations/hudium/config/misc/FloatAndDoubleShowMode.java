package dev.intelligentcreations.hudium.config.misc;

import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.NotNull;

public enum FloatAndDoubleShowMode implements StringIdentifiable {
    ACCURATE,
    SEMI_ACCURATE,
    INTEGER;

    FloatAndDoubleShowMode() {
    }

    @Override
    public String asString() {
        return String.valueOf(this);
    }

    public @NotNull FloatAndDoubleShowMode next() {
        FloatAndDoubleShowMode[] v = values();
        if (v.length == this.ordinal() + 1)
            return v[0];
        return v[this.ordinal() + 1];
    }
}
