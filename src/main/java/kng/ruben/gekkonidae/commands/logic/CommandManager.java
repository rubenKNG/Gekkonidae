package kng.ruben.gekkonidae.commands.logic;

import kng.ruben.gekkonidae.Gekkonidae;
import kng.ruben.gekkonidae.commands.ChatlogCommand;
import kng.ruben.gekkonidae.commands.PingCommand;
import kng.ruben.gekkonidae.music.commands.JoinCommand;
import kng.ruben.gekkonidae.music.commands.LeaveCommand;
import kng.ruben.gekkonidae.music.commands.PlayCommand;
import kng.ruben.gekkonidae.music.commands.SkipCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private final List<Command> commands;

    public CommandManager() {
        this.commands = new ArrayList<>();
        initCommands();
    }

    private void initCommands() {
        commands.add(new JoinCommand("join"));
        commands.add(new LeaveCommand("leave"));
        commands.add(new PingCommand("ping"));
        commands.add(new ChatlogCommand("chatlog"));
        commands.add(new PlayCommand("play"));
        commands.add(new SkipCommand("skip"));
    }

    private void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;

        var rawMessage = event.getMessage().getContentRaw();

        if (!rawMessage.startsWith(Gekkonidae.COMMAND_PREFIX)) return;

        String[] split = rawMessage.replaceFirst(Gekkonidae.COMMAND_PREFIX, "").split("\\s+");

        Command command = getCommand(split[0]);

        if (command != null)
            command.execute(event, Arrays.copyOfRange(split, 1, split.length));
    }

    @Nullable
    private Command getCommand(@NotNull String search) {
        return this.commands.stream().filter(current -> current.getName().equals(search.toLowerCase()) || current.getAliases().contains(search.toLowerCase())).findFirst().orElse(null);
    }
}
