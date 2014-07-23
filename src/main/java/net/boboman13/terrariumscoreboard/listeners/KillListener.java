package net.boboman13.terrariumscoreboard.listeners;

import net.boboman13.terrariumscoreboard.TerrariumScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @author boboman13
 */
public class KillListener implements Listener {

    private TerrariumScoreboard plugin;

    /**
     * Creates the KillListener.
     * @param plugin
     */
    public KillListener(TerrariumScoreboard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(EntityDamageByEntityEvent e) {
        // not our job
        if( !(e.getDamager() instanceof Player && e.getEntity() instanceof Player) ) {
            return;
        }

        // if the player died
        if(!(e.getDamage() >= ((Player) e.getEntity()).getHealth())) {
            return;
        }

        // if the killer is inside the terrarium
        if(plugin.getSB().isInside((Player) e.getDamager())) {
            plugin.getSB().addKill((Player) e.getDamager());
        }
    }

}
