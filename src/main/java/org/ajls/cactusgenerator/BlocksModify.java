package org.ajls.cactusgenerator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.material.Wool;

public class BlocksModify {
    public static void fill (int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Material material) {
        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    Block block = Bukkit.getWorld("world").getBlockAt(x, y, z);
                    block.setType(material);
                }
            }
        }
    }

    public static boolean isInBounds(double x, double y , double z, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return x > minX && x < maxX && y > minY && y < maxY && z > minZ && z < maxZ;
    }

    public static Block removeWaterlogged(Block block) {
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Waterlogged) {
            Waterlogged waterlogged = (Waterlogged) blockData;
            waterlogged.setWaterlogged(false);
            block.setBlockData(waterlogged);
        }
        return block;
    }

}
