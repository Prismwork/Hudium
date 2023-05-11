package dev.intelligentcreations.hudium.api.hud;

import dev.intelligentcreations.hudium.api.hud.config.ComponentConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public final class ComponentType<C extends Component> {
    private final @NotNull Function<@Nullable ComponentConfig, @NotNull C> getter;
    private final @NotNull Supplier<@Nullable ComponentConfig> configGetter;

    private ComponentType(@NotNull Function<@Nullable ComponentConfig, @NotNull C> getter,
                          @NotNull Supplier<@Nullable ComponentConfig> configGetter) {
        this.getter = getter;
        this.configGetter = configGetter;
    }

    public C create() {
        return getter.apply(initConfig());
    }

    public ComponentConfig initConfig() {
        return configGetter.get();
    }

    public static final class Builder<T extends Component> {
        private final @NotNull Function<@Nullable ComponentConfig, @NotNull T> supplier;
        private @NotNull Supplier<@Nullable ComponentConfig> configSupplier;

        public Builder(@NotNull Function<@Nullable ComponentConfig, @NotNull T> supplier) {
            this.supplier = supplier;
            this.configSupplier = () -> null;
        }

        public Builder<T> withConfig(@NotNull Supplier<@NotNull ComponentConfig> supplier) {
            this.configSupplier = supplier;
            return this;
        }

        public ComponentType<T> build() {
            return new ComponentType<>(supplier, configSupplier);
        }
    }
}
