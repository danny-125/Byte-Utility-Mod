package me.danny125.byteutilitymod.modules.movement;

import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.BooleanSetting;
import me.danny125.byteutilitymod.settings.ModeSetting;
import me.danny125.byteutilitymod.util.PacketUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Flight extends Module {

    public ModeSetting mode = new ModeSetting("Mode","Vanilla","Vulcan","Vanilla");

    public Flight() {
        super("Flight", GLFW.GLFW_KEY_G,CATEGORY.MOVEMENT,false);
        this.addSettings(mode);
    }

    @Override
    public void onTick(CallbackInfo info) {
        super.onTick(info);
        if(MinecraftClient.getInstance().player != null){
            if(this.toggled && mode.getMode().equals("Vanilla")){
                if(!MinecraftClient.getInstance().player.getAbilities().allowFlying){
                    MinecraftClient.getInstance().player.getAbilities().allowFlying = true;
                }
                if(!MinecraftClient.getInstance().player.getAbilities().flying){
                    MinecraftClient.getInstance().player.getAbilities().flying = true;
                }
            }
            if(mode.getMode().equals("Vulcan") && this.toggled){
                MinecraftClient mc = MinecraftClient.getInstance();

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
        if(MinecraftClient.getInstance().player != null) {
            if(mode.getMode().equals("Vanilla")) {
                MinecraftClient.getInstance().player.getAbilities().allowFlying = true;
                MinecraftClient.getInstance().player.getAbilities().flying = true;
            }
            if(mode.getMode().equals("Vulcan")){
                MinecraftClient.getInstance().player.jump();
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if(MinecraftClient.getInstance().player != null) {
            if(mode.getMode().equals("Vanilla")) {
                MinecraftClient.getInstance().player.getAbilities().allowFlying = false;
                MinecraftClient.getInstance().player.getAbilities().flying = false;
            }
        }
    }
}
