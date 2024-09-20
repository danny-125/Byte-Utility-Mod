package me.danny125.byteutilitymod.event;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ViewBobbingEvent extends Event{
    public ViewBobbingEvent(CallbackInfo ci, EventType eventType, EventDirection eventDirection) {
        super(ci, eventType, eventDirection);
    }
}
