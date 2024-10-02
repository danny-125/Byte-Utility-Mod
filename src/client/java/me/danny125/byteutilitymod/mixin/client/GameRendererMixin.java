package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.BYTE;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
            if(BYTE.INSTANCE.loadPostProcessor) {
                BYTE.INSTANCE.loadPostProcessor = false;
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
        return BYTE.INSTANCE.isModuleToggled("AcidTrip");
    }
}
