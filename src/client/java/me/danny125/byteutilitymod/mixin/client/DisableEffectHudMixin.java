package me.danny125.byteutilitymod.mixin.client;


import me.danny125.byteutilitymod.BYTE;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHudMixin.class)
public class DisableEffectHudMixin {
    @Inject(at = @At("HEAD"), method = "renderStatusEffectOverlay", cancellable = true)
    public void renderStatusEffectOverlay(MatrixStack matrixStack,CallbackInfo ci){
        if(BYTE.INSTANCE.isModuleToggled("DisableEffectHud")){
            ci.cancel();
            return;
        }
    }
}
