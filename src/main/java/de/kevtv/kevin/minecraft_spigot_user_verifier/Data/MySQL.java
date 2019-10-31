package de.kevtv.kevin.minecraft_spigot_user_verifier.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    public static Connection connection;

    public static void connectToMySQL() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + Configs.getMySQLData("host") + ":" + Configs.getMySQLData("port") + "/" + Configs.getMySQLData("database") + "?autoReconnect=true", Configs.getMySQLData("user"), Configs.getMySQLData("password"));
            System.out.println("Die Verbindung zur MySQL wurde hergestellt!");
        } catch (SQLException e) {
            System.out.println("Die Verbindung zur MySQL ist fehlgeschlagen! Fehler: " + e.getMessage());
        }
    }

    public static void setupMySQLTables() {
        updateMySQL("CREATE TABLE IF NOT EXISTS " + Configs.getMySQLData("prefix") + "verify(id int(11) NOT NULL AUTO_INCREMENT, minecraft_uuid varchar(100) NOT NULL, ts_uuid varchar(100) NOT NULL, verification_code varchar(100) NOT NULL, verified int(11) NOT NULL, primary key (id))");
    }

    public static void updateMySQL(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectMySQL();
            connectToMySQL();
        }
    }

    public static void disconnectMySQL() {
        if(connection != null) {
            try {
                connection.close();
                System.out.println("Die Verbindung zur MySQL wurde erfolgreich beendet!");
            } catch (SQLException e) {
                System.out.println("Fehler beim beenden der Verbindung zur MySQL! Fehler: " + e.getMessage());
            }
        }
    }

}
