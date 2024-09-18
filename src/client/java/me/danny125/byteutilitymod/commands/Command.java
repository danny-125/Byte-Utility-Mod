package me.danny125.byteutilitymod.commands;

public class Command {
    public String command;
    public String description;
    public Command(String command, String description){
        this.command = command;
        this.description = description;
    }
    public String getCommand(){
        return this.command;
    }
    public String getDescription(){
        return this.description;
    }
    public void runCommand(){

    }
}
