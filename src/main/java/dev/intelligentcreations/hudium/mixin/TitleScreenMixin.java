package dev.intelligentcreations.hudium.mixin;

import dev.intelligentcreations.hudium.Constants;
import dev.intelligentcreations.hudium.impl.screen.MainConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin
        extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void hudium$onInit(CallbackInfo ci) {
        addDrawableChild(
                new TexturedButtonWidget(
                        this.width / 2 + 104, this.height / 4 + 48, // x and y
                        20, 20, // width and height
                        0, 0, 20, // u and v
                        Constants.CONFIG_BUTTON_TEXTURE, // texture
                        32, 64, // texture width and height
                        btn -> client.setScreen(new MainConfigScreen(this)), // press action
                        Text.translatable("config.hudium.hudium-config")
                )
        );
    }
}
