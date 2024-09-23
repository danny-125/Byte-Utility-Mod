package me.danny125.byteutilitymod;

import me.danny125.byteutilitymod.commands.Command;
import me.danny125.byteutilitymod.commands.Config;
import me.danny125.byteutilitymod.commands.Help;
import me.danny125.byteutilitymod.commands.Ping;
import me.danny125.byteutilitymod.event.ChatEvent;
import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.JoinWorldEvent;
import me.danny125.byteutilitymod.extension.Extension;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.modules.combat.KillAura;
import me.danny125.byteutilitymod.modules.hud.ClickGuiModule;
import me.danny125.byteutilitymod.modules.hud.HUD;
import me.danny125.byteutilitymod.modules.misc.LSD;
import me.danny125.byteutilitymod.modules.misc.Panic;
import me.danny125.byteutilitymod.modules.movement.AutoSprint;
import me.danny125.byteutilitymod.modules.movement.Flight;
import me.danny125.byteutilitymod.modules.movement.InfJump;
import me.danny125.byteutilitymod.modules.movement.Step;
import me.danny125.byteutilitymod.modules.player.Eagle;
import me.danny125.byteutilitymod.modules.player.NoFall;
import me.danny125.byteutilitymod.modules.player.Velocity;
import me.danny125.byteutilitymod.modules.render.ESP;
import me.danny125.byteutilitymod.modules.render.Fullbright;
import me.danny125.byteutilitymod.modules.render.MobESP;
import me.danny125.byteutilitymod.settings.*;
import me.danny125.byteutilitymod.ui.ClickGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Initialize {
    public static String MOD_VERSION = "0.4";

    public static Initialize INSTANCE = new Initialize();

    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public static CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<Command>();
    public static CopyOnWriteArrayList<Extension> extensions = new CopyOnWriteArrayList<>();

    //communicate with the GameRendererMixin for the LSD module
    public static boolean loadPostProcessor = false;

    public static boolean firstTick = false;

    //Click gui values
    public int screenWidth = 0;
    public int screenHeight = 0;
    //end of click gui values

    public static String newline = System.getProperty("line.separator");

    public static Color getColor() {
        int red = 0;
        int green = 0;
        int blue = 0;
        for(Module m: modules) {
            if(m.getName() == "Color") {
                if(m.toggled) {
                    for(Setting s: m.ListSettings()) {
                        if(s instanceof ModeSetting) {
                            ModeSetting setting = (ModeSetting) s;
                            if(Objects.equals(setting.getMode(), "Red")) {
                                return new Color(200,0,0);
                            }
                            if(Objects.equals(((ModeSetting) s).getMode(), "Damieon")) {
                                return new Color(0,255,0);
                            }
                            if(Objects.equals(((ModeSetting) s).getMode(), "Blue")) {
                                return new Color(0,0,255);
                            }
                            if(Objects.equals(((ModeSetting) s).getMode(), "DamieonPurple")){
                                return new Color(150,0,250);
                            }
                        }

                    }
                }else {
                    return new Color(255,0,0);
                }
            }
        }
        return new Color(255,0,0);
    }



    // very sigma function that initializes the utility mod :)
    public static boolean InitializeMod(){
        try{
            ByteUtilityMod.LOGGER.info("Starting Byte Utility Mod v" + MOD_VERSION);
            System.setProperty("java.awt.headless", "false");
            //add modules to module list
            //modules.add(new ExampleModule());
            modules.add(new Fullbright());
            modules.add(new HUD());
            modules.add(new Flight());
            modules.add(new NoFall());
            modules.add(new KillAura());
            modules.add(new LSD());
            modules.add(new ClickGuiModule());
            modules.add(new Eagle());
            modules.add(new Velocity());
            modules.add(new ESP());
            modules.add(new me.danny125.byteutilitymod.modules.hud.Color());
            modules.add(new Panic());
            modules.add(new AutoSprint());
            modules.add(new InfJump());
            modules.add(new MobESP());
            modules.add(new Step());
            //Enable modules that have ENABLE_ON_START set to true
            enableStartupModules();
            //add commands to command list
            commands.add(new Help());
            commands.add(new Config());
            commands.add(new Ping());
            //Add config stuff here
            loadConfig("ByteConfig.txt");
        }catch (Exception e){
            ByteUtilityMod.LOGGER.error("Error whilst initializing: " + e.getMessage());
            return false;
        }
        return true;
    }
    public static void registerExtension(Extension extension) {
        extensions.add(extension);
        if(!extension.modules.isEmpty()){
            modules.addAll(extension.modules);
        }
        if(!extension.commands.isEmpty()){
            commands.addAll(extension.commands);
        }
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

    //config system taken from my old client called peroxide

    public static void saveConfig(String configfile) {
        // save the configuration file
        String config = "";
        for(Module m: Initialize.modules) {

            for(Setting s : m.ListSettings()) {
                if(s instanceof NumberSetting) {
                    NumberSetting setting = (NumberSetting) s;
                    config = config + m.getName() + s.name + setting.getValue() + newline;
                }
                if(s instanceof KeyBindSetting) {
                    KeyBindSetting setting = (KeyBindSetting) s;
                    config = config + m.getName() + s.name + setting.getCode() + newline;
                }
                if(s instanceof ModeSetting) {
                    ModeSetting setting = (ModeSetting) s;
                    config = config + m.getName() + s.name + setting.getIndex() + newline;
                }
                if(s instanceof BooleanSetting) {
                    BooleanSetting setting = (BooleanSetting) s;
                    config = config + m.getName() + s.name + setting.isToggled() + newline;
                }
            }
            config = config + m.getName() + "Toggled" + m.toggled + newline;

            File file = new File(configfile);
            if(file.exists()) {
                file.delete();
                try (PrintWriter out = new PrintWriter(configfile)) {
                    out.println(config);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }else {
                try (PrintWriter out = new PrintWriter(configfile)) {
                    out.println(config);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

    public static void loadConfig(String configfile) {
        File config = new File(configfile);

        if (config.exists()) {
            for (Module m : Initialize.modules) {

                Path path = Paths.get(configfile);
                List<String> lines = null;
                try {
                    lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                for (String line : lines) {
                    if(line.contains(m.getName() + "Toggledtrue") && m.getName() != "ClickGui") {
                        m.toggled = true;
                    }
                    if(line.contains(m.getName() + "Toggledfalse") && m.getName() != "ClickGui") {
                        m.toggled = false;
                    }

                    for (Setting s : m.ListSettings()) {
                        if (s instanceof NumberSetting) {
                            NumberSetting setting = (NumberSetting) s;
                            String SettingNoValue = m.getName() + s.name;

                            if (line.startsWith(SettingNoValue)) {
                                String value = line.substring(SettingNoValue.length());
                                double valueDouble = Double.parseDouble(value);
                                setting.setValue(valueDouble);
                                continue;
                            }
                        }

                        if (s instanceof ModeSetting) {
                            ModeSetting setting = (ModeSetting) s;
                            String SettingNoValue = m.getName() + s.name;

                            if (line.startsWith(SettingNoValue)) {
                                String value = line.substring(SettingNoValue.length());
                                int valueInt = Integer.parseInt(value);
                                setting.setIndex(valueInt);
                                continue;
                            }
                        }

                        if (s instanceof KeyBindSetting) {
                            KeyBindSetting setting = (KeyBindSetting) s;
                            String SettingNoValue = m.getName() + s.name;

                            if (line.startsWith(SettingNoValue)) {
                                String value = line.substring(SettingNoValue.length());
                                int valueInt = Integer.parseInt(value);
                                setting.setCode(valueInt);
                                continue;
                            }
                        }

                        if (s instanceof BooleanSetting) {
                            BooleanSetting setting = (BooleanSetting) s;
                            String SettingNoValue = m.getName() + s.name;

                            if (line.startsWith(SettingNoValue)) {
                                String truefalse = line.substring(SettingNoValue.length());
                                if(truefalse.contains("true")) {
                                    setting.setToggled(true);
                                    continue;
                                }else {
                                    setting.setToggled(false);
                                    continue;
                                }
                            }
                        }
                    }

                }
            }
        }
    }
    public static void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info){
        for(Module module : modules){
            module.onRender(drawContext, renderTickCounter, info);
        }
    }
    public static void onGameRender(CallbackInfo info){
        for(Module module : modules){
            module.onGameRender(info);
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
        if(ClickGui.INSTANCE.changing && key != GLFW.GLFW_KEY_ESCAPE && key != GLFW.GLFW_MOUSE_BUTTON_RIGHT){
            ((KeyBindSetting)ClickGui.INSTANCE.currentSetting).setCode(key);
            ClickGui.changing = false;
        }
    }
    public Module getModuleByName(String moduleName){
        for(Module module : modules){
            if(module.getName().equals(moduleName)){
                return module;
            }
        }
        return null;
    }
    public boolean isModuleToggled(String moduleName) {
        for (Module module : modules) {
            if (module.getName().equals(moduleName)) {
                return module.isToggled();
            }
        }
        return false;
    }
    public void onEvent(Event e){
        if(e instanceof JoinWorldEvent){
                for(Module module : modules){
                    if(module.isToggled()){
                        module.onEnable();
                    }
                }
        }
        if(e instanceof ChatEvent){
            ChatEvent chatEvent = (ChatEvent) e;
            String message = chatEvent.getMessage();

            for(Command command : commands){
                if(message.startsWith("#")){
                    e.cancel(true);
                    String[] args = message.split(" ");
                    if(args[0].equals("#" + command.getCommand())){
                        command.runCommand(message);
                    }
                }
            }
        }
        for(Module module: modules){
            module.onEvent(e);
        }
    }
    public String getModuleMode(String moduleName){
        String mode = "";
        for(Module module : modules){
            if(module.getName().equals(moduleName)){
                for(Setting s : module.settings){
                    if(s.name.equals("Mode")){
                        mode = ((ModeSetting)s).getMode();
                    }
                }
            }
        }
        return mode;
    }
}
