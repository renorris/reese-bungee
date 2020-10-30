package com.reese.reesebungee.discord;

import com.reese.reesebungee.ReeseBungee;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;

public class DiscordChatListener extends ListenerAdapter {

    ReeseBungee reeseBungee;

    public DiscordChatListener(ReeseBungee reeseBungee) {
        this.reeseBungee = reeseBungee;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getIdLong() == this.reeseBungee.discord.mainChannelID && !event.getAuthor().equals(this.reeseBungee.discord.jda.getSelfUser())) {
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
