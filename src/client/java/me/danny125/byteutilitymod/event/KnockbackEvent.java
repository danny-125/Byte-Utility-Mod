package me.danny125.byteutilitymod.event;

import me.danny125.byteutilitymod.util.Vec;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class KnockbackEvent extends Event{
    Vec velocity;
    public KnockbackEvent(Vec velocity) {
        super();
        this.velocity = velocity;
    }
    public void setVelocity(Vec newVelocity){
        this.velocity = newVelocity;
    }
    public Vec getVelocity(){
        return this.velocity;
    }
}
