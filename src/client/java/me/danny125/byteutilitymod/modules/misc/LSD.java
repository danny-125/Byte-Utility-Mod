package me.danny125.byteutilitymod.modules.misc;

import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.entity.player.PlayerEntity;

public class LSD extends Module {
    public LSD(){
        super("AcidTrip", 0,CATEGORY.MISCELLANEOUS,false);
    }

    @Override
    public void onEnable() {
        if(!(mc.getCameraEntity() instanceof PlayerEntity))
        {
            this.toggled = false;
            return;
        }

        if(mc.gameRenderer.getPostProcessor() != null)
            mc.gameRenderer.disablePostProcessor();

        BYTE.INSTANCE.loadPostProcessor = true;
    }

    @Override
    public void onDisable() {
        if(mc.gameRenderer.getPostProcessor() != null)
            mc.gameRenderer.disablePostProcessor();
    }
}
