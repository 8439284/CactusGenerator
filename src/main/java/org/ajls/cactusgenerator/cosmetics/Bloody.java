package org.ajls.cactusgenerator.cosmetics;

import org.ajls.cactusgenerator.MyListener;
import org.ajls.cactusgenerator.advanced.RandomArrayList;
import org.ajls.cactusgenerator.utils.EventU;
import org.ajls.cactusgenerator.utils.RayTraceU;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.UUID;

public class Bloody {
    public static void bleed(Location location, double damage) {
        World world = location.getWorld();
        Random random = new Random();
        int lightBloodCount = (int) (damage * 10 + 1);  // if damage * 10 = 2, it will be 3(0,1,2 for random)
        int generateLightBloodCount = random.nextInt(lightBloodCount);
        int generateDarkBloodCount = (int) ((damage * 10 - generateLightBloodCount) * 10);
        world.spawnParticle(Particle.BLOCK, location, generateLightBloodCount, 0, 0, 0, 114514, Material.REDSTONE_BLOCK.createBlockData());
        world.spawnParticle(Particle.BLOCK, location, generateDarkBloodCount, 0, 0, 0, 114514, Material.REDSTONE_WIRE.createBlockData());
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

    public static void damageByEntityBleed(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        UUID entityUUID = entity.getUniqueId();
        Entity damagerEntity = event.getDamager();
        World world = entity.getWorld();
        Location location = entity.getLocation();
        double finalDamage = event.getFinalDamage();
        double realFinalDamage = EventU.getFinalDamage(event);
        Location damageLocation = null;
        boolean inside = false;
        if (damagerEntity instanceof LivingEntity) {
            LivingEntity damagerLivingEntity = (LivingEntity) damagerEntity;
            if (entity.getBoundingBox().contains(damagerLivingEntity.getEyeLocation().toVector())) {
                damageLocation = damagerLivingEntity.getEyeLocation();
                inside = true;
            }

        }
        if (!inside) {
            damageLocation = RayTraceU.getRayInterSection(damagerEntity, entity, 114514);
        }
        if (damageLocation == null) {
            damageLocation = location.clone().add(0, 1,0);
        }

        MyListener.entityBloodParticles.add(entityUUID);
//        world.spawnParticle(Particle.BLOCK, damageLocation, (int) (realFinalDamage * 10), 0, 0, 0, 114514, Material.REDSTONE_BLOCK.createBlockData());  //Material.REDSTONE.createBlockData()  Bukkit.createBlockData(Material.REDSTONE)  //location.add(0, 1, 0)
//        for (int i = 0; i < realFinalDamage; i++) {
//            Item bloodItem = world.dropItemNaturally(damageLocation, Bloody.getRandomBloodyItem());
//            bloodItem.setCanPlayerPickup(false);
//        }
        Bloody.bleed(damageLocation, realFinalDamage);
    }
    public static ItemStack getRandomBloodyItem() {
        RandomArrayList<Material> materials = new RandomArrayList<>();
        materials.add(Material.RED_DYE);
        materials.add(Material.NETHER_WART);
        materials.add(Material.REDSTONE);
        materials.add(Material.RED_CANDLE);
        materials.add(Material.RED_BANNER);
        materials.add(Material.RED_STAINED_GLASS_PANE);
        materials.add(Material.RED_CARPET);
        materials.add(Material.REDSTONE_BLOCK);
        materials.add(Material.RED_STAINED_GLASS);
        materials.add(Material.RED_WOOL);
        materials.add(Material.RED_BED);
        materials.add(Material.NETHERRACK);
        materials.add(Material.NETHER_WART_BLOCK);
        materials.add(Material.NETHER_BRICK);
        materials.add(Material.RED_NETHER_BRICKS);
        materials.add(Material.FIRE_CORAL);
        materials.add(Material.RED_CONCRETE);
        materials.add(Material.RED_CONCRETE_POWDER);
        Random random = new Random();
//        int itemIndex = random.nextInt(7);
//        ItemStack itemStack = null; // = new ItemStack(Material.AIR);
//        switch (itemIndex) {
//            case 0:
//                itemStack = new ItemStack(Material.RED_DYE);
//                break;
//            case 1:
//                itemStack = new ItemStack(Material.NETHER_WART);
//                break;
//            case 2:
//                itemStack = new ItemStack(Material.REDSTONE);
//                break;
//            case 3:
//                itemStack = new ItemStack(Material.RED_CANDLE);
//                break;
//            case 4:
//                itemStack = new ItemStack(Material.RED_BANNER);
//                break;
//            case 5:
//                itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
//                break;
//            case 6:
//                itemStack = new ItemStack(Material.RED_CARPET);
//                break;
//
//        }

//        return itemStack;
        return new ItemStack(materials.get(random.nextInt(materials.size())));


    }
}
