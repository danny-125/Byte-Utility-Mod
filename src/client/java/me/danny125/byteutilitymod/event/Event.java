package me.danny125.byteutilitymod.event;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Event {
    public boolean cancelled;
    public EventType eventType;
    public EventDirection eventDirection;
    public CallbackInfo ci;

    public Event(CallbackInfo ci, EventType eventType, EventDirection eventDirection) {
        this.ci = ci;
        this.eventType = eventType;
        this.eventDirection = eventDirection;
        this.cancelled = false;
    }

    public boolean isCancelled(){
        return this.cancelled;
    }
    public EventType getEventType(){
        return this.eventType;
    }
    public EventDirection getEventDirection(){
        return this.eventDirection;
    }
    public void cancel(boolean cancelci){
        if(ci.isCancellable() && cancelci) {
            this.ci.cancel();
        }
        this.cancelled = true;
    }
}
