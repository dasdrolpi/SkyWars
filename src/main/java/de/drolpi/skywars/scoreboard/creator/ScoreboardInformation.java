package de.drolpi.skywars.scoreboard.creator;

import lombok.Data;

/**
 * @author drolpi / Lars Nippert
 * @project Natrox-API
 * @date Date: 04.08.2021
 * @time Time: 21:24
 */

@Data
public class ScoreboardInformation {

    private final String scoreboardName;
    private final AbstractScoreboardConfiguration configuration;

}