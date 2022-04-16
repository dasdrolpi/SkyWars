package de.drolpi.skywars.visibility;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class VisibilityRestrictionManager {

    private final Set<VisibilityRestriction> visibilityRestrictions = new HashSet<>();

    public VisibilityRestriction register(VisibilityRestriction visibilityRestriction) {
        visibilityRestrictions.add(visibilityRestriction);
        return visibilityRestriction;
    }

    public VisibilityRestriction unregister(VisibilityRestriction visibilityRestriction) {
        visibilityRestrictions.remove(visibilityRestriction);
        return visibilityRestriction;
    }

    public void updateAll(Player player, Player player2) {
        for (VisibilityRestriction restriction : visibilityRestrictions) {
            restriction.handleUpdate(player, player2);
        }
    }

    public void updateAll(Player player, Collection<? extends Player> players) {
        for (VisibilityRestriction restriction : visibilityRestrictions) {
            restriction.handleUpdate(player, players);
        }
    }

    public void updateAll(Collection<? extends Player> players, Player player2) {
        for (VisibilityRestriction restriction : visibilityRestrictions) {
            restriction.handleUpdate(players, player2);
        }
    }

}