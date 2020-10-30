package com.reese.reesebungee.discord;

import com.reese.reesebungee.ReeseBungee;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Discord {

    ReeseBungee reeseBungee;
    long mainChannelID;
    JDA jda;

    public Discord(ReeseBungee reeseBungee) {
        this.reeseBungee = reeseBungee;
        this.mainChannelID = this.reeseBungee.config.getLong("discord-channel-id");

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
        this.jda.getTextChannelById(this.mainChannelID).sendMessage("\uD83D\uDFE2 " + Format.bold(name) + " connected").queue();
    }

    public void playerServerConnect(String playerName, String serverName) {
        this.jda.getTextChannelById(this.mainChannelID).sendMessage( "➡ " + Format.bold(playerName) + " joined " + Format.bold(serverName)).queue();
    }

    public void playerLeave(String name) {
        this.jda.getTextChannelById(this.mainChannelID).sendMessage("\uD83D\uDD34 " + Format.bold(name) + " disconnected").queue();
    }

    public void playerChat(String name, String msg) {
        this.jda.getTextChannelById(this.mainChannelID).sendMessage("\uD83D\uDDE8 " + Format.bold(name) + ": " + msg).queue();
    }
}
