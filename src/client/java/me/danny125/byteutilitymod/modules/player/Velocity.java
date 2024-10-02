package me.danny125.byteutilitymod.modules.player;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.KnockbackEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.NumberSetting;
import me.danny125.byteutilitymod.util.Vec;

public class Velocity extends Module {

    public NumberSetting amount = new NumberSetting("Knockback",50.0,0.0,100.0,5.0,"percent");

    public Velocity(){
        super("Velocity",0, CATEGORY.PLAYER,false);
        this.addSettings(amount);
    }

    @Override
    public void onEvent(Event e) {
        super.onEvent(e);
        if(e instanceof KnockbackEvent){
            KnockbackEvent knockbackEvent = (KnockbackEvent) e;

            double multiplier = amount.getValue()/100.0;

            Vec v = knockbackEvent.getVelocity();
            Vec newVelocity = new Vec(v.getX()*multiplier,v.getY()*multiplier,v.getZ()*multiplier);
            knockbackEvent.setVelocity(newVelocity);
        }
    }
}
