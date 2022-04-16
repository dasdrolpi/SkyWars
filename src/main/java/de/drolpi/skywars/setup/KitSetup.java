package de.drolpi.skywars.setup;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.chest.bukkit.ConfigurationItemStack;
import de.drolpi.skywars.kit.KitManager;
import de.drolpi.skywars.kit.Kit;
import de.drolpi.skywars.setup.answer.QuestionAnswerInventory;
import de.drolpi.skywars.setup.answer.QuestionAnswerItemStack;
import de.drolpi.skywars.setup.answer.QuestionAnswerString;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class KitSetup extends Setup{

    private final KitManager kitManager;

    public KitSetup(Player player, SkyWars skyWars) {
        super(player, skyWars.getSetupManager());

        this.kitManager = skyWars.getKitManager();
    }

    @Override
    public void configure() {
        addQuestion(new Question<>(
                "kitName",
                "§aWie soll das Kit heißen?",
                new QuestionAnswerString() {

                    @Override
                    public boolean isValidInput(@NotNull String input) {
                        return true;
                    }

                    @Override
                    public String getInvalidInputMessage(@NotNull String input) {
                        return "null";
                    }
                }
        ));

        addQuestion(new Question<>(
                "kitItem",
                "§aWas soll das Item des Kits sein?",
                new QuestionAnswerItemStack() {
                    @Override
                    public String getInvalidInputMessage(@NotNull String input) {
                        return "null";
                    }
                }
        ));

        addQuestion(new Question<>(
                "kitInventory",
                "§aWelche items soll das Kit beinhalten?",
                new QuestionAnswerInventory() {
                    @Override
                    public String getInvalidInputMessage(@NotNull String input) {
                        return "null";
                    }
                }
        ));
    }

    @Override
    public void finish() {
        if(hasResult("kitName")) {
            Kit kit = new Kit(getResult("kitName", String.class));

            if(hasResult("kitItem")) {
                kit.setSelectItem(ConfigurationItemStack.of(getResult("kitItem", ItemStack.class)));
                System.out.println("Test1");
            }

            if(hasResult("kitInventory")) {
                PlayerInventory inventory = getResult("kitInventory", PlayerInventory.class);

                if(inventory.getHelmet() != null)
                    kit.setHelmet(ConfigurationItemStack.of(inventory.getHelmet()));
                if(inventory.getChestplate() != null)
                    kit.setChestplate(ConfigurationItemStack.of(inventory.getChestplate()));
                if(inventory.getLeggings() != null)
                    kit.setLeggins(ConfigurationItemStack.of(inventory.getLeggings()));
                if(inventory.getBoots() != null)
                    kit.setBoots(ConfigurationItemStack.of(inventory.getBoots()));

                Map<Integer, ConfigurationItemStack> items = new HashMap<>();
                for (int i = 0; i < inventory.getContents().length; i++) {
                    ItemStack itemStack = inventory.getItem(i);

                    if(itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                        items.put(i, ConfigurationItemStack.of(itemStack));
                    }
                }

                kit.setInventory(items);
                System.out.println("Test2");
            }

            kitManager.getConfig().getKits().add(kit);
            kitManager.getConfigLoader().saveConfig(kitManager.getConfig());
            player.sendMessage("SUCCES");
        }
    }

    @Override
    public void cancel() {

    }
}
