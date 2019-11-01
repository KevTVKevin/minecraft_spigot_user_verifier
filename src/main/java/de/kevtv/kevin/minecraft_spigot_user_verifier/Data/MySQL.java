package de.kevtv.kevin.minecraft_spigot_user_verifier.Data;

import java.sql.*;
import java.util.ArrayList;

public class MySQL {

    private static Connection connection;

    public static void connectToMySQL() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + Configs.getMySQLData("host") + ":" + Configs.getMySQLData("port") + "/" + Configs.getMySQLData("database") + "?autoReconnect=true", Configs.getMySQLData("user"), Configs.getMySQLData("password"));
            System.out.println("Die Verbindung zur MySQL wurde hergestellt!");
        } catch (SQLException e) {
            System.out.println("Die Verbindung zur MySQL ist fehlgeschlagen! Fehler: " + e.getMessage());
        }
    }

    public static void setupMySQLTables() {
        updateMySQL("CREATE TABLE IF NOT EXISTS " + Configs.getMySQLData("prefix") + "verify(id int(11) NOT NULL AUTO_INCREMENT, minecraft_uuid varchar(255) NOT NULL, ts_uuid varchar(255) NOT NULL, verification_code varchar(255) NOT NULL, verified int(11) NOT NULL, primary key (id))");
    }

    public static void insertValuesToTable(String minecraftUUID, String tsUUID, String verificationCode) {
        updateMySQL("INSERT INTO " + Configs.getMySQLData("prefix") + "verify(minecraft_uuid, ts_uuid, verification_code, verified) VALUES ('" + minecraftUUID + "','" + tsUUID + "','" + verificationCode + "','0')");
    }

    public static ArrayList getColumnValues(String column) {
        ArrayList<String> minecraft_uuids = new ArrayList<>();

        try {
            String query = "SELECT " + column + " FROM " + Configs.getMySQLData("prefix") + "verify";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                minecraft_uuids.add(resultSet.getString(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            disconnectMySQL();
            connectToMySQL();
        }

        return minecraft_uuids;
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
