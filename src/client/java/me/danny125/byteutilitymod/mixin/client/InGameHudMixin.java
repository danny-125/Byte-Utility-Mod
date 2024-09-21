package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.Initialize;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable=true)
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo ci) {
        Initialize.INSTANCE.onRender(drawContext,renderTickCounter,ci);
    }
}
