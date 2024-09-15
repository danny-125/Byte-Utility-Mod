package me.danny125.byteutilitymod.modules.player;

import me.danny125.byteutilitymod.ByteUtilityModClient;
import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.ModeSetting;
import me.danny125.byteutilitymod.util.PacketUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class NoFall extends Module{

    public ModeSetting mode = new ModeSetting("Mode","GroundSpoof","GroundSpoof","Singleplayer");

    public NoFall(){
        super("NoFall", 0,CATEGORY.PLAYER,false);
        this.addSettings(mode);
    }

    @Override
    public void onEvent(Event e) {
        if(!(e instanceof TickEvent)){
            return;
        }
        super.onEvent(e);
        if(mode.getMode().startsWith("GroundSpoof")){
            if(MinecraftClient.getInstance().player != null && this.toggled) {
                if (MinecraftClient.getInstance().player.fallDistance > 2.0F || Initialize.INSTANCE.isModuleToggled("Flight")) {
                    Packet<?> noFallPacket = new PlayerMoveC2SPacket.OnGroundOnly(true);
                    PacketUtil.sendPacket(noFallPacket);
                }
            }
        }
    }
}

