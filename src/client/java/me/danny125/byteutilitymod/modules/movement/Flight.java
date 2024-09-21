package me.danny125.byteutilitymod.modules.movement;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.ModeSetting;
import me.danny125.byteutilitymod.util.PacketUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Flight extends Module {

    public ModeSetting mode = new ModeSetting("Mode","Vanilla","Vulcan","Vanilla");

    public Flight() {
        super("Flight", GLFW.GLFW_KEY_G,CATEGORY.MOVEMENT,false);
        this.addSettings(mode);
    }

    @Override
    public void onEvent(Event e) {
        if(!(e instanceof TickEvent)){
            return;
        }
        super.onEvent(e);
        if(mc.player != null){
            if(this.toggled && mode.getMode().equals("Vanilla")){
                if(!mc.player.getAbilities().allowFlying){
                    mc.player.getAbilities().allowFlying = true;
                }
                if(!mc.player.getAbilities().flying){
                    mc.player.getAbilities().flying = true;
                }
            }
            if(mode.getMode().equals("Vulcan") && this.toggled){
                PacketUtil.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
                Vec3d velocity = mc.player.getVelocity();
                mc.player.jump();
                if (mc.options.jumpKey.isPressed()) {
                    mc.player.setVelocity(velocity.x,0.5f,velocity.z);;
                } else {
                    if (mc.options.sneakKey.isPressed()) {
                        mc.player.setVelocity(velocity.x,-0.5f,velocity.z);;
                    } else {
                        mc.player.setVelocity(velocity.x,0f,velocity.z);;
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(mc.player != null) {
            if(mode.getMode().equals("Vanilla")) {
                mc.player.getAbilities().allowFlying = true;
                mc.player.getAbilities().flying = true;
            }
            if(mode.getMode().equals("Vulcan")){
                mc.player.jump();
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if(mc.player != null) {
            if(mode.getMode().equals("Vanilla")) {
                mc.player.getAbilities().allowFlying = false;
                mc.player.getAbilities().flying = false;
            }
        }
    }
}
