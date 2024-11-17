package org.ajls.cactusgenerator.Commands;

import org.ajls.cactusgenerator.ItemStackModify;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Cactus implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack itemStack = new ItemStack(Material.CACTUS);
            ItemStackModify.setLore(itemStack, "cactus_generator");
            player.getInventory().addItem(itemStack);
            player.sendMessage("cactus_generator given to you");
        }
        return true;
    }
}
