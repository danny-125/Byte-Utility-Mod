package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.settings.NumberSetting;
import me.danny125.byteutilitymod.settings.Setting;
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

                // will change these to make it configurable
                if(Initialize.INSTANCE.isModuleToggled("Velocity")) {
                    for(Setting s : Initialize.INSTANCE.getModuleByName("Velocity").settings){
                        if(s.name == "Modifier" && s instanceof NumberSetting){
                            accessor.setVelocityX((int) (velocity.getX() * (((NumberSetting) s).value/100)));
                            accessor.setVelocityY((int) (velocity.getY() * (((NumberSetting) s).value/100)));
                            accessor.setVelocityZ((int) (velocity.getZ() * (((NumberSetting) s).value/100)));
                        }
                    }
                }
            }
        }
    }
}