package kng.ruben.gekkonidae.music.commands;

import kng.ruben.gekkonidae.commands.logic.Command;
import kng.ruben.gekkonidae.music.MusicManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class LeaveCommand extends Command {

    public LeaveCommand(String name) {
        super(name);
    }

    @Override
    protected boolean execute(MessageReceivedEvent event, String[] args) {
        var channel = event.getChannel();
        var audioManager = event.getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            channel.sendMessage("Bot is not connected to an audio channel.").queue();
            return false;
        }

        channel.sendMessage("Disconnecting from channel.").queue();
        MusicManager.getOrDefault(audioManager.getGuild()).close();
        return true;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("quit");
    }
}
