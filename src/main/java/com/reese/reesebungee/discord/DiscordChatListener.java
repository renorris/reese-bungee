package com.reese.reesebungee.discord;

import com.reese.reesebungee.ReeseBungee;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.awt.*;
import java.util.Collection;

public class DiscordChatListener extends ListenerAdapter {

    ReeseBungee reeseBungee;

    public DiscordChatListener(ReeseBungee reeseBungee) {
        this.reeseBungee = reeseBungee;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getChannel().getIdLong() != this.reeseBungee.discord.mainChannelID || event.getAuthor().equals(this.reeseBungee.discord.jda.getSelfUser())) {
            return;
        }

        if (event.getMessage().getContentStripped().equals("ca!online")) {
            int currentPlayerCount = this.reeseBungee.getProxy().getOnlineCount();
            Collection<ProxiedPlayer> players = this.reeseBungee.getProxy().getPlayers();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(currentPlayerCount + " players connected");
            embed.setColor(Color.RED);

            if (currentPlayerCount > 0) {
                embed.setColor(Color.GREEN);
                StringBuilder playerList = new StringBuilder();
                for (ProxiedPlayer player : players) {
                    playerList.append(player.getDisplayName() + "\n");
                }
                MessageEmbed.Field field = new MessageEmbed.Field("List", playerList.toString(), false);
                embed.addField(field);
            }
            event.getTextChannel().sendMessage(embed.build()).queue();
        }
        else {
            // not a command -> forward to minecraft
            StringBuilder str = new StringBuilder(String.valueOf(ChatColor.DARK_PURPLE));
            str.append("[Discord] ");
            str.append(ChatColor.RESET);
            str.append(ChatColor.GRAY);
            str.append("<");
            str.append(ChatColor.WHITE);
            str.append(event.getAuthor().getName());
            str.append(ChatColor.GRAY);
            str.append("> ");
            str.append(ChatColor.WHITE);
            str.append(event.getMessage().getContentStripped());
            this.reeseBungee.broadcastToPlayers(str.toString());
        }
    }

}
