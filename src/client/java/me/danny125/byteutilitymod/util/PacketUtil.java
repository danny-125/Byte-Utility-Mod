package me.danny125.byteutilitymod.util;

import me.danny125.byteutilitymod.event.PacketEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;

import java.util.Timer;
import java.util.TimerTask;

public class PacketUtil {
    public static void sendPacket(Packet<?> packet){
        if(MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.networkHandler.sendPacket(packet);
        }
    }
}
