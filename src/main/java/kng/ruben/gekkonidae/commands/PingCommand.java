package kng.ruben.gekkonidae.commands;

import kng.ruben.gekkonidae.commands.logic.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends Command {

    public PingCommand(String name) {
        super(name);
    }

    @Override
    protected boolean execute(MessageReceivedEvent event, String[] args) {
        long start = System.currentTimeMillis();

        event.getChannel().sendMessage("Calculating ping...").queue(response -> response.editMessageFormat("Ping: %d",
                System.currentTimeMillis() - start).queue());

        return true;
    }
}
