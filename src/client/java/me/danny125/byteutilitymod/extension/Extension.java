package me.danny125.byteutilitymod.extension;

import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.commands.Command;
import me.danny125.byteutilitymod.modules.Module;

import java.util.concurrent.CopyOnWriteArrayList;

public class Extension {

    public String name;
    public String description;
    public CopyOnWriteArrayList<Module> modules;
    public CopyOnWriteArrayList<Command> commands;

    public Extension(String name, String description, CopyOnWriteArrayList<Module> modules, CopyOnWriteArrayList<Command> commands) {
        this.name = name;
        this.description = description;
        this.modules = modules;
        this.commands = commands;
    }
}
