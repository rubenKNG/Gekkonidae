package kng.ruben.gekkonidae.music.commands;

import kng.ruben.gekkonidae.commands.logic.Command;
import kng.ruben.gekkonidae.music.MusicManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class JoinCommand extends Command {

    public JoinCommand(String name) {
        super(name);
    }

    @Override
    protected boolean execute(MessageReceivedEvent event, String[] args) {
        var channel = event.getChannel();

        var member = event.getMember();

        if (!member.getVoiceState().inAudioChannel()) {
            channel.sendMessage("You have to be in a voice channel!").queue();
            return false;
        }

        var self = event.getGuild().getSelfMember();
        var voiceState = self.getVoiceState();

        if (voiceState.inAudioChannel()) {
            channel.sendMessage("Already connected to a voice channel").queue();
            return false;
        }
        var audioManager = event.getGuild().getAudioManager();
        var audioChannel = member.getVoiceState().getChannel();

        channel.sendMessage("Connecting to " + audioChannel.getName() + ".").queue();
        MusicManager.getOrDefault(audioManager.getGuild()).open(audioChannel);

        return true;
    }
}
