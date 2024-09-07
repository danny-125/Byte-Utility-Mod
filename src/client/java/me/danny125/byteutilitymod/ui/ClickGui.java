package me.danny125.byteutilitymod.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;

public class ClickGui extends Screen {
    // Constructor
    public ClickGui() {
        super(Text.literal("ClickGUI"));
    }

    @Override
    protected void init() {
        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.literal("Toggle Fly Module"),
                                (button) -> {
                                    //put button code here
                                }
                        ).dimensions(this.width / 2 - 100, this.height / 2 - 20, 200, 20)
                        .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
