package kng.ruben.gekkonidae.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TestCommand extends Command {

    public TestCommand(String name) {
        super(name);
    }

    @Override
    boolean execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("ddwwd").queue();

        return true;
    }

}
