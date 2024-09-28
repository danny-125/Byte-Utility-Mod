package me.danny125.byteutilitymod.event;

import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class PacketEvent extends Event {
    Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        super();
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }
}
