package me.danny125.byteutilitymod.modules.hud;

import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.NumberSetting;

public class Color extends Module {
    public NumberSetting red = new NumberSetting("Red",17.0,0.0,255.0,5.0,"");
    public NumberSetting green = new NumberSetting("Green",119.0,0.0,255.0,5.0,"");
    public NumberSetting blue = new NumberSetting("Blue",229.0,0.0,255.0,5.0,"");
    public Color(){
        super("Color",0,CATEGORY.HUD,false);
        this.addSettings(red,green,blue);
    }
}
