package me.danny125.byteutilitymod.modules;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.settings.KeyBindSetting;
import me.danny125.byteutilitymod.settings.Setting;
import net.minecraft.client.MinecraftClient;
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
    public MinecraftClient mc;

    //used for sliding
    public double x;
    public double y;

    public double posX;
    public double posY;

    public String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
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
        this.mc = MinecraftClient.getInstance();
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
    public Setting getSettingByName(String settingName){
        for(Setting s1 : settings){
            if(s1.name.equals(settingName)){
                return s1;
            }
        }
        return null;
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

    public void onEvent(Event e){

    }
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info){

    }
    public void onGameRender(CallbackInfo info){

    }

    public CATEGORY getCategory(){
        return this.category;
    }
}
