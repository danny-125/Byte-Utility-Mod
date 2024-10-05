package me.danny125.byteutilitymod.modules.hud;

import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Name extends Module {
    public Name(){
        super("Name",0,CATEGORY.HUD,true);
    }

    @Override
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info) {
        super.onRender(drawContext, renderTickCounter, info);
        if(this.toggled){
            MinecraftClient client = MinecraftClient.getInstance();

            String text = "Byte v" + BYTE.MOD_VERSION;
            int scale = 2;

            drawContext.getMatrices().push();
            drawContext.getMatrices().scale(scale, scale, 1);
            drawContext.drawText(client.textRenderer, text, 10 / scale, 10 / scale, BYTE.getColor().getRGB(), true);
            drawContext.getMatrices().pop();
        }
    }
}
