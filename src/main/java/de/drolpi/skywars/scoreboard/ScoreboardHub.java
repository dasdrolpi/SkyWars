package de.drolpi.skywars.scoreboard;

import de.drolpi.skywars.scoreboard.creator.ScoreboardCreator;
import de.drolpi.skywars.scoreboard.creator.ScoreboardCreatorManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ScoreboardHub {

    @Nullable
    CustomScoreboard showScoreboard(Player player, String scoreboardName);

    void hideScoreboard(Player player);

    @Nullable
    CustomScoreboard getDisplayedScoreboard(Player player);

    @Nullable
    CustomScoreboard getScoreboard(Player player, String scoreboardName);

    Collection<CustomScoreboard> getScoreboards(String scoreboardName);

    void destroyScoreBoardsOfPlayer(Player player);

    ScoreboardCreator getCreator();

    static ScoreboardHub create(JavaPlugin javaPlugin) {
        return new ScoreboardManager(javaPlugin, new ScoreboardCreatorManager());
    }

}