package me.danny125.byteutilitymod.modules.combat;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.NumberSetting;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker extends Module {

    public NumberSetting cooldown = new NumberSetting("Cooldown",625.0,1.0,2000.0,25.0,"ms");

    public AutoClicker() {
        super("AutoClicker",0,CATEGORY.COMBAT,false);
        this.addSettings(cooldown);
    }

    public long lastMS = System.currentTimeMillis();

    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset) {
                reset();
            }
            return true;
        }

        return false;
    }

    public static void click() throws AWTException {
        Robot bot = new Robot();
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    @Override
    public void onEvent(Event e) {
        super.onEvent(e);
        if(e instanceof TickEvent && this.toggled){
            if (hasTimeElapsed((long)(cooldown.getValue())+1, true)) {
                if (mc.currentScreen == null) {
                    try {
                        click();
                    } catch (AWTException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }
}
