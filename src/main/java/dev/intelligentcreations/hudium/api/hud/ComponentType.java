package dev.intelligentcreations.hudium.api.hud;

import java.util.function.Supplier;

public final class ComponentType<C extends Component> {
    private final Supplier<C> getter;

    private ComponentType(Supplier<C> getter) {
        this.getter = getter;
    }

    public C create() {
        return getter.get();
    }

    public static final class Builder<T extends Component> {
        private final Supplier<T> supplier;

        public Builder(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        public ComponentType<T> build() {
            return new ComponentType<>(supplier);
        }
    }
}
