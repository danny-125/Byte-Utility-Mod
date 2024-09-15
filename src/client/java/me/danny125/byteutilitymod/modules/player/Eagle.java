package me.danny125.byteutilitymod.modules.player;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.AirBlockItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Eagle extends Module {
    public Eagle(){
        super("Eagle",0,CATEGORY.PLAYER,false);
    }

    MinecraftClient mc = MinecraftClient.getInstance();

    public void onDisable(){
        if(mc != null && mc.player != null) {
            mc.options.sneakKey.setPressed(false);
        }
    }

    @Override
    public void onEvent(Event e){
        super.onEvent(e);
        if(!(e instanceof TickEvent)){
            return;
        }
        if(mc.player != null && this.toggled) {
            BlockPos posBelowPlayer = mc.player.getBlockPos().down();

            if (mc.world.isAir(posBelowPlayer)) {
                // Force the player to sneak when they're near the edge
                mc.options.sneakKey.setPressed(true);
            } else {
                // Allow normal movement otherwise
                mc.options.sneakKey.setPressed(false);
            }
        }
    }
}
