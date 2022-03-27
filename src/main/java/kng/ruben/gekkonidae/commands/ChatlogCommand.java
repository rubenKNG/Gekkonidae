package kng.ruben.gekkonidae.commands;

import kng.ruben.gekkonidae.commands.logic.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ChatlogCommand extends Command {

    public ChatlogCommand(String name) {
        super(name);
    }

    @Override
    protected boolean execute(MessageReceivedEvent event, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        event.getChannel().getHistory().retrievePast(50).complete().forEach(current -> {
            stringBuilder.append(current.getAuthor().getName() + ": " + current.getContentRaw());
            stringBuilder.append("\n");
        });
        try {
            event.getChannel().sendMessage(post(stringBuilder.toString(), true)).queue();
        } catch (IOException exception) {
            event.getChannel().sendMessage("Could not create chatlog. Try again later.").queue();
        }

        return true;
    }


    private String post(String text, boolean raw) throws IOException {
        byte[] postData = text.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        String requestURL = "https://hastebin.com/documents";
        URL url = new URL(requestURL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Hastebin Java Api");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);

        String response = null;
        DataOutputStream wr;
        try {
            wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.contains("\"key\"")) {
            response = response.substring(response.indexOf(":") + 2, response.length() - 2);

            String postURL = raw ? "https://hastebin.com/raw/" : "https://hastebin.com/";
            response = postURL + response;
        }

        return response;
    }


}
