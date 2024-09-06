package me.danny125.byteutilitymod.modules.hud;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import me.danny125.byteutilitymod.modules.Module;

public class ClickGui extends Module {

    public ClickGui() {
        super("ClickGui", "Gui to enable or disable modules", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER,false);
    }
    public void onEnable() {
        MinecraftClient.getInstance().displayGuiScreen(new GUIMethod());
        toggle();
    }
}

