package me.danny125.byteutilitymod.modules.combat;

import me.danny125.byteutilitymod.modules.Module;
import org.lwjgl.glfw.GLFW;

public class KillAura extends Module {
    public KillAura() {
        super("KillAura", GLFW.GLFW_KEY_R, CATEGORY.COMBAT, false);
    }
}
