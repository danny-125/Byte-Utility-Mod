package me.danny125.byteutilitymod.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.util.Identifier;

@Mixin(Identifier.class)
public interface IdentifierAccessor {
    @Invoker("<init>")
    static Identifier createIdentifier(String namespace, String path) {
        throw new AssertionError();
    }
}
