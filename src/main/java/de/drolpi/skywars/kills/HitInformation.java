package de.drolpi.skywars.kills;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Contains player related data about the last damager and the last hit.
 *
 */
public class HitInformation {

    private final Player player;
    private Player lastDamager;
    private long lastDamagerTimeStamp;

    public HitInformation(Player player) {
        this.player = player;
    }

    /**
     * Sets the last damager and the timestamp of the hit.
     *
     * @param lastDamager The UUID of the player that hits the HitInformation owner.
     * @param lastDamagerTimeStamp The timestamp of the hit from the damager.
     */
    public void setLastDamager(Player lastDamager, long lastDamagerTimeStamp) {
        this.lastDamager = lastDamager;
        this.lastDamagerTimeStamp = lastDamagerTimeStamp;
    }

    /**
     * Returns, if applicable, a Killer object of the last damager of the player who owns the HitInformation,
     * if the hit is in a self-determined period of time.
     *
     * @param time The difference in which the hit of the last damager should have been located.
     * @param timeUnit The TimeUnit from the long time. {@link java.util.concurrent.TimeUnit}
     * @return An optional in which the last damager is if the hit is in a certain time period.
     */
    public Optional<Player> getLastDamager(long time, TimeUnit timeUnit) {
        long currentTimeStamp = System.currentTimeMillis() - timeUnit.toMillis(time);
        if (currentTimeStamp <= this.lastDamagerTimeStamp) {
            return Optional.of(lastDamager);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns the last damager of the player who owns the HitInformation.
     *
     * @return A Killer object with information form the player that hit the owner of the HitInformation at least.
     */
    public Player getLastDamager() {
        return lastDamager;
    }

    /**
     * Returns the time of the last hit from the damager.
     *
     * @return A long of the last hit timestamp.
     */
    public long getLastDamagerTimeStamp() {
        return lastDamagerTimeStamp;
    }

    /**
     * Returns the owner of the HitInformation
     *
     * @return The player who was hit.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Clears the last damager information
     */
    public void clear() {
        lastDamager = null;
        lastDamagerTimeStamp = 0;
    }
}