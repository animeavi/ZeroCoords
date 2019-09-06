package io.github.animeavi.zerocoords.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.animeavi.zerocoords.ZC;
import net.md_5.bungee.api.ChatColor;

public class ReloadConfig implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        ZC.updateValues();

        String msg = ChatColor.translateAlternateColorCodes('&',
                ZC.plugin.getConfig().getString("config-reloaded-message", "&2ZeroCoords configuration reloaded!"));

        ZC.plugin.getLogger().info(ChatColor.stripColor(msg));
        sender.sendMessage(msg);

        return true;
    }

}
