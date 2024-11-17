package org.ajls.cactusgenerator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;

import java.util.List;
import java.util.Random;

public class Generator {
    public static void generateCactus(Location location) {
        Configuration configuration = CactusGenerator.getPlugin().getConfig();
        List<Integer> heights = configuration.getIntegerList("cactus_generator.height");
        Random random = new Random();;
        int randomResult = random.nextInt(heights.size());
        int height = heights.get(randomResult);
        World world = location.getWorld();
        for (int i = 0; i < height; i++) {
            Block block = world.getBlockAt(location);
            block.setType(Material.CACTUS);
            location.setY(location.getY() + 1);
        }
        List<String> tops = configuration.getStringList("cactus_generator.top");
        randomResult = random.nextInt(tops.size());
        String top = tops.get(randomResult);
        Block block = world.getBlockAt(location);
        Material material = Material.valueOf(top);//Material.getMaterial(top);
//        Material.
        block.setType(material);
        BlocksModify.removeWaterlogged(block);
    }

    public static void generateFloatingSand(Location location) {
        World world = location.getWorld();
        Block block = world.getBlockAt(location);
        block.setType(Material.SAND, false);
    }

    public static void generateSilkAir(Block block) {
        block.setType(Material.AIR, false);
    }
}
