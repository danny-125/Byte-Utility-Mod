package me.danny125.byteutilitymod.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;

public class PacketUtil {
    public static void sendPacket(Packet<?> packet){
        if(MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.networkHandler.sendPacket(packet);
        }
    }
}
