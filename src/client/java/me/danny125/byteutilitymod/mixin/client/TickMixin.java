package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.event.EventDirection;
import me.danny125.byteutilitymod.event.EventType;
import me.danny125.byteutilitymod.event.TickEvent;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class TickMixin {
    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void tick(CallbackInfo info) {
        BYTE.INSTANCE.onEvent(new TickEvent(info, EventType.PRE, EventDirection.INCOMING));}
    private void tickPost(CallbackInfo info) { BYTE.INSTANCE.onEvent(new TickEvent(info, EventType.POST, EventDirection.INCOMING)); }
}