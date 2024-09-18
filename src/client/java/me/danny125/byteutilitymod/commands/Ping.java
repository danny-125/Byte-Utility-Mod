package me.danny125.byteutilitymod.commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Ping extends Command{
    public Ping(){
        super("ping","Pong!");
    }
    @Override
    public void runCommand(){
        super.runCommand();
        if(MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.literal("§5Pong!"));
        }
    }
}
