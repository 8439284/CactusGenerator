package org.ajls.cactusgenerator.events;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.ajls.lib.advanced.HashMapWithDefault;
import org.ajls.lib.references.Time;
import org.ajls.lib.utils.BukkitU;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class EntityAndLiquidEventTrigger implements Listener {
    public static HashMap<UUID, Block> entity_liquidBlocks = new HashMap<>();
    public static HashMapWithDefault<UUID, Material> entity_liquidMaterials = new HashMapWithDefault<>(Material.AIR);
    public static HashMap<UUID, Integer> entity_liquidTime = new HashMap<>();
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Entity entity = event.getPlayer();
        UUID entityUUID = entity.getUniqueId();
        Block block = entity.getLocation().getBlock();
        Material material = block.getType();
        BlockData blockData = block.getBlockData();
        int time = Time.getTime();
        boolean isWaterlogged = false;  //blockData instanceof Waterlogged
        if (blockData instanceof Waterlogged) {
            Waterlogged waterlogged = (Waterlogged) blockData;
            isWaterlogged = waterlogged.isWaterlogged();
        }

        if (material == Material.WATER || isWaterlogged) {
            if (entity_liquidMaterials.get(entityUUID) != Material.WATER) {
                BukkitU.callEvent(new EntityAndLiquidEvent(entity, Material.WATER));
                entity_liquidMaterials.put(entityUUID, Material.WATER);

            }
        }
        else if (material == Material.LAVA) {
            if (entity_liquidMaterials.get(entity.getUniqueId()) != Material.LAVA) {
                BukkitU.callEvent(new EntityAndLiquidEvent(entity, Material.LAVA));
                entity_liquidBlocks.put(entity.getUniqueId(), block);
            }
        }
        else if (entity_liquidMaterials.containsKey(entityUUID)){
            BukkitU.callEvent(new EntityAndLiquidEvent(entity, Material.AIR));
            entity_liquidMaterials.remove(entityUUID);
        }
    }
}
