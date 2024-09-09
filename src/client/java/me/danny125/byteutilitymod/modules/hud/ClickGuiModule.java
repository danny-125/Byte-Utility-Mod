package me.danny125.byteutilitymod.modules.hud;

import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.ui.ClickGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.lwjgl.glfw.GLFW;

import me.danny125.byteutilitymod.modules.Module;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ClickGuiModule extends Module {

    public ClickGuiModule() {
        super("ClickGui", GLFW.GLFW_KEY_RIGHT_SHIFT, CATEGORY.HUD,false);
    }
    public void onEnable() {
        MinecraftClient.getInstance().setScreen(new ClickGui());
        this.toggle();
    }

    @Override
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info) {
        super.onRender(drawContext, renderTickCounter, info);
        if(Initialize.INSTANCE != null) {
            Initialize.INSTANCE.screenWidth = drawContext.getScaledWindowWidth();
            Initialize.INSTANCE.screenHeight = drawContext.getScaledWindowHeight();
        }
    }
}

