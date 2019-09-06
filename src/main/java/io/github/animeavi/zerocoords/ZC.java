package io.github.animeavi.zerocoords;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.animeavi.zerocoords.commands.ReloadConfig;
import io.github.animeavi.zerocoords.commands.ShowCoords;
import io.github.animeavi.zerocoords.events.PlayerEvent;

public class ZC extends JavaPlugin {
    public static ZC plugin;
    public static FileConfiguration config;
    public static HashSet<String> enabledWorlds;
    private static HashMap<String, Boolean> enabledCoords = new HashMap<String, Boolean>();

    public void onEnable() {
        if (resolvePlugin("ActionBarAPI") != null) {
            plugin = this;
            plugin.createConfig();
            updateValues();
            this.getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
            getCommand("zcreload").setExecutor(new ReloadConfig());
            getCommand("showcoords").setExecutor(new ShowCoords());
        }
    }

    public boolean playerEnabledCoords(Player player) {
        String id = player.getUniqueId().toString();

        if (enabledCoords.get(id) != null) {
            return enabledCoords.get(id);
        }

        return false;
    }

    public void toggleEnabledCoords(Player player) {
        String id = player.getUniqueId().toString();
        enabledCoords.put(id, !playerEnabledCoords(player));
    }

    public void onDisable() {
        plugin = null;
    }

    @SuppressWarnings("unused")
    private void createConfig() {
        try {
            File file;
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdirs();
            }
            if (!(file = new File(this.getDataFolder(), "config.yml")).exists()) {
                this.getLogger().info("Configuration not found, creating!");
                this.saveDefaultConfig();
            } else {
                this.getLogger().info("Configuration found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateValues() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        enabledWorlds = getEnabledWorlds();
    }

    private static HashSet<String> getEnabledWorlds() {
        enabledWorlds = new HashSet<String>(config.getStringList("enabled-worlds"));

        if (enabledWorlds.isEmpty()) {
            enabledWorlds = new HashSet<String>();
            enabledWorlds.add("world");
            enabledWorlds.add("world_nether");
            enabledWorlds.add("world_the_end");
        }

        return enabledWorlds;
    }

    private Plugin resolvePlugin(String name) {
        Plugin temp = getServer().getPluginManager().getPlugin(name);

        if (temp == null) {
            return null;
        }

        return temp;
    }
}
