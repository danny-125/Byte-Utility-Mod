package me.danny125.byteutilitymod.modules.player;

import me.danny125.byteutilitymod.modules.Module;

import me.danny125.byteutilitymod.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;

public class Velocity extends Module {
    public NumberSetting modifier = new NumberSetting("Modifier",75.0,0.0,100.0,5.0,"units");
    public Velocity(){
        super("Velocity",0, CATEGORY.PLAYER,false);
        this.addSettings(modifier);
    }
}
