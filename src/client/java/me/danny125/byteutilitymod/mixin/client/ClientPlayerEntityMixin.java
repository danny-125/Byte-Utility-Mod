package me.danny125.byteutilitymod.mixin.client;

import com.mojang.authlib.GameProfile;
import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.event.EventType;
import me.danny125.byteutilitymod.event.KnockbackEvent;
import me.danny125.byteutilitymod.event.MotionEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.util.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow
    @Final
    protected MinecraftClient client;

    private Screen tempCurrentScreen;
    private boolean hideNextItemUse;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile)
    {
        super(world, profile);
    }
    @Inject(at = @At("HEAD"), method = "sendMovementPackets()V")
    private void onSendMovementPacketsHEAD(CallbackInfo ci)
    {
        MotionEvent m = new MotionEvent();
        m.setEventType(EventType.PRE);
        BYTE.INSTANCE.onEvent(m);
    }

    @Inject(at = @At("TAIL"), method = "sendMovementPackets()V")
    private void onSendMovementPacketsTAIL(CallbackInfo ci)
    {
        MotionEvent m = new MotionEvent();
        m.setEventType(EventType.POST);
        BYTE.INSTANCE.onEvent(m);
    }
    @Override
    public void setVelocityClient(double x, double y, double z)
    {
        KnockbackEvent event = new KnockbackEvent(new Vec(x, y, z));
        BYTE.INSTANCE.onEvent(event);
        Vec v = event.getVelocity();
        super.setVelocityClient(v.getX(),v.getY(),v.getZ());
    }
}
