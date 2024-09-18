package me.danny125.byteutilitymod.event;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ChatEvent extends Event{
    public String message;
    public ChatEvent(CallbackInfo callbackInfo, EventType eventType,EventDirection eventDirection,String msg) {
        super(callbackInfo,eventType,eventDirection);
        this.message = msg;
    }
    public String getMessage() {
        return this.message;
    }
}
