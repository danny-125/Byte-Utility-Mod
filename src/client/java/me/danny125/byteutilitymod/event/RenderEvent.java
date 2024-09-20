package me.danny125.byteutilitymod.event;

import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class RenderEvent extends Event {
    public MatrixStack matrixStack;
    public float tickDelta;
    public RenderEvent(CallbackInfo info, EventType type, EventDirection direction, MatrixStack matrixStack,float tickDelta) {
        super(info, type, direction);
        this.matrixStack = matrixStack;
        this.tickDelta = tickDelta;
    }
    public MatrixStack getMatrixStack() {
        return this.matrixStack;
    }
    public float getTickDelta() {
        return this.tickDelta;
    }
}