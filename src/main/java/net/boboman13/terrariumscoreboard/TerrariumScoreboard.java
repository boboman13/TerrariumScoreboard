package net.boboman13.terrariumscoreboard;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.boboman13.terrariumscoreboard.listeners.KillListener;
import net.boboman13.terrariumscoreboard.listeners.MoveListener;
import net.boboman13.terrariumscoreboard.scoreboard.ScoreboardWrapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashSet;

/**
 * @author boboman13
 */
public class TerrariumScoreboard extends JavaPlugin {

    private boolean verbose;

    /**
     * The Bukkit Scoreboard.
     */
    private ScoreboardWrapper sb;


    /**
     * The players inside the terrarium.
     */
    private HashSet<String> players = new HashSet<String>();

    /**
     * MoveListener instance.
     */
    private MoveListener moveListener;

    /**
     * KillListener instance.
     */
    private KillListener killListener;

    /**
     * Called when Craftbukkit instantiates the plugin.
     */
    @Override
    public void onEnable() {
        // Configuration stuff
        this.saveDefaultConfig();
        this.verbose = this.getConfig().getBoolean("settings.verbose");

        // Scoreboard
        this.sb = new ScoreboardWrapper(this);

        // Listeners
        this.moveListener = new MoveListener(this);
        this.getServer().getPluginManager().registerEvents(this.moveListener, this);

        this.killListener = new KillListener(this);
        this.getServer().getPluginManager().registerEvents(this.killListener, this);

        this.log("TerrariumScoreboard has been enabled!");
    }

    /**
     * Called when Craftbukkit disables the plugin.
     */
    @Override
    public void onDisable() {
        this.killListener = null;
        this.moveListener = null;
    }

    /**
     * Gets the ScoreboardWrapper.
     * @return
     */
    public ScoreboardWrapper getSB() {
        return this.sb;
    }

    /**
     * Logs a message to the console. Only messages if it is in verbose mode.
     * @param log
     */
    public void logVerbose(String log) {
        if(this.verbose) this.getLogger().info("[V] " + log);
    }

    /**
     * Logs an error to the console.
     * @param log
     */
    public void logError(String log) {
        this.getLogger().info("[ERROR] " + log);
    }

    /**
     * Logs a message to the console.
     * @param log
     */
    public void log(String log) {
        this.getLogger().info(log);
    }

    /**
     * Gets the WorldGuardPlugin.
     * @return WorldGuardPlugin
     */
    public WorldGuardPlugin getWG() {
        Plugin pg = this.getServer().getPluginManager().getPlugin("WorldGuard");

        if(pg == null || !(pg instanceof WorldGuardPlugin)) {
            this.setEnabled(false);
            this.logError("WorldGuard not found!");

            return null;
        }

        if(!pg.isEnabled()) {
            this.setEnabled(false);
            this.logError("WorldGuard is disabled.");

            return null;
        }

        return (WorldGuardPlugin) pg;
    }

}
