package me.danny125.byteutilitymod.modules.player;

import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class NoFall extends Module{
    public NoFall(){
        super("NoFall", GLFW.GLFW_KEY_U,CATEGORY.PLAYER,true);
    }
}

