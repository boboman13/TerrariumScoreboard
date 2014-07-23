package net.boboman13.terrariumscoreboard.listeners;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.boboman13.terrariumscoreboard.TerrariumScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author boboman13
 */
public class MoveListener implements Listener {

    private TerrariumScoreboard plugin;

    /**
     * Instantiates the listener.
     * @param plugin The TerrariumScoreboard plugin.
     */
    public MoveListener(TerrariumScoreboard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();

        // really long, but just gets the region specified in the config.
        ProtectedRegion region = plugin.getWG().getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("arena.world"))).getRegion(plugin.getConfig().getString("arena.region"));

        // if entering an arena
        if(
                region.contains(to.getBlockX(), to.getBlockY(), to.getBlockZ()) &&
                !region.contains(from.getBlockX(), from.getBlockY(), from.getBlockZ())
                ) {
            plugin.getSB().addPlayer(e.getPlayer());
        // if leaving an arena
        } else if (
                !region.contains(to.getBlockX(), to.getBlockY(), to.getBlockZ()) &&
                region.contains(from.getBlockX(), from.getBlockY(), from.getBlockZ())
                ) {
            plugin.getSB().removePlayer(e.getPlayer());
        }

        // don't do anything else
    }

}
