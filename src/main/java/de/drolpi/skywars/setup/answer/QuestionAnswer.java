package de.drolpi.skywars.setup.answer;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public interface QuestionAnswer<T> {

    T parse(Player player, String input);

    boolean isValidInput(String input);

    /**
     * @return null if there are infinite possible numbers
     */
    @Nullable Collection<String> getPossibleAnswers();

    default @Nullable List<String> getCompletableAnswers() {
        return null;
    }

    String getInvalidInputMessage(@NotNull String input);
}
