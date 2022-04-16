package de.drolpi.skywars.scoreboard;

import org.bukkit.entity.Player;

public interface CustomScoreboard {

    /**
     * Updates the scoreboard
     */
    void update();

    /**
     * Updates the title
     */
    void updateTitle();

    /**
     * Determine if the scoreboard is shown for the player
     *
     * @return activated
     */
    boolean isShown();

    /**
     * Returns the holder of this scoreboard
     *
     * @return holder
     */
    Player getPlayer();

}