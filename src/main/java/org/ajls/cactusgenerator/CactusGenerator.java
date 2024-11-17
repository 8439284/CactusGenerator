package org.ajls.cactusgenerator;

import org.ajls.cactusgenerator.Commands.*;
import org.ajls.cactusgenerator.Commands.Float;
import org.bukkit.plugin.java.JavaPlugin;

public final class CactusGenerator extends JavaPlugin {
    private static CactusGenerator plugin;

    public static CactusGenerator getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new MyListener(), this);
        getCommand("cactus").setExecutor(new Cactus());
        getCommand("sand").setExecutor(new Sand());
        getCommand("silk").setExecutor(new Silk());
        getCommand("silent").setExecutor(new Silent());
        getCommand("float").setExecutor(new Float());
        getCommand("microhid").setExecutor(new MicroHID());
        getCommand("r").setExecutor(new R());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
