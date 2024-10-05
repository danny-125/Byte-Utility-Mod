package me.danny125.byteutilitymod.modules.hud;

import java.awt.Color;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.GuiEvent;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.BooleanSetting;
import me.danny125.byteutilitymod.settings.ModeSetting;
import me.danny125.byteutilitymod.settings.NumberSetting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;


public class ArrayList extends Module {

    public BooleanSetting render = new BooleanSetting("Show HUD Modules", false);

    public ModeSetting sort = new ModeSetting("Sorting", "Normal", "Normal", "Reversed", "None");



    public static  Color MainColor = BYTE.getColor();

    public static CopyOnWriteArrayList<Module> disabled = new CopyOnWriteArrayList<Module>();

    public ArrayList() {
        super("ArrayList", 0, CATEGORY.HUD,true);
        this.addSettings(render, sort);
        this.toggle();
    }

    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }

    public void onEvent(Event e) {
        if(!this.toggled){
            return;
        }
        if(e instanceof TickEvent) {

             TextRenderer textRenderer = mc.textRenderer;
            if(sort.getMode() == "Normal") {
                BYTE.modules.sort(Comparator.comparingDouble(m -> {
                                    String modulesText = ((Module) m).name;
                                    String prefixText = ((Module) m).getPrefix();

                                    return getStringWidth(textRenderer, modulesText);

                                })
                                .reversed()
                );
            }
            if(sort.getMode() == "Reversed") {
                BYTE.modules.sort(Comparator.comparingDouble(m -> {
                            String modulesText = ((Module) m).name;

                            return getStringWidth(textRenderer, modulesText);

                        })
                );
            }
            if(!render.isToggled()) {
                BYTE.modules.stream().filter(m -> m.category != CATEGORY.RENDER).collect(Collectors.toList());
            }
        }

        if(e instanceof GuiEvent) {

            DrawContext drawContext = ((GuiEvent) e).drawContext;

            drawContext.getMatrices().push();;
            TextRenderer textRenderer = mc.textRenderer;


            posX = BYTE.INSTANCE.screenWidth - 2;
            if( posY == 0) {
                posY = 8;
            }

            drawContext.getMatrices().translate(-6, 0, 0);



            Color color = new Color(255,255,255, 200);



            int count = 0;

            int mcount = 0;


            int mwidth = 0;
            for(Module m : BYTE.modules) {

                boolean Moving;

                Moving = false;

                int multiplyer = textRenderer.fontHeight + 2;



                for(Module mq : BYTE.modules) {
                    if(mq.isToggled())
                        mcount++;
                }

                if(m.toggled && m.name != "ArrayList" && m.name != "Name" && m.category != CATEGORY.HUD || m.toggled && m.name != "Name" && m.name != "ArrayList" && render.isToggled() || disabled.contains(m) && m.name != "HUD" && m.name != "ArrayList") {


                    //sheesh

                    String stringWidth;


                    stringWidth = m.name;


                    if(m.x == 0 && !disabled.contains(m)) {
                        m.x = BYTE.INSTANCE.screenWidth - getStringWidth(textRenderer, stringWidth) + 80;
                    } else if(m.x == BYTE.INSTANCE.screenWidth - getStringWidth(textRenderer, stringWidth) + 80) {
                        Moving = false;
                    }

                    if(m.x > BYTE.INSTANCE.screenWidth + 80) {
                        m.x = posX - getStringWidth(textRenderer, stringWidth);
                    }

                    if(m.x == 0 && disabled.contains(m)) {
                        m.x =  posX - getStringWidth(textRenderer, stringWidth);
                    }

                    if(m.x > BYTE.INSTANCE.screenWidth - getStringWidth(textRenderer, stringWidth) - 0.1 && !disabled.contains(m)) {
                        m.x -= 10;
                        Moving = true;
                    }

                    if(disabled.contains(m)) {
                        m.x += 10;;

                        Moving = true;

                        if(m.x > BYTE.INSTANCE.screenWidth) {
                            disabled.remove(m);
                        }
                    }

                    //System.out.println(Color.TRANSLUCENT);

                    if(Moving == false) {
                        m.x = BYTE.INSTANCE.screenWidth - getStringWidth(textRenderer, stringWidth) - 0.1;
                    }

                    Color outline = new Color(0,0,0);

                    drawContext.drawText(textRenderer,m.name, (int)(m.x + 1), (int)(posY + (count * multiplyer) - 2), BYTE.getColor().getRGB(),true);

                    if(m.prefix != "") {
                        //customFont.drawStringWithShadow(m.prefix, m.x + fr.getStringWidth(name) * 1.2 - 2, 10 + (count * multiplyer), Color.white);
                    }

                    count++;
                    mwidth = getStringWidth(textRenderer,stringWidth);
                }

            }

            drawContext.getMatrices().pop();
        }
    }

    public int getStringWidth(TextRenderer font, String text) {
            return mc.textRenderer.getWidth(text);

    }

}
