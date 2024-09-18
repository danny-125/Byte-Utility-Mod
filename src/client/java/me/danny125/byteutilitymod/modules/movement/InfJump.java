package me.danny125.byteutilitymod.modules.movement;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;

public class InfJump extends Module {
    public InfJump() {
        super("InfJump",0,CATEGORY.MOVEMENT,false);
    }
    @Override
    public void onEvent(Event e) {
        super.onEvent(e);
        if(e instanceof TickEvent && this.toggled){
            MinecraftClient.getInstance().player.setOnGround(true);
        }
    }
}
