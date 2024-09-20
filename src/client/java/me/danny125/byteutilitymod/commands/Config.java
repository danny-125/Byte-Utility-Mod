package me.danny125.byteutilitymod.commands;

import me.danny125.byteutilitymod.Initialize;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

public class Config extends Command{
    public Config() {
        super("config","Save/load configs","#conifg <load/save>");
    }

    @Override
    public void runCommand(String msg) {
        super.runCommand(command);
        String[] args = msg.split(" ");
        if(!(args.length == 2)){
            MinecraftClient.getInstance().player.sendMessage(Text.literal("§5Usage:"));
            MinecraftClient.getInstance().player.sendMessage(Text.literal("§5"+ this.usage));
            return;
        }
        if(!args[1].equals("load") && !args[1].equals("save")){
            MinecraftClient.getInstance().player.sendMessage(Text.literal("§5Usage:"));
            MinecraftClient.getInstance().player.sendMessage(Text.literal("§5" + this.usage));
            return;
        }
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        GLFW.glfwIconifyWindow(windowHandle);

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if(args[1].equals("load")){
                Initialize.loadConfig(selectedFile.getAbsolutePath());
                MinecraftClient.getInstance().player.sendMessage(Text.literal("§5Loaded configuration file!"));
                GLFW.glfwRestoreWindow(windowHandle);

            }else if(args[1].equals("save")){
                Initialize.saveConfig(selectedFile.getAbsolutePath());
                MinecraftClient.getInstance().player.sendMessage(Text.literal("§5Saved configuration file!"));
                GLFW.glfwRestoreWindow(windowHandle);
            }else {
                MinecraftClient.getInstance().player.sendMessage(Text.literal("§5Error loading configuration file..."));
                GLFW.glfwRestoreWindow(windowHandle);
            }
            }
    }
}
