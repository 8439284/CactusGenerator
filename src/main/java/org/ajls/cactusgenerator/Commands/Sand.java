package org.ajls.cactusgenerator.Commands;

import org.ajls.cactusgenerator.ItemStackModify;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack itemStack = new ItemStack(Material.SAND);
            ItemStackModify.setLore(itemStack, "floating_sand");
            player.getInventory().addItem(itemStack);
            player.sendMessage("floating_sand given to you");
        }
        return true;
    }
}
