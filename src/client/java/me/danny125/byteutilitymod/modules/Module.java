package me.danny125.byteutilitymod.modules;

public class Module {
    public boolean toggled;
    public String name;
    public int key;
    public CATEGORY category;

    public enum CATEGORY{
        RENDER,
        MOVEMENT,
        COMBAT,
        MISCELLANEOUS
    }

    public Module(String MODULE_NAME,int KEYBIND,CATEGORY c){
        this.category = c;
        this.key = KEYBIND;
        this.name = MODULE_NAME;
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

    public void onEnable(){

    }

    public void onDisable(){

    }

    public void onTick(){

    }
}
