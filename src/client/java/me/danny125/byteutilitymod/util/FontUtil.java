package me.danny125.byteutilitymod.util;

import me.danny125.byteutilitymod.mixin.client.IdentifierAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontUtil {
    public static Font loadCustomFont() {
        // Create the identifier for the custom font file in your mod's assets
        Identifier fontIdentifier = IdentifierAccessor.createIdentifier("byte-utility-mod", "font/custom_font.ttf");

        // Get the ResourceManager from the Minecraft client to load the font
        ResourceManager resourceManager = MinecraftClient.getInstance().getResourceManager();

        try {
            // Get the resource (font file) from the ResourceManager
            Resource resource = resourceManager.getResource(fontIdentifier).orElseThrow(() -> new IOException("Font not found"));

            // Open the font file's input stream
            try (InputStream fontStream = resource.getInputStream()) {
                // Load the font and set the size (deriving a 24px size font in this case)
                return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(24f);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Return a default font if loading fails
            return new Font("Arial", Font.PLAIN, 24);
        }
    }
}