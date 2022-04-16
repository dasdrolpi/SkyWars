package de.drolpi.skywars.scoreboard;

import de.drolpi.skywars.runnable.CatchingRunnable;
import de.drolpi.skywars.scoreboard.creator.AbstractScoreboardConfiguration;
import de.drolpi.skywars.scoreboard.creator.ScoreboardInformation;
import de.drolpi.skywars.scoreboard.line.LineParser;
import de.drolpi.skywars.scoreboard.result.ScoreboardResult;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class ScoreboardImpl implements CustomScoreboard {

    private final JavaPlugin javaPlugin;
    private final Player player;
    private final ScoreboardInformation scoreBoardInformation;
    private final ScoreboardManager service;
    private final AbstractScoreboardConfiguration configuration;
    private boolean shown;
    private Scoreboard scoreboard;
    private Objective objective;

    public ScoreboardImpl(JavaPlugin javaPlugin, Player player, ScoreboardInformation scoreboardInformation, ScoreboardManager service) {
        this.javaPlugin = javaPlugin;
        this.player = player;
        this.scoreBoardInformation = scoreboardInformation;
        this.service = service;
        this.configuration = scoreboardInformation.getConfiguration();
    }

    public void create() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewObjective("board", "dummy").setDisplaySlot(DisplaySlot.SIDEBAR);
        objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
    }

    public void show() {
        if (shown) return;
        shown = true;

        Bukkit.getScheduler().runTask(javaPlugin, new CatchingRunnable(() -> {
            player.setScoreboard(scoreboard);
            update();
        }));
    }

    public void hide() {
        if (!shown) return;
        shown = false;

        if (player.isOnline()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }

        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }
    }

    @Override
    public boolean isShown() {
        return shown;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        if (!player.isOnline()) {
            hide();
            return;
        }

        updateTitle();

        List<String> lines = configuration.getLinesHandler().apply(new ScoreboardResult(this, player));

        scoreboard.getEntries().forEach(scoreboard::resetScores);
        scoreboard.getTeams().forEach(team -> {
            team.unregister();
        });

        for (String line : lines) {
            int position = lines.size() - lines.indexOf(line);
            setLine(position, line);
        }

    }

    @Override
    public void updateTitle() {
        if (!player.isOnline()) {
            hide();
            return;
        }

        String handlerTitle = configuration.getTitleHandler().apply(new ScoreboardResult(this, player));
        String finalTitle = handlerTitle != null ? handlerTitle : ChatColor.BOLD.toString();
        objective.setDisplayName(finalTitle);
    }

    private void setLine(int position, String line) {
        ChatColor chatColor = ChatColor.values()[position];

        Team team = scoreboard.getTeam(chatColor.toString());
        String entry = chatColor + ChatColor.RESET.toString();

        if (team == null) {
            team = scoreboard.registerNewTeam(chatColor.toString());
            team.addEntry(entry);
        }

        String[] parsedText = LineParser.parseText(line);

        team.setPrefix(parsedText[0]);
        team.setSuffix(parsedText[1]);

        objective.getScore(entry).setScore(position);
    }

    public Objective getObjective() {
        return objective;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}