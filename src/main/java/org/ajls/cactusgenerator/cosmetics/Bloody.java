package org.ajls.cactusgenerator.cosmetics;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Bloody {
    public static void bleed(Location location, double damage) {
        World world = location.getWorld();
        Random random = new Random();
        int lightBloodCount = (int) (damage * 10 + 1);  // if damage * 10 = 2, it will be 3(0,1,2 for random)
        int generateLightBloodCount = random.nextInt(lightBloodCount);
        int generateDarkBloodCount = (int) ((damage * 10 - generateLightBloodCount) * 10);
        world.spawnParticle(Particle.BLOCK, location, generateLightBloodCount, 0, 0, 0, 114514, Material.REDSTONE_BLOCK.createBlockData());
        world.spawnParticle(Particle.BLOCK, location, generateDarkBloodCount, 0, 0, 0, 114514, Material.REDSTONE_BLOCK.createBlockData());
//        Bukkit.broadcastMessage("light" + String.valueOf(generateLightBloodCount));
//        Bukkit.broadcastMessage("dark" + String.valueOf(generateDarkBloodCount));
//        Bukkit.broadcastMessage("total" + String.valueOf(generateDarkBloodCount / 100 + generateLightBloodCount / 10));
        for (int i = 0; i < damage; i++) {
            Item bloodItem = world.dropItemNaturally(location, Bloody.getRandomBloodyItem());
            bloodItem.setCanPlayerPickup(false);
        }

//        world.spawnParticle(Particle.BLOCK, location, (int) (realFinalDamage * 10), 0, 0, 0, 114514, Material.REDSTONE_BLOCK.createBlockData());  //Material.REDSTONE.createBlockData()  Bukkit.createBlockData(Material.REDSTONE)
//        for (int i = 0; i < realFinalDamage; i++) {
//            Item bloodItem = world.dropItemNaturally(damageLocation, Bloody.getRandomBloodyItem());
//            bloodItem.setCanPlayerPickup(false);
//        }

    }
    public static ItemStack getRandomBloodyItem() {
        Random random = new Random();
        int itemIndex = random.nextInt(7);
        ItemStack itemStack = null; // = new ItemStack(Material.AIR);
        switch (itemIndex) {
            case 0:
                itemStack = new ItemStack(Material.RED_DYE);
                break;
            case 1:
                itemStack = new ItemStack(Material.NETHER_WART);
                break;
            case 2:
                itemStack = new ItemStack(Material.REDSTONE);
                break;
            case 3:
                itemStack = new ItemStack(Material.RED_CANDLE);
                break;
            case 4:
                itemStack = new ItemStack(Material.RED_BANNER);
                break;
            case 5:
                itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                break;
            case 6:
                itemStack = new ItemStack(Material.RED_CARPET);
                break;

        }
        return itemStack;


    }
}
