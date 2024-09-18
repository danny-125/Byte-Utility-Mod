package me.danny125.byteutilitymod.modules.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.mixin.client.IdentifierAccessor;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.ModeSetting;
import me.danny125.byteutilitymod.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.Objects;

public class HUD extends Module{
    public HUD(){
        super("HUD",0,CATEGORY.HUD,true);
    }

    @Override
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info) {
        super.onRender(drawContext, renderTickCounter, info);
        if(this.toggled){
            MinecraftClient client = MinecraftClient.getInstance();
            Identifier imageTexture = IdentifierAccessor.createIdentifier("byte-utility-mod", "textures/gui/logo.png");
            if(Initialize.INSTANCE.getModuleByName("Color").isToggled()){
                for(Setting s : Initialize.INSTANCE.getModuleByName("Color").settings){
                    if(s instanceof ModeSetting){
                        ModeSetting m = (ModeSetting)s;
                        if(Objects.equals(m.getMode(), "Dameion")){
                            imageTexture = IdentifierAccessor.createIdentifier("byte-utility-mod", "textures/gui/dameion.png");
                        }
                    }
                }
            }

            RenderSystem.setShaderTexture(0, imageTexture);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            RenderSystem.setShaderColor((float)(Initialize.getColor().getRed()/5), (float)(Initialize.getColor().getGreen()/5), (float)(Initialize.getColor().getBlue()/5), 1.0f); // Red tint

            drawContext.getMatrices().push();

            float scale = 0.5f;
            drawContext.getMatrices().scale(scale, scale, 1.0f);

            int scaledX = (int) (10 / scale);
            int scaledY = (int) (10 / scale);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            drawContext.drawTexture(imageTexture, scaledX, scaledY, 0, 0, 200, 200);

            RenderSystem.disableBlend();

            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

            drawContext.getMatrices().pop();

            int x = 13;
            int y = 17;

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
                    drawContext.drawText(client.textRenderer, text, x, y+(MODULE_COUNT*textHeight+1)+75, Initialize.getColor().getRGB(), true);
                    drawContext.getMatrices().pop();
                    MODULE_COUNT++;
                }
            }
        }
    }
}
