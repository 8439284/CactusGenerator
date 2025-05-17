package org.ajls.cactusgenerator.utils;

import org.ajls.lib.namespacedkeys.NamespacedKeys;
import org.ajls.lib.utils.ItemU;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class DisplayItemU {
    static String NORMAL = "normal";
    public static Item setDisplayItem(Item item) {
        item.setCanPlayerPickup(false);
        item.setCanMobPickup(false);
        ItemU.setStringPersistentData(item, NamespacedKeys.DISPLAY_NAME, NORMAL);
        return item;

    }

    public static boolean isDisplayItem(Item item) {
        return item.getPersistentDataContainer().has(NamespacedKeys.DISPLAY_NAME, PersistentDataType.STRING);
    }
}
