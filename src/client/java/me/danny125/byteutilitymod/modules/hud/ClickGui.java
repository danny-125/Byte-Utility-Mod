package me.danny125.byteutilitymod.modules.hud;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import me.danny125.byteutilitymod.modules.Module;

public class ClickGui extends Module {

    public ClickGui() {
        super("ClickGui", GLFW.GLFW_KEY_RIGHT_SHIFT, CATEGORY.RENDER,false);
    }
    public void onEnable() {

    }
}

