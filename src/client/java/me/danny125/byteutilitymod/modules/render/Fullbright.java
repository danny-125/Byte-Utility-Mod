package me.danny125.byteutilitymod.modules.render;

import me.danny125.byteutilitymod.ByteUtilityMod;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class Fullbright extends Module{
    public Fullbright() {
        super("Fullbright", GLFW.GLFW_KEY_G,CATEGORY.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ByteUtilityMod.LOGGER.info("Fullbright enabled");
        MinecraftClient.getInstance().options.getGamma().setValue(1.0);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ByteUtilityMod.LOGGER.info("Fullbright disabled");
        MinecraftClient.getInstance().options.getGamma().setValue(0.0);
    }
}
