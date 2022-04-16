package de.drolpi.skywars.setup.answer;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public abstract class QuestionAnswerLocation implements QuestionAnswer<Location> {

    @Override
    public Location parse(Player player, String input) {
        if(input.equalsIgnoreCase("gesetzt")) {
            return player.getLocation();
        }
        return null;
    }

    @Override
    public boolean isValidInput(String input) {
        return input.equalsIgnoreCase("gesetzt");
    }

    @Override
    public @Nullable Collection<String> getPossibleAnswers() {
        return Collections.singleton("gesetzt");
    }
}
