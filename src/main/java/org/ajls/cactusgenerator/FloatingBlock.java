package org.ajls.cactusgenerator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Marker;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class FloatingBlock {
    static Marker createFloatingBlockMarker(Block block) {
        Location location = block.getLocation();
        Marker marker = location.getWorld().spawn(location, Marker.class);
        PersistentDataContainer persistentDataContainer = marker.getPersistentDataContainer();
        persistentDataContainer.set(PersistentDataKeys.FLOAT_X, PersistentDataType.INTEGER, block.getX());
        persistentDataContainer.set(PersistentDataKeys.FLOAT_Y, PersistentDataType.INTEGER, block.getY());
        persistentDataContainer.set(PersistentDataKeys.FLOAT_Z, PersistentDataType.INTEGER, block.getZ());
        persistentDataContainer.set(PersistentDataKeys.FLOAT_WORLD, PersistentDataType.STRING, block.getWorld().getName());
        return marker;
    }

    static Marker getFloatingBlockMarker(Block block) {
        Location location = block.getLocation();
        World locationWorld = location.getWorld();
        for (Marker marker : locationWorld.getEntitiesByClass(Marker.class)) {
            PersistentDataContainer persistentDataContainer = marker.getPersistentDataContainer();
            if (persistentDataContainer.has(PersistentDataKeys.FLOAT_WORLD, PersistentDataType.STRING)) {
                int x = persistentDataContainer.get(PersistentDataKeys.FLOAT_X, PersistentDataType.INTEGER);
                int y = persistentDataContainer.get(PersistentDataKeys.FLOAT_Y, PersistentDataType.INTEGER);
                int z = persistentDataContainer.get(PersistentDataKeys.FLOAT_Z, PersistentDataType.INTEGER);
                String worldName = persistentDataContainer.get(PersistentDataKeys.FLOAT_WORLD, PersistentDataType.STRING);
                if (x == location.getX() && y == location.getY() && z == location.getZ() && worldName.equals(locationWorld.getName())) {
                    return marker;
                }
            }

//            World world = Bukkit.getWorld(worldName);

        }
        return null;
    }

    static Marker removeFloatingBlockMarker(Block block) {
        Marker marker = getFloatingBlockMarker(block);
        if (marker != null) {
            marker.remove();
        }
        return marker;
    }
}
