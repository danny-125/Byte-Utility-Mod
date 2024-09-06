package me.danny125.byteutilitymod.modules.movement;

import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Flight extends Module {
    public Flight() {
        super("Flight", GLFW.GLFW_KEY_G,CATEGORY.MOVEMENT,false);
    }

    @Override
    public void onTick(CallbackInfo info) {
        super.onTick(info);
        if(MinecraftClient.getInstance().player != null){
            if(this.toggled){
                if(!MinecraftClient.getInstance().player.getAbilities().allowFlying){
                    MinecraftClient.getInstance().player.getAbilities().allowFlying = true;
                }
                if(!MinecraftClient.getInstance().player.getAbilities().flying){
                    MinecraftClient.getInstance().player.getAbilities().flying = true;
                }
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.getAbilities().allowFlying = true;
            MinecraftClient.getInstance().player.getAbilities().flying = true;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if(MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.getAbilities().allowFlying = false;
            MinecraftClient.getInstance().player.getAbilities().flying = false;
        }
    }
}
