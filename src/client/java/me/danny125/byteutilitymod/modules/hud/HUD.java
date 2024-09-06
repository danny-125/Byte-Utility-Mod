package me.danny125.byteutilitymod.modules.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.mixin.client.IdentifierAccessor;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
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
            //draw logo on hud
            MinecraftClient client = MinecraftClient.getInstance();
            Identifier imageTexture = IdentifierAccessor.createIdentifier("byte-utility-mod", "textures/gui/logo.png");

            RenderSystem.setShaderTexture(0, imageTexture);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            drawContext.getMatrices().push();

            float scale = 0.5f;
            drawContext.getMatrices().scale(scale, scale, 1.0f);

            int scaledX = (int) (10 / scale);
            int scaledY = (int) (10 / scale);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();  // Set blending function

            drawContext.drawTexture(imageTexture, scaledX, scaledY, 0, 0, 200, 200);

            RenderSystem.disableBlend();

            drawContext.getMatrices().pop();

            int x = 13;
            int y = 17;

            int MODULE_COUNT = 0;

            //Draw module array list
            Initialize.modules.sort(Comparator.comparingInt(m -> client.textRenderer.getWidth(((Module)m).getName())).reversed());

            for(Module m : Initialize.INSTANCE.modules){
                if(m.isToggled()){
                    String text = m.getName();

                    int textWidth = client.textRenderer.getWidth(text);
                    int textHeight = client.textRenderer.fontHeight;
                    int screenWidth = drawContext.getScaledWindowWidth();
                    int screenHeight = drawContext.getScaledWindowHeight();

                    drawContext.getMatrices().push();
                    drawContext.drawText(client.textRenderer, text, x, y+(MODULE_COUNT*textHeight+1)+75, 0xc8171f, true);
                    drawContext.getMatrices().pop();
                    MODULE_COUNT++;
                }
            }
        }
    }
}
