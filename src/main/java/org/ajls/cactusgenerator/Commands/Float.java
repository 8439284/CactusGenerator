package org.ajls.cactusgenerator.Commands;

import org.ajls.cactusgenerator.ItemStackModify;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Float implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            ItemStackModify.addLore(itemInHand, "float");
//            player.getInventory().setItemInMainHand(itemInHand); //item stack is modified and player inventory will change
//            player.getInventory().addItem(itemStack);
            player.sendMessage("floating enchantment enchanted");
        }
        return true;
    }
}
