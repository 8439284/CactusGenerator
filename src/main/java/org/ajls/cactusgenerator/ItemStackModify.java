package org.ajls.cactusgenerator;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ItemStackModify {
    //unbreakable
    public static ItemStack setUnbreakable(ItemStack itemStack){
        // set unbreakable
        ItemMeta meta = itemStack.getItemMeta();
        meta.setUnbreakable(true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack setDisplayName(ItemStack itemstack, String name) {
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(name);
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static ItemStack setLore(ItemStack itemStack, String lore) {
//        if (itemStack.hasItemMeta()) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> loresList = new ArrayList<String>();
        loresList.add(lore);
        meta.setLore(loresList);
        itemStack.setItemMeta(meta);
//        }
        return itemStack;
    }

    public static ItemStack addLore(ItemStack itemStack, String lore) {

        ItemMeta meta = itemStack.getItemMeta();
        List<String> loresList = new ArrayList<>();
        if (meta.getLore() != null) {
            loresList = meta.getLore();
        }
        loresList.add(lore);
        meta.setLore(loresList);
        itemStack.setItemMeta(meta);
//        }
        return itemStack;
    }

    public static ItemStack setClassItem(ItemStack itemStack) {
        addLore(itemStack, "classItem");
        return itemStack;
    }

    public static boolean containsLore(ItemStack itemStack, String lore) {
        if (!itemStack.hasItemMeta()) return false;
        ItemMeta meta = itemStack.getItemMeta();
        List<String> loresList = meta.getLore();
        if (loresList == null) return false;
        return loresList.contains(lore);
    }

    public static ItemStack setEffect(ItemStack itemstack, PotionEffectType effectType, int duration, int amplifier) {
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();
        meta.addCustomEffect(new PotionEffect(effectType, duration, amplifier), true);
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static ItemStack setAttributeAttackDamage(ItemStack itemstack, int amountPts) {
        ItemMeta meta = itemstack.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", amountPts, AttributeModifier.Operation.ADD_NUMBER));
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static ItemStack setAttributePlayerBlockRange(ItemStack itemStack, int amountPts) {
        if (itemStack == null) return itemStack;
        if (!itemStack.hasItemMeta()) return itemStack;

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addAttributeModifier(Attribute.PLAYER_BLOCK_INTERACTION_RANGE, new AttributeModifier("generic.playerBlockRange", amountPts, AttributeModifier.Operation.ADD_NUMBER));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setPersistentData(ItemStack itemStack, NamespacedKey namespacedKey, PersistentDataType persistentDataType, Object value) {
        if (itemStack == null) return itemStack;
        if (!itemStack.hasItemMeta()) return itemStack;

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, persistentDataType, value);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static boolean containsPersistentData(ItemStack itemStack, NamespacedKey namespacedKey, PersistentDataType persistentDataType) {
        if (itemStack == null) return false;
        if (!itemStack.hasItemMeta()) return false;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(namespacedKey, persistentDataType) == null) return false;
//        itemMeta.getPersistentDataContainer().getKeys().contains()
        return true;

//        itemStack.setItemMeta(itemMeta);
//        return itemStack;
    }

}
