package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.event.EventDirection;
import me.danny125.byteutilitymod.event.EventType;
import me.danny125.byteutilitymod.event.JoinWorldEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "joinWorld", at = @At("HEAD"))
    private void onJoinWorld(ClientWorld world, DownloadingTerrainScreen.WorldEntryReason worldEntryReason, CallbackInfo ci) {
        Initialize.INSTANCE.onEvent(new JoinWorldEvent(ci, EventType.PRE, EventDirection.OUTGOING));
    }
}