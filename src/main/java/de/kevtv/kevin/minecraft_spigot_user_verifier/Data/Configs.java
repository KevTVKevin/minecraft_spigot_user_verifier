package de.kevtv.kevin.minecraft_spigot_user_verifier.Data;

import de.kevtv.kevin.minecraft_spigot_user_verifier.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configs {

    public static File fileMySQL;
    public static YamlConfiguration mySQL;

    public static void setMySQLConfig() {

        fileMySQL = new File("plugins//" + Main.getPlugin().getDescription().getName(), "MySQL.yml");
        mySQL = YamlConfiguration.loadConfiguration(fileMySQL);

        mySQL.addDefault("KevTV.MySQL.host", "localhost");
        mySQL.addDefault("KevTV.MySQL.port", "3306");
        mySQL.addDefault("KevTV.MySQL.user", "root");
        mySQL.addDefault("KevTV.MySQL.password", "pw");
        mySQL.addDefault("KevTV.MySQL.database", "databaseName");
        mySQL.addDefault("KevTV.MySQL.prefix", "uv_");

        try {
            mySQL.options().copyDefaults(true);
            mySQL.save(fileMySQL);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getMySQLData(String data) {
        String correctData = "";

        if(fileMySQL.exists()) {
            if(mySQL.getString("KevTV.MySQL.host") != null) {
                correctData = mySQL.getString("KevTV.MySQL." + data);
            }
        }

        return correctData;

    }

}
