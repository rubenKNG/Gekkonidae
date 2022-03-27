package kng.ruben.gekkonidae.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    public static final Map<Guild, MusicManager> MANAGERS = new HashMap<>();
    private static final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    private final Guild guild;
    private final TrackScheduler scheduler;
    private AudioPlayer player;
    private boolean open;

    static {
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
    }

    public MusicManager(@NotNull Guild guild) {
        this.guild = guild;
        this.player = audioPlayerManager.createPlayer();
        this.scheduler = new TrackScheduler(this.player);
        this.player.addListener(this.scheduler);
        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(this.player));
    }

    public void open(AudioChannel channel) {
        guild.getAudioManager().openAudioConnection(channel);
        open = true;
    }

    public void close() {
        guild.getAudioManager().closeAudioConnection();
        open = false;
    }

    public void addToQueue(@NotNull TextChannel channel, @NotNull String url) {
        var musicManager = getOrDefault(guild);

        audioPlayerManager.loadItemOrdered(musicManager, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);
                channel.sendMessage("The song has successfully been added to the queue").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                playlist.getTracks().forEach(musicManager.scheduler::queue);
                channel.sendMessage("Tracks from playlist has been added to the queue.").queue();
            }

            @Override
            public void noMatches() {
                channel.sendMessage("There were nothing found.").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Something went wrong loading this song. Try again later.").queue();
            }
        });
    }

    public void skip() {
        getOrDefault(this.guild).scheduler.playNextTrack();
    }

    public ArrayList<AudioTrack> getQueue() {
        return new ArrayList<>(this.scheduler.getQueue());
    }


    public static MusicManager getOrDefault(@NotNull Guild guild) {
        if (MANAGERS.containsKey(guild)) return MANAGERS.get(guild);

        var musicManager = new MusicManager(guild);

        MANAGERS.put(guild, musicManager);

        return musicManager;
    }

    public boolean isOpen() {
        return open;
    }

    public Guild getGuild() {
        return guild;
    }
}
