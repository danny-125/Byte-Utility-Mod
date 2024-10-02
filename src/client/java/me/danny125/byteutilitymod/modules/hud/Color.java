package me.danny125.byteutilitymod.modules.hud;

import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.ModeSetting;

public class Color extends Module {


    public ModeSetting mode = new ModeSetting("Mode","Red","Red","Blue","Damieon","DamieonPurple");

    public Color(){
        super("Color",0,CATEGORY.HUD,false);
        this.addSettings(mode);
    }
}
