package com.reese.reesebungee.discord;

import com.reese.reesebungee.ReeseBungee;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;

import javax.security.auth.login.LoginException;

public class Discord {

    ReeseBungee reeseBungee;
    long statusFeedChannelID;
    long mcChatChannelID;
    JDA jda;

    public Discord(ReeseBungee reeseBungee) {
        this.reeseBungee = reeseBungee;
        this.statusFeedChannelID = this.reeseBungee.config.getLong("status-feed");
        this.mcChatChannelID = this.reeseBungee.config.getLong("chat-channel");

        this.reeseBungee.getLogger().info("Connecting to discord...");
        try {
            this.jda = JDABuilder.createDefault(this.reeseBungee.config.getString("bot-token")).build().awaitReady();
            this.jda.addEventListener(new DiscordChatListener(this.reeseBungee));
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
        this.reeseBungee.getLogger().info("Discord connection established.");
        this.jda.getPresence().setActivity(Activity.playing("canetwork.xyz"));
    }

    public void playerLogin(String name) {
        this.jda.getTextChannelById(this.statusFeedChannelID).sendMessage(Format.timestamp() + " " + Format.bold(name) + " connected").queue();
    }

    public void playerServerConnect(String playerName, String serverName) {
        this.jda.getTextChannelById(this.statusFeedChannelID).sendMessage(Format.timestamp() + " " + Format.bold(playerName) + " joined " + Format.bold(serverName)).queue();
    }

    public void playerLeave(String name) {
        this.jda.getTextChannelById(this.statusFeedChannelID).sendMessage(Format.timestamp() + " " + Format.bold(name) + " disconnected").queue();
    }

    public void sendChatMessage(String str) {
        MessageBuilder msg = new MessageBuilder();
        msg.append(str);
        this.jda.getTextChannelById(this.mcChatChannelID).sendMessage(msg.build()).queue();
    }
}
