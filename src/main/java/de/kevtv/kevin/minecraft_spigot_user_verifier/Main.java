package de.kevtv.kevin.minecraft_spigot_user_verifier;

import de.kevtv.kevin.minecraft_spigot_user_verifier.Data.Configs;
import de.kevtv.kevin.minecraft_spigot_user_verifier.Data.MySQL;
import de.kevtv.kevin.minecraft_spigot_user_verifier.Commands.CheckConnectionCommand;
import de.kevtv.kevin.minecraft_spigot_user_verifier.Commands.RemoveConnectionCommand;
import de.kevtv.kevin.minecraft_spigot_user_verifier.Commands.SetupConnectionCommand;
import de.kevtv.kevin.minecraft_spigot_user_verifier.Events.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setPlugin(this);

        Configs.setMySQLConfig();

        MySQL.connectToMySQL();
        MySQL.setupMySQLTables();

        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MySQL.disconnectMySQL();

    }

    public static Main getPlugin() {
        return plugin;
    }

    public static void setPlugin(Main plugin) {
        Main.plugin = plugin;
    }

    private void registerCommands() {

        Objects.requireNonNull(getPlugin().getCommand("checkConnection")).setExecutor(new CheckConnectionCommand());
        Objects.requireNonNull(getPlugin().getCommand("setupConnection")).setExecutor(new SetupConnectionCommand());
        Objects.requireNonNull(getPlugin().getCommand("removeConnection")).setExecutor(new RemoveConnectionCommand());

    }

    private void registerEvents() {

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), getPlugin());

    }

}
