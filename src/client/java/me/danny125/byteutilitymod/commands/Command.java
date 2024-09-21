package me.danny125.byteutilitymod.commands;

public class Command {
    public String command;
    public String description;
    public String usage;
    public Command(String command, String description, String usage){
        this.command = command;
        this.description = description;
        this.usage = usage;
    }
    public String getCommand(){
        return this.command;
    }
    public String getDescription(){
        return this.description;
    }
    public String getUsage(){
        return this.usage;
    }
    public void runCommand(String msg){

    }
}
