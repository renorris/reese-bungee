package com.reese.reesebungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Events implements Listener {

    ReeseBungee reeseBungee;

    public Events(ReeseBungee reeseBungee) {
        this.reeseBungee = reeseBungee;
    }

    @EventHandler
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
        broadcastToPlayers(msg.toString());
        this.reeseBungee.discord.playerServerConnect(playerName, serverName);
    }

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
        broadcastToPlayers(msg.toString());
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
        broadcastToPlayers(msg.toString());
        this.reeseBungee.discord.playerLogin(playerName);
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!event.isCommand()) {
            ProxiedPlayer playerSender = (ProxiedPlayer) event.getSender();
            StringBuilder msg = new StringBuilder();
            msg.append(ChatColor.GRAY);
            msg.append("[");
            msg.append(ChatColor.GREEN);
            msg.append(playerSender.getServer().getInfo().getName());
            msg.append(ChatColor.RESET);
            msg.append(ChatColor.GRAY);
            msg.append("] ");
            msg.append("<");
            msg.append(ChatColor.WHITE);
            msg.append(playerSender.getName());
            msg.append(ChatColor.GRAY);
            msg.append("> ");
            msg.append(ChatColor.RESET);
            msg.append(event.getMessage());
            broadcastToPlayers(msg.toString());
            event.setCancelled(true);
        }
    }

    public void broadcastToPlayers(String msg) {
        TextComponent textComponent = new TextComponent(msg);
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            p.sendMessage(ChatMessageType.CHAT, textComponent);
        }
    }
}
