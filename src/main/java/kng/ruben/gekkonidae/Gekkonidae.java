package kng.ruben.gekkonidae;

import kng.ruben.gekkonidae.commands.logic.CommandManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

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
        jdaBuilder.enableCache(CacheFlag.VOICE_STATE);
        jdaBuilder.setActivity(Activity.listening("to commands"));

        jdaBuilder.build();
    }

}
