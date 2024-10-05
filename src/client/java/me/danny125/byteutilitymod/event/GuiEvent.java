package me.danny125.byteutilitymod.event;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class GuiEvent extends Event{
    public DrawContext drawContext;
    public RenderTickCounter renderTickCounter;
    public GuiEvent(DrawContext drawContext,RenderTickCounter renderTickCounter){
        super();
        this.drawContext = drawContext;
        this.renderTickCounter = renderTickCounter;
    }
}
