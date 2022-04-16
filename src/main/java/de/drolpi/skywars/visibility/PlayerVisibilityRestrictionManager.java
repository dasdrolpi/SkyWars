package de.drolpi.skywars.visibility;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.BiPredicate;

public class PlayerVisibilityRestrictionManager extends VisibilityRestriction {

    public PlayerVisibilityRestrictionManager(BiPredicate<Player, Player> shouldHide) {
        super(shouldHide);
    }

    @Override
    public void handleUpdate(Player viewer, Player toHide) {
        setVisible(viewer, toHide, shouldHide.test(toHide, viewer));
    }

    @Override
    public void handleUpdate(Player viewer, Collection<? extends Player> toHides) {
        for (Player toHide : toHides) {
            handleUpdate(viewer, toHide);
        }
    }

    @Override
    public void handleUpdate(Collection<? extends Player> viewers, Player toHide) {
        for (Player viewer : viewers) {
            handleUpdate(viewer, toHide);
        }
    }

}