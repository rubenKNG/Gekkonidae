package kng.ruben.gekkonidae.music.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kng.ruben.gekkonidae.commands.logic.Command;
import kng.ruben.gekkonidae.music.MusicManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class QueueCommand extends Command {

    public QueueCommand(String name) {
        super(name);
    }

    @Override
    protected boolean execute(MessageReceivedEvent event, String[] args) {
        List<AudioTrack> tracks = MusicManager.getOrDefault(event.getGuild()).getQueue();

        if (tracks.size() == 0) {
            event.getChannel().sendMessage("No tracks in queue").queue();
            return false;
        }

        var messageAction = event.getChannel().sendMessage("**Tracks in queue:**\n");
        int amount = Math.min(tracks.size(), 20);

        for (int i = 0; i < amount; i++) {
            var current = tracks.get(i);
            var info = current.getInfo();

            messageAction.append(i + 1 + ": " + info.title + " [" + info.author + "]\n");
        }
        if (tracks.size() > amount)
            messageAction.append("\nand " + (tracks.size() - amount) + " more.");

        messageAction.queue();

        return true;
    }
}
