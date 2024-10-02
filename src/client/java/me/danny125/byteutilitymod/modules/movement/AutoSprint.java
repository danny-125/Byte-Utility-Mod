package me.danny125.byteutilitymod.modules.movement;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.BooleanSetting;

public class AutoSprint extends Module {

    public BooleanSetting omni = new BooleanSetting("Omni", false);

    public AutoSprint() {
        super("AutoSprint",0,CATEGORY.MOVEMENT,false);
        this.addSettings(omni);
    }

    @Override
    public void onEvent(Event e) {
        super.onEvent(e);
        if(e instanceof TickEvent && this.toggled){
            if(mc.world == null){
                return;
            }
            if(omni.isToggled()){
                if(!mc.player.isSprinting()){
                    mc.player.setSprinting(true);
                }
            }else{
                if((Math.abs(mc.player.getMovement().getX()) > 0.075) && !mc.player.isBlocking() && !mc.player.isSneaking() || (Math.abs(mc.player.getMovement().getZ()) > 0.075) && !mc.player.isBlocking() && !mc.player.isSneaking()){
                    mc.player.setSprinting(true);
                }else{
                    mc.player.setSprinting(false);
                }
            }
        }
    }
}
