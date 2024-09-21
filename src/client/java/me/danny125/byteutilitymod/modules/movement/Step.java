package me.danny125.byteutilitymod.modules.movement;

import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.entity.attribute.EntityAttributes;

public class Step extends Module {
    public Step() {
        super("Step", 0, CATEGORY.MOVEMENT, false);
    }

    @Override
    public void onEnable() {
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(1);
    }

    @Override
    public void onDisable() {
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(0.6);
    }
}
