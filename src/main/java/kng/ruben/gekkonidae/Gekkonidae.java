package kng.ruben.gekkonidae;

import kng.ruben.gekkonidae.commands.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class Gekkonidae {

    public static final String COMMAND_PREFIX = "!";

    public static void main(String[] args) throws LoginException {
        if (args.length == 0) {
            System.out.println("No token provided");
            System.exit(1);
        }

        var jdaBuilder = JDABuilder.createDefault(args[0]);
        jdaBuilder.setStatus(OnlineStatus.ONLINE);

        jdaBuilder.addEventListeners(new CommandManager());

        jdaBuilder.build();

    }


}
