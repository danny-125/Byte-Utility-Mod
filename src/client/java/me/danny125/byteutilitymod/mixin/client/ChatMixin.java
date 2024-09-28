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
        if((message = normalize(message)).isEmpty())
            return;

        ChatEvent event = new ChatEvent(message);
        event.setCi(ci);
        BYTE.INSTANCE.onEvent(event);

        boolean cancelled = event.isCancelled();
        if(!cancelled)
            return;

        String newMessage = event.getMessage();
        if(addToHistory)
            client.inGameHud.getChatHud().addToMessageHistory(newMessage);

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