package org.ajls.cactusgenerator;

import org.ajls.cactusgenerator.cosmetics.Bloody;
import org.ajls.cactusgenerator.maths.Cylinder;
import org.ajls.cactusgenerator.utils.EventU;
import org.ajls.cactusgenerator.utils.RayTraceU;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashSet;
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
    HashSet<UUID> entityBloodParticles = new HashSet<>();
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        UUID entityUUID = entity.getUniqueId();
        World world = entity.getWorld();
        Location location = entity.getLocation();
        Location damageLocation = location.clone().add(0, 1, 0);
        double finalDamage = event.getFinalDamage();
        double realFinalDamage = EventU.getFinalDamage(event);

        if (!entityBloodParticles.remove(entityUUID)) {

            //originally 100 and use redstone wire as particle
//            world.spawnParticle(Particle.BLOCK, damageLocation, (int) (realFinalDamage * 10), 0, 0, 0, 114514, Material.REDSTONE_BLOCK.createBlockData());  //Material.REDSTONE.createBlockData()  Bukkit.createBlockData(Material.REDSTONE)
            Bloody.bleed(damageLocation, realFinalDamage);
//            for (int i = 0; i < realFinalDamage; i++) {
//                Item bloodItem = world.dropItemNaturally(damageLocation, Bloody.getRandomBloodyItem());
//                bloodItem.setCanPlayerPickup(false);
//            }

        }
//        BlockData blockData = BlockData
//        Bukkit.createBlockData(Material.PINK_CONCRETE)
//        entity.getLocation().getBlock().breakNaturally(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        UUID entityUUID = entity.getUniqueId();
        Entity damagerEntity = event.getDamager();
        World world = entity.getWorld();
        Location location = entity.getLocation();
        double finalDamage = event.getFinalDamage();
        double realFinalDamage = EventU.getFinalDamage(event);
        Location damageLocation = RayTraceU.getRayInterSection(damagerEntity, entity, 114514);
        if (damageLocation == null) {
            damageLocation = location.clone().add(0, 1,0);
        }

        entityBloodParticles.add(entityUUID);
//        world.spawnParticle(Particle.BLOCK, damageLocation, (int) (realFinalDamage * 10), 0, 0, 0, 114514, Material.REDSTONE_BLOCK.createBlockData());  //Material.REDSTONE.createBlockData()  Bukkit.createBlockData(Material.REDSTONE)  //location.add(0, 1, 0)
//        for (int i = 0; i < realFinalDamage; i++) {
//            Item bloodItem = world.dropItemNaturally(damageLocation, Bloody.getRandomBloodyItem());
//            bloodItem.setCanPlayerPickup(false);
//        }
        Bloody.bleed(damageLocation, realFinalDamage);

    }
}
