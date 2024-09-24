package me.danny125.byteutilitymod.util;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class TextUtil {
    public static void drawShadedString(DrawContext drawContext, TextRenderer textRenderer, String text, int x, int y, int color, boolean first) {
        drawContext.getMatrices().push();

        float scale = 1.01f;
        drawContext.getMatrices().scale(scale, scale, 1.0f);

        int scaledX = (int) (x / scale);
        int scaledY = (int) (y / scale);

        if(first) {
            drawContext.drawText(textRenderer, text, scaledX + 1, scaledY, 0x00000000, false);
        }else{
            drawContext.drawText(textRenderer, text, scaledX + 1, scaledY+1, 0x00000000, false);
        }

        drawContext.getMatrices().pop();

        drawContext.drawText(textRenderer, text, x, y, color, false);
    }
}