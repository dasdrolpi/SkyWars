package de.drolpi.skywars.scoreboard.creator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author drolpi / Lars Nippert
 * @project Natrox-API
 * @date Date: 06.08.2021
 * @time Time: 17:53
 */

@Getter
public class ScoreboardCreatorManager implements ScoreboardCreator {

    private final List<ScoreboardInformation> scoreboards = new ArrayList<>();

    @Override
    public void createScoreboard(String scoreboardName, AbstractScoreboardConfiguration configuration) {
        configuration.configure();

        scoreboards.add(new ScoreboardInformation(scoreboardName, configuration));
    }
}