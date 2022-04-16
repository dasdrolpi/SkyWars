package de.drolpi.skywars.chest.filler;

import de.drolpi.skywars.chest.collection.RandomCollection;
import de.drolpi.skywars.chest.config.ChestLootConfig;
import de.drolpi.skywars.chest.config.ChestLootConfigLoader;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ChestFiller {

    private final static ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private final RandomCollection<ChestItem> normalChestConfiguration;
    private final RandomCollection<ChestItem> middleChestConfiguration;

    public ChestFiller() {
        ChestLootConfigLoader configLoader = new ChestLootConfigLoader();
        ChestLootConfig config = configLoader.loadOrCreateFile();

        this.normalChestConfiguration = config.getNormalChest();
        this.middleChestConfiguration = config.getMiddleChest();
    }

    public void fillChest(ChestType chestType, Inventory inventory) {
        RandomCollection<ChestItem> collection;

        if (chestType == ChestType.MIDDLE) {
            collection = middleChestConfiguration;
        } else {
            collection = normalChestConfiguration;
        }

        Set<Integer> addedSlots = new HashSet<>();
        Set<ItemStack> addedMaterials = new HashSet<>();
        Set<String> addedTypes = new HashSet<>();

        int itemAmount;

        do {
            itemAmount = (int) (Math.random() * 16);
        } while (itemAmount < 8);

        while(itemAmount >= 1) {
            itemAmount--;

            for( ;; ) {

                int randomSlot = (int) (Math.random() * 26);
                if(!addedSlots.contains(randomSlot)) {

                    addedSlots.add(randomSlot);
                    for( ;; ) {

                        ChestItem chestItem = collection.next();
                        ItemStack randomStack = chestItem.getItemStack();

                        if(addedMaterials.size() >= collection.size()) {
                            return;
                        }

                        if(!addedMaterials.contains(randomStack)) {
                            if(randomStack.getType().name().contains( "_" )) {
                                String type = randomStack.getType().name().split( "_" )[1];
                                if(addedTypes.contains( type )) {
                                    continue;
                                }
                                addedTypes.add( type );
                            }

                            addedMaterials.add(randomStack);
                            ItemStack setStack = randomStack.clone();
                            if(randomStack.getAmount() == 0) {
                                int randomAmount;
                                if(chestItem.getMinRandom() == 0) {
                                    randomAmount = RANDOM.nextInt(chestItem.getMaxRandom());
                                } else {
                                    randomAmount = RANDOM.nextInt(chestItem.getMaxRandom() - chestItem.getMinRandom() + 1) + chestItem.getMinRandom();
                                }
                                setStack.setAmount(randomAmount);
                            }
                            inventory.setItem(randomSlot, setStack);

                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

}