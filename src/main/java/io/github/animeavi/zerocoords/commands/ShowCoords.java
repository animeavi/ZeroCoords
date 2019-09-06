package io.github.animeavi.zerocoords.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.animeavi.zerocoords.ZC;

public class ShowCoords implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ZC.plugin.toggleEnabledCoords(player);
        }
        return true;
    }

}
