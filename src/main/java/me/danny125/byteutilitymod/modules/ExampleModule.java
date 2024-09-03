package me.danny125.byteutilitymod.modules;

import me.danny125.byteutilitymod.modules.Module;

//This is an example of a module for Byte Utility Mod
//NOTE: This is not actually added to the module list this is just for representation.
public class ExampleModule extends Module{
    public ExampleModule() {
        //First arg is the module name, second is the keybind as integer, third is category.
        super("ExampleModule",0, CATEGORY.MISCELLANEOUS);
    }

    // On tick
    @Override
    public void onTick() {
        super.onTick();
    }
}
