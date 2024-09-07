package me.danny125.byteutilitymod.modules.hud;

import me.danny125.byteutilitymod.ui.ClickGui;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import me.danny125.byteutilitymod.modules.Module;

public class ClickGuiModule extends Module {

    public ClickGuiModule() {
        super("ClickGui", GLFW.GLFW_KEY_RIGHT_SHIFT, CATEGORY.HUD,false);
    }
    public void onEnable() {
        MinecraftClient.getInstance().setScreen(new ClickGui());
        this.toggle();
    }
}

