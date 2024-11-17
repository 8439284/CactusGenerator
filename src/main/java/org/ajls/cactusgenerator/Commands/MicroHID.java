package org.ajls.cactusgenerator.Commands;

import org.ajls.cactusgenerator.ItemStackModify;
import org.ajls.cactusgenerator.NameSpacedKeys;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class MicroHID implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack itemStack = new ItemStack(Material.LIGHTNING_ROD);
            ItemStackModify.setAttributePlayerBlockRange(itemStack, -5);
            ItemStackModify.setLore(itemStack, "Micro H.I.D.");
            ItemStackModify.setPersistentData(itemStack, NameSpacedKeys.MICRO_HID, PersistentDataType.BOOLEAN, true);
            player.getInventory().addItem(itemStack);
            player.sendMessage("Micro H.I.D. given to you. Use it wisely.");

        }
        return true;
    }
}
