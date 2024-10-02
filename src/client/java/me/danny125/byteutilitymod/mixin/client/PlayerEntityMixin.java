package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.BYTE;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void disableFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if(BYTE.INSTANCE.isModuleToggled("NoFall")) {
            if(BYTE.INSTANCE.getModuleMode("NoFall").equals("Singleplayer")){
                cir.setReturnValue(false);
            }
        }
    }
}