package org.ajls.cactusgenerator.Commands;

import org.ajls.cactusgenerator.utils.CommandU;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class R implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandU.runCommand(sender, "plugman reload CactusGenerator"); //"reload confirm"
//        sender.
        return true;
    }
}
