package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.Initialize;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityVelocityUpdateS2CPacket.class)
public class VelocityUpdateS2CPacketMixin {

    @Inject(method = "<init>(ILnet/minecraft/util/math/Vec3d;)V", at = @At("TAIL"))
    private void onConstructor(int entityId, Vec3d velocity, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world != null && client.player != null) {
            Entity entity = client.world.getEntityById(entityId);

            if (entity instanceof PlayerEntity && entity.equals(client.player)) {
                VelocityUpdateS2CPacketAccessor accessor = (VelocityUpdateS2CPacketAccessor) this;

                if(Initialize.INSTANCE.isModuleToggled("AntiKnockback")) {
                    accessor.setVelocityX(0);
                    accessor.setVelocityY(0);
                    accessor.setVelocityZ(0);
                }
            }
        }
    }
}