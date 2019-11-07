package de.kevtv.kevin.minecraft_spigot_user_verifier.Commands;

import de.kevtv.kevin.minecraft_spigot_user_verifier.Data.MySQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CheckConnectionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();
            if(MySQL.getColumnValues("minecraft_uuid").contains(playerUUID.toString())) {
                sender.sendMessage("Du bist momentan mit der Teamspeak UUID: " + MySQL.getSpecificValue("ts_uuid", "minecraft_uuid", playerUUID.toString()) + " verbunden!");
            } else {
                sender.sendMessage("Du bist nicht verifiziert!");
            }
        } else {
            sender.sendMessage("Dieser Command soll nur von Spielern ausgeführt werden!");
        }

        return true;
    }
}
