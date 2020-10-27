package com.reese.reesebungee;

import com.reese.reesebungee.discord.Discord;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ReeseBungee extends Plugin {

    public Discord discord;
    public Configuration config;

    public ReeseBungee() throws IOException {
        setDefaultConfig();
        this.config = getConfig();
        this.discord = new Discord(this);
    }

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new Events(this));
    }

    private void setDefaultConfig() {
        if (!getDataFolder().exists()) {
            getLogger().info("Creating plugin directory");
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            getLogger().info("Creating default config.yml");
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastToPlayers(String msg) {
        TextComponent textComponent = new TextComponent(msg);
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            p.sendMessage(ChatMessageType.CHAT, textComponent);
        }
    }

    private Configuration getConfig() throws IOException {
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
    }

    private void saveConfig(Configuration configuration) throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(getDataFolder(), "config.yml"));
    }
}
