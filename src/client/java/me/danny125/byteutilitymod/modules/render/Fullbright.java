package me.danny125.byteutilitymod.modules.render;

import me.danny125.byteutilitymod.ByteUtilityMod;
import me.danny125.byteutilitymod.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Fullbright extends Module{
    public Fullbright() {
        super("Fullbright", GLFW.GLFW_KEY_G,CATEGORY.RENDER,false);
    }

    private static boolean nightVisionEnabled = false;

    //Check if player has night vision in case the module is enabled by default or enabled in the config.
    //This is necessary because it won't be able to set night vision if player is not loaded into world/sever.
    @Override
    public void onTick(CallbackInfo info) {
        super.onTick(info);
        if(this.toggled){
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                if (!player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                    StatusEffectInstance nightVision = new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 2, false, false, true);
                    player.addStatusEffect(nightVision);
                    nightVisionEnabled = true;
                }
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ByteUtilityMod.LOGGER.info("Fullbright enabled");
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            StatusEffectInstance nightVision = new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 2, false, false, true);
            player.addStatusEffect(nightVision);
            nightVisionEnabled = true;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ByteUtilityMod.LOGGER.info("Fullbright disabled");
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            if(nightVisionEnabled) {
                player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                nightVisionEnabled = false;
            }
        }
    }
}
