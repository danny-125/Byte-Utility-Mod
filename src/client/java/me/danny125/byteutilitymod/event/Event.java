package me.danny125.byteutilitymod.event;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Event {
    public boolean cancelled;
    public EventType eventType;
    public EventDirection eventDirection;
    public CallbackInfo ci;

    public Event() {
        this.cancelled = false;
    }

    public void setCi(CallbackInfo ci) {
        this.ci = ci;
    }
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    public void setEventDirection(EventDirection eventDirection) {
        this.eventDirection = eventDirection;
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
    public CallbackInfo getCi(){
        return this.ci;
    }
    public void cancel(){
        this.cancelled = true;
    }
}
