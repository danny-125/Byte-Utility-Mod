package me.danny125.byteutilitymod;

import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.modules.hud.HUD;
import me.danny125.byteutilitymod.modules.movement.Flight;
import me.danny125.byteutilitymod.modules.player.NoFall;
import me.danny125.byteutilitymod.modules.render.Fullbright;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CopyOnWriteArrayList;

public class Initialize {
    public static String MOD_VERSION = "0.1";

    public static Initialize INSTANCE = new Initialize();

    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();

    // very sigma function that initializes the utility mod :)
    public static boolean InitializeMod(){
        try{
            //add modules to module list
            //modules.add(new ExampleModule());

            modules.add(new Fullbright());
            modules.add(new HUD());
            modules.add(new Flight());
            modules.add(new NoFall());
            //Enable modules that have ENABLE_ON_START set to true
            enableStartupModules();

            //Add config stuff here

        }catch (Exception e){
            ByteUtilityMod.LOGGER.error("Error whilst initializing: " + e.getMessage());
            return false;
        }
        return true;
    }
    public static boolean isGuiOpen(){
        if(MinecraftClient.getInstance().player != null){
            return MinecraftClient.getInstance().currentScreen != null;
        }
        return false;
    }
    public static void enableStartupModules(){
        for(Module module : modules){
            if(module.shouldEnableOnStart()){
                module.toggle();
            }
        }
    }
    public static void onTick(CallbackInfo info){
        for(Module module : modules){
            module.onTick(info);
        }
    }
    public static void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info){
        for(Module module : modules){
            module.onRender(drawContext, renderTickCounter, info);
        }
    }
    public static void keyPress(int key){
        for(Module module : modules){
            int MODULE_KEY = module.getKey();
            if(key == MODULE_KEY && !isGuiOpen()){
                module.toggle();
                if(module.isToggled()){
                    module.onEnable();
                }else{
                    module.onDisable();
                }
            }
        }
    }
    public boolean isModuleToggled(String moduleName) {
        for (Module module : modules) {
            if (module.getName().equals(moduleName)) {
                return module.isToggled();
            }
        }
        return false;
    }
}
