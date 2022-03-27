package kng.ruben.gekkonidae.music.commands;

import kng.ruben.gekkonidae.commands.logic.Command;
import kng.ruben.gekkonidae.music.MusicManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SkipCommand extends Command {

    public SkipCommand(String name) {
        super(name);
    }

    @Override
    protected boolean execute(MessageReceivedEvent event, String[] args) {
        var guild = event.getGuild();
        var musicManager = MusicManager.getOrDefault(guild);

        if (!musicManager.isOpen()) {
            event.getTextChannel().sendMessage("Bot is not connected to a voice channel.");
            return false;
        }
        musicManager.skip();
        event.getChannel().sendMessage("Skipped track.");

        return true;
    }
}
