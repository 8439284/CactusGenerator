package org.ajls.cactusgenerator.Commands;

import org.ajls.cactusgenerator.CactusGenerator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Silent implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasMetadata("silent")) {
                player.setMetadata("silent", new FixedMetadataValue(CactusGenerator.getPlugin(), true));
                player.sendMessage(ChatColor.GREEN + "Placing and breaking blocks " + ChatColor.YELLOW + "won't " + ChatColor.GREEN + "cause block updates");
            }
            else {
                player.removeMetadata("silent", CactusGenerator.getPlugin());
                player.sendMessage(ChatColor.RED + "Placing and breaking blocks " + ChatColor.YELLOW + "will " + ChatColor.RED + "cause block updates");
            }
        }
        return true;
    }
}
