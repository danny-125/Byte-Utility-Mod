package me.danny125.byteutilitymod.modules.misc;

import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.mixin.client.GameRendererMixin;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class LSD extends Module {
    public LSD(){
        super("LSD", 0,CATEGORY.MISCELLANEOUS,false);
    }
    MinecraftClient MC = MinecraftClient.getInstance();

    @Override
    public void onEnable()
    {
        if(!(MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity))
        {
            this.toggled = false;
            return;
        }

        if(MC.gameRenderer.getPostProcessor() != null)
            MC.gameRenderer.disablePostProcessor();

        Initialize.INSTANCE.loadPostProcessor = true;
    }

    @Override
    public void onDisable()
    {
        if(MC.gameRenderer.getPostProcessor() != null)
            MC.gameRenderer.disablePostProcessor();
    }
}
