package com.reese.reesebungee;

import com.reese.reesebungee.discord.Format;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Events implements Listener {

    ReeseBungee reeseBungee;

    public Events(ReeseBungee reeseBungee) {
        this.reeseBungee = reeseBungee;
    }

    /*@EventHandler
    public void onServerConnected(ServerConnectedEvent event) {
        String playerName = event.getPlayer().getName();
        String serverName = event.getServer().getInfo().getName();
        StringBuilder msg = new StringBuilder();
        msg.append(ChatColor.BOLD);
        msg.append(ChatColor.YELLOW);
        msg.append(playerName);
        msg.append(ChatColor.RESET);
        msg.append(ChatColor.YELLOW);
        msg.append(" joined ");
        msg.append(ChatColor.GREEN);
        msg.append(serverName);
        this.reeseBungee.broadcastToPlayers(msg.toString());
        this.reeseBungee.discord.playerServerConnect(playerName, serverName);
    }*/

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        String playerName = event.getPlayer().getName();
        StringBuilder msg = new StringBuilder();
        msg.append(ChatColor.BOLD);
        msg.append(ChatColor.YELLOW);
        msg.append(playerName);
        msg.append(ChatColor.RESET);
        msg.append(ChatColor.YELLOW);
        msg.append(" disconnected");
        this.reeseBungee.broadcastToPlayers(msg.toString());
        this.reeseBungee.discord.playerLeave(playerName);
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        String playerName = event.getPlayer().getName();
        StringBuilder msg = new StringBuilder();
        msg.append(ChatColor.BOLD);
        msg.append(ChatColor.YELLOW);
        msg.append(playerName);
        msg.append(ChatColor.RESET);
        msg.append(ChatColor.YELLOW);
        msg.append(" connected");
        this.reeseBungee.broadcastToPlayers(msg.toString());
        this.reeseBungee.discord.playerLogin(playerName);
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!event.isCommand()) {
            ProxiedPlayer playerSender = (ProxiedPlayer) event.getSender();
            String serverName = playerSender.getServer().getInfo().getName();
            String playerName = playerSender.getName();

            StringBuilder msg = new StringBuilder();
            msg.append(ChatColor.GRAY);
            msg.append("[");
            msg.append(ChatColor.GREEN);
            msg.append(serverName);
            msg.append(ChatColor.RESET);
            msg.append(ChatColor.GRAY);
            msg.append("] ");
            msg.append("<");
            msg.append(ChatColor.WHITE);
            msg.append(playerName);
            msg.append(ChatColor.GRAY);
            msg.append("> ");
            msg.append(ChatColor.RESET);
            msg.append(event.getMessage());

            this.reeseBungee.broadcastToPlayers(msg.toString());
            this.reeseBungee.discord.playerChat(playerName, event.getMessage());

            event.setCancelled(true);
        }
    }
}
