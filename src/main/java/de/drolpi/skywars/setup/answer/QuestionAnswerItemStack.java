package de.drolpi.skywars.setup.answer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public abstract class QuestionAnswerItemStack implements QuestionAnswer<ItemStack> {

    @Override
    public ItemStack parse(Player player, String input) {
        if(input.equalsIgnoreCase("gesetzt")) {
            return player.getItemInHand();
        }
        return null;
    }

    @Override
    public boolean isValidInput(String input) {
        return input.equalsIgnoreCase("gesetzt");
    }

    @Override
    public @Nullable Collection<String> getPossibleAnswers() {
        return  Collections.singleton("gesetzt");
    }
}
