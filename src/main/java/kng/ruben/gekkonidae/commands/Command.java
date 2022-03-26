package kng.ruben.gekkonidae.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public abstract class Command {

    private String name;

    public Command(String name) {
        this.name = name;
    }

    abstract boolean execute(MessageReceivedEvent event, String[] args);


     public List<String> getAliases() {
        return List.of();
    }

    public String getName() {
        return name;
    }
}
