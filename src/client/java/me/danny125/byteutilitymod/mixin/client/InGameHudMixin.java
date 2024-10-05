package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.event.GuiEvent;
import me.danny125.byteutilitymod.modules.hud.DisableEffectHud;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin {
    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable=true)
    public void renderStatusEffectOverlay(DrawContext drawContext,RenderTickCounter renderTickCounter,CallbackInfo callbackInfo) {
    if(BYTE.INSTANCE.isModuleToggled("DisableEffectHud")){
        callbackInfo.cancel();
        return;
    }
    }
    @Inject(method = "render", at = @At("HEAD"), cancellable=true)
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo ci) {
        BYTE.INSTANCE.onRender(drawContext,renderTickCounter,ci);
        BYTE.INSTANCE.onEvent(new GuiEvent(drawContext,renderTickCounter));
    }
}
