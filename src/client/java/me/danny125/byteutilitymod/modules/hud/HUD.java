package me.danny125.byteutilitymod.modules.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.mixin.client.IdentifierAccessor;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.ModeSetting;
import me.danny125.byteutilitymod.settings.Setting;
import me.danny125.byteutilitymod.util.TextUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

public class HUD extends Module{
    public HUD(){
        super("HUD",0,CATEGORY.HUD,true);
    }

    @Override
    public void onRender(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo info) {
        super.onRender(drawContext, renderTickCounter, info);
        if(this.toggled && mc.textRenderer != null){
            Identifier imageTexture = IdentifierAccessor.createIdentifier("byte-utility-mod", "textures/gui/logo.png");
            if(Initialize.INSTANCE.getModuleByName("Color").isToggled()){
                for(Setting s : Initialize.INSTANCE.getModuleByName("Color").settings){
                    if(s instanceof ModeSetting){
                        ModeSetting m = (ModeSetting)s;
                        if(Objects.equals(m.getMode(), "Damieon") || Objects.equals(m.getMode(), "DamieonPurple") ){
                            imageTexture = IdentifierAccessor.createIdentifier("byte-utility-mod", "textures/gui/dameion.png");
                        }
                    }
                }
            }

            RenderSystem.setShaderTexture(0, imageTexture);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            RenderSystem.setShaderColor((float)(Initialize.getColor().getRed()/2), (float)(Initialize.getColor().getGreen()/2), (float)(Initialize.getColor().getBlue()/2), 1.0f); // Red tint

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

            Initialize.modules.sort(Comparator.comparingInt(m -> mc.textRenderer.getWidth(((Module)m).getName())).reversed());

            for(Module m : Initialize.INSTANCE.modules){
                if(m.isToggled()){
                    String text = m.getName();
                    int textWidth = mc.textRenderer.getWidth(text);
                    int textHeight = mc.textRenderer.fontHeight;
                    int screenWidth = drawContext.getScaledWindowWidth();
                    int screenHeight = drawContext.getScaledWindowHeight();
                    drawContext.getMatrices().push();
                    TextUtil.drawShadedString(drawContext,mc.textRenderer, text, x, y+(MODULE_COUNT*textHeight+1)+75, Initialize.getColor().getRGB(), MODULE_COUNT == 0);
                    drawContext.getMatrices().pop();
                    MODULE_COUNT++;
                }
            }
        }
    }
}
