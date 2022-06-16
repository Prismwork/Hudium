package dev.intelligentcreations.hudium.mixin;

import dev.intelligentcreations.hudium.HudiumClient;
import dev.intelligentcreations.hudium.config.gui.HudiumConfigScreen;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.SpruceTexturedButtonWidget;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At("RETURN"))
    public void onInitWidgets(CallbackInfo ci) {
        this.addDrawableChild(new SpruceTexturedButtonWidget(Position.of(this.width / 2 + 106, this.height / 4 + 8), 20, 20, Text.translatable("config.hudium-config.save"),
                btn -> this.client.setScreen(new HudiumConfigScreen(this)), 0, 0, 20, new Identifier(HudiumClient.MOD_ID, "textures/gui/hudium_config.png"), 32, 64).asVanilla());
    }
}
