package me.danny125.byteutilitymod.modules.hud;

import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HUD extends Module{
    public HUD(){
        super("HUD",0,CATEGORY.HUD,true);
    }

    @Override
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info) {
        super.onRender(drawContext, renderTickCounter, info);
        if(this.toggled){
            MinecraftClient client = MinecraftClient.getInstance();

            String text = "Byte Utility Mod";
            int scale = 2;

            int textWidth = client.textRenderer.getWidth(text) * scale;

            int screenWidth = drawContext.getScaledWindowWidth();
            int screenHeight = drawContext.getScaledWindowHeight();

            int x = screenWidth - textWidth - 10;
            int y = 10;

            drawContext.getMatrices().push();
            drawContext.getMatrices().scale(scale, scale, 1);
            drawContext.drawText(client.textRenderer, text, x / scale, y / scale, 0xFFFFFF, true);
            drawContext.getMatrices().pop();
        }
    }
}
