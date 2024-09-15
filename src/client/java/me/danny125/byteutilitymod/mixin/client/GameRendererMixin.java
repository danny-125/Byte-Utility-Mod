package me.danny125.byteutilitymod.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.event.EventDirection;
import me.danny125.byteutilitymod.event.EventType;
import me.danny125.byteutilitymod.event.RenderEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.danny125.byteutilitymod.modules.Module;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    private PostEffectProcessor postProcessor;

    @Shadow
    public abstract void loadPostProcessor(Identifier id);

    @Shadow
    public abstract void disablePostProcessor();

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && isLSDActive()) {
            if(Initialize.INSTANCE.loadPostProcessor) {
                Initialize.INSTANCE.loadPostProcessor = false;
                if (this.postProcessor != null) {
                    this.disablePostProcessor();
                }
                Identifier shader = IdentifierAccessor.createIdentifier("minecraft", "shaders/post/lsd.json");
                this.loadPostProcessor(shader);
            }
        } else {
            if (this.postProcessor != null) {
                this.disablePostProcessor();
            }
        }
    }

    private boolean isLSDActive() {
        return Initialize.INSTANCE.isModuleToggled("LSD");
    }
}
