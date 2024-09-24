package me.danny125.byteutilitymod.modules.movement;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;

public class InfJump extends Module {
    public InfJump() {
        super("InfJump",0,CATEGORY.MOVEMENT,false);
    }
    @Override
    public void onEvent(Event e) {
        super.onEvent(e);
        if(e instanceof TickEvent && this.toggled && mc.player != null){
            mc.player.setOnGround(true);
        }
    }
}
