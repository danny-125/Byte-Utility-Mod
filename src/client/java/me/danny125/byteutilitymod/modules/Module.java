package me.danny125.byteutilitymod.modules;

import me.danny125.byteutilitymod.settings.KeyBindSetting;
import me.danny125.byteutilitymod.settings.Setting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Module {
    public boolean toggled;
    public String name;
    public CATEGORY category;
    public boolean enableOnStart;
    public List<Setting> settings = new ArrayList<Setting>();
    public KeyBindSetting keyCode = new KeyBindSetting(0);

    public enum CATEGORY{
        RENDER,
        MOVEMENT,
        COMBAT,
        PLAYER,
        HUD,
        MISCELLANEOUS
    }

    public Module(String MODULE_NAME,int KEYBIND,CATEGORY c,Boolean ENABLE_ON_START){
        this.category = c;
        keyCode.setCode(KEYBIND);
        this.addSettings(keyCode);
        this.name = MODULE_NAME;
        this.enableOnStart = ENABLE_ON_START;
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
    }

    public void removeSettings(Setting... settings) {
        this.settings.removeAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
    }

    public List<Setting> ListSettings() {
        List<Setting> s = new ArrayList<>();

        for(Setting s1 : settings) {
            s.add(s1);
        }
        return s;
    }

    public int getKey(){
        return keyCode.code;
    }

    public void toggle(){
        this.toggled = !this.toggled;
    }

    public boolean isToggled(){
        return this.toggled;
    }

    public boolean shouldEnableOnStart(){
        return this.enableOnStart;
    }

    public void onEnable(){

    }

    public void onDisable(){

    }

    public String getName(){
        return this.name;
    }

    public void onTick(CallbackInfo info){

    }
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info){

    }
}
