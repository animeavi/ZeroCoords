package io.github.animeavi.zerocoords.events;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

import io.github.animeavi.zerocoords.ZC;

public class PlayerEvent implements Listener {
    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    private static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST,
            BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (ZC.enabledWorlds.contains(event.getPlayer().getWorld().getName())) {
            Player player = event.getPlayer();
            if (ZC.plugin.playerEnabledCoords(player)) {
                Location loc = event.getPlayer().getLocation();
                String defaultFormat = "&6&l{direction}&r&6 | &lX&r&6: {coordX} / &lY&r&6: {coordY} / &lZ&r&6: {coordZ}";
                String coords = formatCoords(ZC.config.getString("coords-format", defaultFormat), loc);

                ActionBarAPI.sendActionBar(event.getPlayer(), coords);
            }
        }

    }

    private BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections)
            return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();

        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }

    private String formatCoords(String format, Location loc) {
        BlockFace bFace = yawToFace(loc.getYaw(), true);
        String X = String.valueOf(loc.getBlockX());
        String Y = String.valueOf(loc.getBlockY());
        String Z = String.valueOf(loc.getBlockZ());
        String coords = format.replace("{direction}", bFace.toString().replace("_", " ")).replace("{coordX}", X).replace("{coordY}", Y)
                .replace("{coordZ}", Z).replace("&", "§");

        return coords;
    }
}
