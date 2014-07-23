package net.boboman13.terrariumscoreboard.scoreboard;

import net.boboman13.terrariumscoreboard.TerrariumScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Objective;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.util.HashMap;

/**
 * @author boboman13
 */
public class ScoreboardWrapper {

    private TerrariumScoreboard plugin;

    private Team team;
    private Scoreboard scoreboard;

    private Objective obj;

    private HashMap<String, Score> scores = new HashMap<String, Score>();
    private HashMap<String, Integer> numscores = new HashMap<String, Integer>();

    /**
     * Instantiates the ScoreboardWrapper.
     * @param plugin
     */
    public ScoreboardWrapper(TerrariumScoreboard plugin) {
        this.plugin = plugin;

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.team = this.scoreboard.registerNewTeam("Players");
        this.obj = scoreboard.registerNewObjective("counter", "dummy");

        this.obj.setDisplayName("Kills");
        this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        plugin.logVerbose("Instantiated ScoreboardWrapper.");
    }

    /**
     * Adds a player to the scoreboard.
     * @param player
     */
    public void addPlayer(Player player) {
        plugin.logVerbose("Adding a player " + player.getName());

        team.addPlayer(player);
        numscores.put(player.getName(), 0);

        Score score = obj.getScore(player);
        score.setScore(0);
        scores.put(player.getName(), score);

        player.setScoreboard(this.scoreboard);
    }

    /**
     * Removes a player from the scoreboard.
     * @param player
     */
    public void removePlayer(Player player) {
        plugin.logVerbose("Removing player " + player.getName());

        team.removePlayer(player);
        numscores.remove(player.getName());
        scores.remove(player.getName());

        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    /**
     * Simple function to tell whether or not a player is inside the Terrarium.
     * @param player
     * @return
     */
    public boolean isInside(Player player) {
        return numscores.containsKey(player.getName());
    }

    /**
     * Adds a kill to the player.
     * @param player
     */
    public void addKill(Player player) {
        plugin.logVerbose("Adding a kill to player " + player.getName());

        if(scores.containsKey(player.getName())) {
            Integer score = numscores.get(player.getName());
            score++;

            numscores.put(player.getName(), score);
            scores.get(player.getName()).setScore(score);
        }
    }

}
