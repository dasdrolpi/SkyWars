package de.drolpi.skywars.setup.answer;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract class QuestionAnswerString implements QuestionAnswer<String> {

    @Override
    public String parse(Player player, String input) {
        return input;
    }

    @Override
    public boolean isValidInput(@NotNull String input) {
        return true;
    }

    @Override
    public @Nullable Collection<String> getPossibleAnswers() {
        return null;
    }
}