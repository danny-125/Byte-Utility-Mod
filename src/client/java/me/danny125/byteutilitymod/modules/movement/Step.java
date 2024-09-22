package me.danny125.byteutilitymod.modules.movement;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.entity.attribute.EntityAttributes;

public class Step extends Module {
    public Step() {
        super("Step", 0, CATEGORY.MOVEMENT, false);
    }

    public void onEvent(Event event) {
        if(event instanceof TickEvent){
            if(mc.player != null) {
                if (this.toggled && mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).getValue() == 0.6) {
                    mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(1);
                }
            }
        }
    }

    @Override
    public void onEnable() {
        if(mc.world != null) {
            mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(1);
        }
    }

    @Override
    public void onDisable() {
        if(mc.world != null) {
            mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(0.6);
        }
    }
}
