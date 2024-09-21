package me.danny125.byteutilitymod.modules.player;

import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.ModeSetting;
import me.danny125.byteutilitymod.util.PacketUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

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
            if(mc.player != null && this.toggled) {
                if (mc.player.fallDistance > 2.0F || Initialize.INSTANCE.isModuleToggled("Flight")) {
                    Packet<?> noFallPacket = new PlayerMoveC2SPacket.OnGroundOnly(true);
                    PacketUtil.sendPacket(noFallPacket);
                }
            }
        }
    }
}

