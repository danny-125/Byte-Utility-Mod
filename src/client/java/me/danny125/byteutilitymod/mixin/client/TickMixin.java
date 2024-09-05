package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.Initialize;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class TickMixin {
    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void tick(CallbackInfo info) {
        Initialize.INSTANCE.onTick(info);
    }
}