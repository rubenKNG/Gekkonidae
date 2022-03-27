package kng.ruben.gekkonidae.music.commands;

import kng.ruben.gekkonidae.commands.logic.Command;
import kng.ruben.gekkonidae.music.MusicManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PlayCommand extends Command {

    public PlayCommand(String name) {
        super(name);
    }

    @Override
    protected boolean execute(MessageReceivedEvent event, String[] args) {
        if (args.length == 0) {
            event.getChannel().sendMessage("Please enter a track url").queue();
            return false;
        } else if (!event.getMember().getVoiceState().inAudioChannel()) {
            event.getChannel().sendMessage("You are not in a channel").queue();
            return false;
        }
        var trackUrl = args[0];

        var musicManager = MusicManager.getOrDefault(event.getGuild());
        if (!musicManager.isOpen())
            musicManager.open(event.getMember().getVoiceState().getChannel());

        musicManager.play(event.getTextChannel(), trackUrl);

        return true;
    }
}
