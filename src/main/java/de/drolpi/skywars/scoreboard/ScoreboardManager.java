package de.drolpi.skywars.scoreboard;

import de.drolpi.skywars.scoreboard.cache.ScoreboardCache;
import de.drolpi.skywars.scoreboard.creator.ScoreboardCreator;
import de.drolpi.skywars.scoreboard.creator.ScoreboardCreatorManager;
import de.drolpi.skywars.scoreboard.creator.ScoreboardInformation;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScoreboardManager implements ScoreboardHub {

    private final JavaPlugin javaPlugin;
    private final ScoreboardCreatorManager creator;

    private final List<ScoreboardCache> scoreboards;

    public ScoreboardManager(JavaPlugin javaPlugin, ScoreboardCreatorManager creator) {
        this.javaPlugin = javaPlugin;
        this.creator = creator;
        this.scoreboards = new ArrayList<>();
    }

    @Override
    public CustomScoreboard showScoreboard(Player player, String scoreboardName) {
        Optional<ScoreboardInformation> optional = creator.getScoreboards().stream().filter(it -> it.getScoreboardName().equalsIgnoreCase(scoreboardName)).findFirst();

        if (!optional.isPresent()) {
            return null;
        }

        hideScoreboard(player);

        ScoreboardInformation scoreBoardInformation = optional.get();
        ScoreboardImpl open = getScoreboard(player, scoreboardName);

        ScoreboardImpl scoreBoard = open != null ? open : createBukkitScoreboard(player, scoreBoardInformation);

        scoreBoard.show();

        player.setMetadata("current-scoreboard", new FixedMetadataValue(javaPlugin, scoreboardName));

        return scoreBoard;
    }

    private ScoreboardImpl createBukkitScoreboard(Player player, ScoreboardInformation scoreboardInformation) {
        ScoreboardImpl scoreBoard = new ScoreboardImpl(javaPlugin, player, scoreboardInformation, this);
        scoreBoard.create();

        scoreboards.add(new ScoreboardCache(scoreboardInformation.getScoreboardName(), player, scoreBoard));
        return scoreBoard;
    }

    @Override
    public void hideScoreboard(Player player) {
        ScoreboardImpl scoreboard = getDisplayedScoreboard(player);

        if (scoreboard == null) {
            return;
        }

        scoreboard.hide();
    }

    @Override
    public ScoreboardImpl getDisplayedScoreboard(Player player) {
        List<MetadataValue> values = player.getMetadata("current-scoreboard");

        if (values.isEmpty()) {
            return null;
        }

        MetadataValue metadata = values.get(0);

        if (metadata == null) {
            return null;
        }

        return getScoreboard(player, metadata.asString());
    }

    @Override
    public ScoreboardImpl getScoreboard(Player player, String scoreboardName) {
        Optional<ScoreboardCache> optional = scoreboards.stream().filter(it -> it.getPlayer().equals(player) && it.getScoreboardName().equalsIgnoreCase(scoreboardName)).findFirst();

        return optional.map(ScoreboardCache::getScoreboard).orElse(null);
    }

    @Override
    public Collection<CustomScoreboard> getScoreboards(String scoreboardName) {
        return scoreboards.stream().filter(it -> it.getScoreboardName().equalsIgnoreCase(scoreboardName)).map(ScoreboardCache::getScoreboard).collect(Collectors.toList());
    }

    @Override
    public void destroyScoreBoardsOfPlayer(Player player) {
        scoreboards.removeIf(it -> it.getPlayer() == player);
    }

    @Override
    public ScoreboardCreator getCreator() {
        return creator;
    }
}