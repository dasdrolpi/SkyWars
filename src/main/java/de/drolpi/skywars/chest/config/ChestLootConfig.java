package de.drolpi.skywars.chest.config;

import de.drolpi.skywars.chest.bukkit.ConfigurationItemStack;
import de.drolpi.skywars.chest.collection.RandomCollection;
import de.drolpi.skywars.chest.filler.ChestItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChestLootConfig {

    private final Map<Integer, Set<ChestItem>> normalChest;
    private final Map<Integer, Set<ChestItem>> middleChest;

    public ChestLootConfig() {
        this.normalChest = new HashMap<>();
        this.middleChest = new HashMap<>();

        createDefaultNormal();
        createDefaultMiddle();
    }

    public RandomCollection<ChestItem> getNormalChest() {
        RandomCollection<ChestItem> randomCollection = new RandomCollection<>();
        for (Map.Entry<Integer, Set<ChestItem>> entry : normalChest.entrySet()) {
            for (ChestItem chestItem : entry.getValue()) {
                if(chestItem.getMaxRandom() >= chestItem.getMinRandom()) {
                    randomCollection.add(entry.getKey(), chestItem);
                }else {
                    System.out.println(
                            "Weight: " + entry.getKey() + " Material:"
                                    + chestItem.getItemStack().getType().toString()
                                    + " can't be loaded, because the minimum random amount is larger than the maximum");
                }
            }
        }

        return randomCollection;
    }

    public RandomCollection<ChestItem> getMiddleChest() {
        RandomCollection<ChestItem> randomCollection = new RandomCollection<>();
        for (Map.Entry<Integer, Set<ChestItem>> entry : middleChest.entrySet()) {
            for (ChestItem chestItem : entry.getValue()) {
                if(chestItem.getMaxRandom() >= chestItem.getMinRandom()) {
                    randomCollection.add(entry.getKey(), chestItem);
                }else {
                    Bukkit.broadcastMessage(
                            "Weight: " + entry.getKey() + " Material:"
                                    + chestItem.getItemStack().getType().toString()
                                    + " can't be loaded, because the minimum random amount is larger than the maximum");
                }
            }
        }

        return randomCollection;
    }

    public void createDefaultNormal() {
        //Weight 1
        Set<ChestItem> weight1 = new HashSet<>();

        weight1.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_AXE).addEnchantment(Enchantment.DIG_SPEED, 1).build()));
        weight1.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 1).build()));

        normalChest.put(1, weight1);

        Set<ChestItem> weight2 = new HashSet<>();

        //Weight 2
        weight2.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()));
        weight2.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight2.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight2.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.ENDER_PEARL).build()));
        weight2.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight2.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.IRON_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight2.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLD_AXE).addEnchantment(Enchantment.DIG_SPEED, 2).build()));
        weight2.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLD_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 2).build()));

        normalChest.put(2, weight2);

        Set<ChestItem> weight3 = new HashSet<>();

        //Weight 3
        weight3.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.IRON_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight3.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.IRON_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight3.add(new ChestItem(10, 1, ConfigurationItemStack.builder(Material.IRON_INGOT).build()));
        weight3.add(new ChestItem(10, 2, ConfigurationItemStack.builder(Material.TNT).build()));
        weight3.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLDEN_APPLE).build()));

        normalChest.put(3, weight3);

        Set<ChestItem> weight4 = new HashSet<>();

        //Weight 4
        weight4.add(new ChestItem(4, 1, ConfigurationItemStack.builder(Material.DIAMOND).build()));
        weight4.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.WATER_BUCKET).build()));
        weight4.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.LAVA_BUCKET).build()));
        weight4.add(new ChestItem(15, 3, ConfigurationItemStack.builder(Material.EXP_BOTTLE).build()));

        normalChest.put(4, weight4);

        Set<ChestItem> weight6 = new HashSet<>();

        //Weight 6
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLD_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLD_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLD_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLD_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.COMPASS).build()));
        weight6.add(new ChestItem(10, 5, ConfigurationItemStack.builder(Material.COOKED_BEEF).build()));
        weight6.add(new ChestItem(5, 1, ConfigurationItemStack.builder(Material.FLINT).build()));
        weight6.add(new ChestItem(64, 30, ConfigurationItemStack.builder(Material.STONE).build()));
        weight6.add(new ChestItem(64, 30, ConfigurationItemStack.builder(Material.BRICK).build()));
        weight6.add(new ChestItem(64, 20, ConfigurationItemStack.builder(Material.GLASS).build()));
        weight6.add(new ChestItem(64, 30, ConfigurationItemStack.builder(Material.WOOD).build()));
        weight6.add(new ChestItem(5, 2, ConfigurationItemStack.builder(Material.WEB).build()));
        weight6.add(new ChestItem(4, 2, ConfigurationItemStack.builder(Material.PUMPKIN_PIE).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.CAKE).build()));
        weight6.add(new ChestItem(10, 5, ConfigurationItemStack.builder(Material.RAW_BEEF).build()));
        weight6.add(new ChestItem(5, 1, ConfigurationItemStack.builder(Material.STICK).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.WOOD_SWORD).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.WOOD_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLD_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.STONE_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).build()));
        weight6.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.LEATHER_HELMET).build()));
        weight6.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.LEATHER_CHESTPLATE).build()));
        weight6.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.LEATHER_LEGGINGS).build()));
        weight6.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.LEATHER_BOOTS).build()));
        weight6.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.CHAINMAIL_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()));
        weight6.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()));
        weight6.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()));
        weight6.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()));

        normalChest.put(6, weight6);
    }
    
    public void createDefaultMiddle() {
        Set<ChestItem> weight2 = new HashSet<>();
        
        //Weight 2
        weight2.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.ENDER_PEARL).build()));

        middleChest.put(2, weight2);

        Set<ChestItem> weight3 = new HashSet<>();
        
        //Weight 3
        weight3.add(new ChestItem(4, 1, ConfigurationItemStack.builder(Material.DIAMOND).build()));
        weight3.add(new ChestItem(10, 1, ConfigurationItemStack.builder(Material.IRON_INGOT).build()));
        weight3.add(new ChestItem(10, 2, ConfigurationItemStack.builder(Material.TNT).build()));
        weight3.add(new ChestItem(15, 3, ConfigurationItemStack.builder(Material.EXP_BOTTLE).build()));
        weight3.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.GOLD_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight3.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.GOLD_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight3.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.GOLD_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight3.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.GOLD_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight3.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLDEN_APPLE).build()));
        weight3.add(new ChestItem(16, 5, ConfigurationItemStack.builder(Material.EGG).build()));

        middleChest.put(3, weight3);

        Set<ChestItem> weight4 = new HashSet<>();
        
        //Weight 4
        weight4.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_AXE).addEnchantment(Enchantment.DIG_SPEED, 1).build()));
        weight4.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 1).build()));
        weight4.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.WATER_BUCKET).build()));
        weight4.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.LAVA_BUCKET).build()));

        middleChest.put(4, weight4);

        Set<ChestItem> weight5 = new HashSet<>();
        
        //Weight 5
        weight5.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()));
        weight5.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()));
        weight5.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight5.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight5.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.IRON_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight5.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight5.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.IRON_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight5.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.IRON_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()));
        weight5.add(new ChestItem(16, 5, ConfigurationItemStack.builder(Material.SNOW_BALL).build()));

        middleChest.put(5, weight5);

        Set<ChestItem> weight6 = new HashSet<>();
        
        //Weight 6
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.COMPASS).build()));
        weight6.add(new ChestItem(10, 5, ConfigurationItemStack.builder(Material.COOKED_BEEF).build()));
        weight6.add(new ChestItem(5, 1, ConfigurationItemStack.builder(Material.FLINT).build()));
        weight6.add(new ChestItem(64, 30, ConfigurationItemStack.builder(Material.STONE).build()));
        weight6.add(new ChestItem(64, 30, ConfigurationItemStack.builder(Material.BRICK).build()));
        weight6.add(new ChestItem(64, 20, ConfigurationItemStack.builder(Material.GLASS).build()));
        weight6.add(new ChestItem(64, 30, ConfigurationItemStack.builder(Material.WOOD).build()));
        weight6.add(new ChestItem(5, 2, ConfigurationItemStack.builder(Material.WEB).build()));
        weight6.add(new ChestItem(4, 2, ConfigurationItemStack.builder(Material.PUMPKIN_PIE).build()));
        weight6.add(new ChestItem(1,1, ConfigurationItemStack.builder(Material.CAKE).build()));
        weight6.add(new ChestItem(10, 5, ConfigurationItemStack.builder(Material.RAW_BEEF).build()));
        weight6.add(new ChestItem(5, 1, ConfigurationItemStack.builder(Material.STICK).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.WOOD_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.GOLD_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).build()));
        weight6.add(new ChestItem(1, 1, ConfigurationItemStack.builder(Material.STONE_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).build()));

        middleChest.put(6, weight6);
    }
}
