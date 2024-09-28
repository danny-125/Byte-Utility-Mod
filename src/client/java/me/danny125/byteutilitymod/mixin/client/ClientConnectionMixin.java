package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.event.EventDirection;
import me.danny125.byteutilitymod.event.EventType;
import me.danny125.byteutilitymod.event.PacketEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("RETURN"),cancellable = true)
    private void onSendPacketPre(Packet<?> packet, CallbackInfo info) {
        PacketEvent packetEvent = new PacketEvent(packet);
        BYTE.INSTANCE.onEvent(packetEvent);

        if(packetEvent.isCancelled()){
            return;
        }
    }
}
