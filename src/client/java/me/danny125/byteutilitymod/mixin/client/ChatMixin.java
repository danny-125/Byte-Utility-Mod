package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.event.ChatEvent;
import me.danny125.byteutilitymod.event.EventDirection;
import me.danny125.byteutilitymod.event.EventType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

@Mixin(ChatScreen.class)
public abstract class ChatMixin extends Screen
{
    @Shadow
    protected TextFieldWidget chatField;

    private ChatMixin(Text title)
    {
        super(title);
    }

    @Inject(at = @At("HEAD"),
            method = "sendMessage(Ljava/lang/String;Z)V",
            cancellable = true)
    public void onSendMessage(String message, boolean addToHistory,
                              CallbackInfo ci)
    {
        // Ignore empty messages just like vanilla
        if((message = normalize(message)).isEmpty())
            return;

        // Create and fire the chat output event
        ChatEvent event = new ChatEvent(ci, EventType.PRE, EventDirection.OUTGOING, message);
        BYTE.INSTANCE.onEvent(event);

        // If the event hasn't been modified or cancelled,
        // let the vanilla method handle the message
        boolean cancelled = event.isCancelled();
        if(!cancelled)
            return;

        // Otherwise, cancel the vanilla method and handle the message here
        ci.cancel();

        // Add the message to history, even if it was cancelled
        // Otherwise the up/down arrows won't work correctly
        String newMessage = event.getMessage();
        if(addToHistory)
            client.inGameHud.getChatHud().addToMessageHistory(newMessage);

        // If the event isn't cancelled, send the modified message
        if(!cancelled)
            if(newMessage.startsWith("/"))
                client.player.networkHandler
                        .sendChatCommand(newMessage.substring(1));
            else
                client.player.networkHandler.sendChatMessage(newMessage);
    }

    @Shadow
    public abstract String normalize(String chatText);
}