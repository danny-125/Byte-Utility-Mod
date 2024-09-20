package me.danny125.byteutilitymod.event;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class JoinWorldEvent extends Event {
    public JoinWorldEvent(CallbackInfo ci, EventType eventType, EventDirection eventDirection) {
        super(ci,eventType,eventDirection);
    }
}
