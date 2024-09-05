package me.danny125.byteutilitymod.modules.hud;

import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;

public class HUD extends Module{
    public HUD(){
        super("HUD",0,CATEGORY.HUD,true);
    }

    @Override
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info) {
        super.onRender(drawContext, renderTickCounter, info);
        if(this.toggled){
            MinecraftClient client = MinecraftClient.getInstance();

            int x = 10;
            int y = 10;

            int MODULE_COUNT = 0;

            Initialize.modules.sort(Comparator.comparingInt(m -> client.textRenderer.getWidth(((Module)m).getName())).reversed());

            for(Module m : Initialize.INSTANCE.modules){
                if(m.isToggled()){
                    String text = m.getName();

                    int textWidth = client.textRenderer.getWidth(text);
                    int textHeight = client.textRenderer.fontHeight;
                    int screenWidth = drawContext.getScaledWindowWidth();
                    int screenHeight = drawContext.getScaledWindowHeight();

                    drawContext.getMatrices().push();
                    //drawContext.getMatrices().scale(1, 1, 1);
                    drawContext.drawText(client.textRenderer, text, x, y+(MODULE_COUNT*textHeight+1), 0xFFFFFF, true);
                    drawContext.getMatrices().pop();
                    MODULE_COUNT++;
                }
            }
        }
    }
}
