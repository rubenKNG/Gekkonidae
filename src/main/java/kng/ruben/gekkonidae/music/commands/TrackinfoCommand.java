package kng.ruben.gekkonidae.music.commands;

import kng.ruben.gekkonidae.commands.logic.Command;
import kng.ruben.gekkonidae.music.MusicManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class TrackinfoCommand extends Command {

    public TrackinfoCommand(String name) {
        super(name);
    }

    @Override
    protected boolean execute(MessageReceivedEvent event, String[] args) {
        var playingTrack = MusicManager.getOrDefault(event.getGuild()).getCurrentTrack();

        if (playingTrack == null) {
            event.getChannel().sendMessage("There is no track currently playing.").queue();
            return false;
        }
        var trackInfo = playingTrack.getInfo();

        event.getTextChannel().sendMessageFormat("Now playing `%s` by `%s` (Link: <%s>)", trackInfo.title, trackInfo.author, trackInfo.uri).queue();

        return true;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("nowplaying, songinfo");
    }
}
