package me.danny125.byteutilitymod.event;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ChatEvent extends Event{
    public String message;
    public ChatEvent(String msg) {
        super();
        this.message = msg;
    }
    public String getMessage() {
        return this.message;
    }
}
