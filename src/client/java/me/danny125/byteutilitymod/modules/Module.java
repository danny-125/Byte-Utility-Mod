package me.danny125.byteutilitymod.modules;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Module {
    public boolean toggled;
    public String name;
    public int key;
    public CATEGORY category;
    public boolean enableOnStart;

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
        this.key = KEYBIND;
        this.name = MODULE_NAME;
        this.enableOnStart = ENABLE_ON_START;
    }

    public int getKey(){
        return this.key;
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
