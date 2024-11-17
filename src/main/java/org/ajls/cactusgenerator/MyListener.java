package org.ajls.cactusgenerator;

import org.ajls.cactusgenerator.maths.Cylinder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.UUID;

public class MyListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (ItemStackModify.containsLore(itemInMainHand, "float")) {
            FloatingBlock.createFloatingBlockMarker(block);
//            marker.setMetadata();
        }
        if (!player.hasMetadata("silent")) {
            if (ItemStackModify.containsLore(itemInMainHand, "cactus_generator")) {
//            event.setCancelled(true);
//                Location location = event.getBlock().getLocation();
//            event.get
                Generator.generateCactus(location);
            }
            else if (ItemStackModify.containsLore(itemInMainHand, "floating_sand")) {
                event.setCancelled(true);
//            Location location = event.getBlock().getLocation();
//                Block block = event.getBlock();
                BukkitScheduler scheduler = Bukkit.getScheduler();
                scheduler.scheduleSyncDelayedTask(CactusGenerator.getPlugin(), () -> {
                    block.setType(Material.SAND, false);
                },0);
            }
        }
        else {
            event.setCancelled(true);
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.scheduleSyncDelayedTask(CactusGenerator.getPlugin(), () -> {
                event.getBlock().setType(player.getInventory().getItemInMainHand().getType(), false);
            },0);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        Block block = event.getBlock();
        Marker marker = FloatingBlock.getFloatingBlockMarker(block);
        if (marker != null) {
            marker.remove();
        }
        if (!player.hasMetadata("silent")) {
            if(ItemStackModify.containsLore(itemInMainHand, "silk")) {
                event.setCancelled(true);
//                Block block = event.getBlock();
                BukkitScheduler scheduler = Bukkit.getScheduler();
                scheduler.scheduleSyncDelayedTask(CactusGenerator.getPlugin(), () -> {
                    block.setType(Material.AIR, false);
                },0);
            }
        }
        else {
            event.setCancelled(true);
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.scheduleSyncDelayedTask(CactusGenerator.getPlugin(), () -> {
                event.getBlock().setType(Material.AIR, false);
            },0);
        }

    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : new ArrayList<Block>(event.blockList()))
            FloatingBlock.removeFloatingBlockMarker(block);
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
//        BlockState state = event.getBlock().getState();
//        state.getData();
        Block block = event.getBlock();
        Material material = block.getType();
        if (material.toString().contains("CORAL")) {
            event.setCancelled(true);
        }
    }

//    @EventHandler
//    public void onBlockPhysics(BlockPhysicsEvent event) {
//        Block block = event.getBlock();
//        Material material = block.getType();
//        if (material == Material.YELLOW_CONCRETE_POWDER) {
//            event.setCancelled(true);
//        }
//    }

//    @EventHandler
//    public void onEntitySpawn(EntitySpawnEvent event) {
//        Entity entity = event.getEntity();
//        if (entity instanceof FallingBlock) {
//            FallingBlock fallingBlock = (FallingBlock) entity;
//            BlockData blockData = fallingBlock.getBlockData();
//            Material material = blockData.getMaterial();
//            if (material == Material.YELLOW_CONCRETE_POWDER) {
//                event.setCancelled(true);
//            }
//        }
//    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        Entity entity = event.getEntity();
        Block block = event.getBlock();
        Material material = block.getType();
        if (entity instanceof FallingBlock) {
            Marker marker = FloatingBlock.getFloatingBlockMarker(block);
            if (marker != null) {
                event.setCancelled(true);
            }
            if (material == Material.YELLOW_CONCRETE_POWDER) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        EquipmentSlot hand = event.getHand();
        UUID playerUUID = event.getPlayer().getUniqueId();
        ItemStack itemStack = event.getItem();
        Player player = event.getPlayer();
//        if (hand == EquipmentSlot.HAND) {
//            player_mainHandRightClickItem.put(playerUUID, itemStack);
//        }
//        if (hand == EquipmentSlot.OFF_HAND) {
//            player_offHandRightClickItem.put(playerUUID, itemStack);
//        }
        if (itemStack != null && itemStack.getType() != Material.AIR) {

        }
        if (ItemStackModify.containsPersistentData(itemStack, NameSpacedKeys.MICRO_HID, PersistentDataType.BOOLEAN)) {
//            player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
            for (Entity entity: Cylinder.getNearbyCylindricalEntities(player, 5, 1, 114514, 0)) {
                entity.getWorld().strikeLightning(entity.getLocation());
            }
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

    }
}
