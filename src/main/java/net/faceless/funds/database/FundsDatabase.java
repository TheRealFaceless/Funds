package net.faceless.funds.database;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.UUID;

public class FundsDatabase {

    private final JavaPlugin plugin;
    private Connection connection;

    public FundsDatabase(@NotNull final JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        connection = connection();
        if (connection == null) return;

        try {
            final PreparedStatement createPlayerTable = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS players (" + "`UUID` varchar(36) NOT NULL, " + "`balance` varchar(64) NOT NULL, " + "PRIMARY KEY(`UUID`));"
            );
            createPlayerTable.executeUpdate();
            createPlayerTable.close();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public void set(@NotNull final UUID uuid, final BigDecimal balance) {
        connection = connection();
        if (connection == null) return;

        final String bal = balance.toString();

        try {
            final PreparedStatement select = connection.prepareStatement(
                    "SELECT * FROM players WHERE `UUID` = ?;"
            );
            select.setString(1, uuid.toString());

            final ResultSet resultSet = select.executeQuery();

            // IF THEIR NOT ALREADY AN ENTRY FOR THE PLAYER
            if (!resultSet.isBeforeFirst()) {
                final PreparedStatement insert = connection.prepareStatement(
                        "INSERT INTO players (`UUID`,`balance`) VALUES (?,?);"
                );

                insert.setString(1, uuid.toString());
                insert.setString(2, bal);
                insert.executeUpdate();
                insert.close();
            }

            // UPDATE THE ENTRY INSTEAD OF CREATING A NEW ONE FOR THE PLAYER
            else {
                final PreparedStatement update = connection.prepareStatement(
                        "UPDATE players SET `balance` = ? WHERE `UUID` = ?;"
                );

                update.setString(1, bal);
                update.setString(2, uuid.toString());
                update.executeUpdate();
                update.close();
            }

            select.close();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public @NotNull BigDecimal getBalance(@NotNull final UUID uuid) {
        connection = connection();
        if (connection == null) return BigDecimal.ZERO;
        BigDecimal result = BigDecimal.ZERO;

        try {
            final PreparedStatement select = connection.prepareStatement("SELECT * FROM players WHERE `UUID`='" + uuid + "';");

            final ResultSet resultSet = select.executeQuery();
            final String resultString = resultSet.getString("balance");

            if (resultString != null)
                try { result = new BigDecimal(resultString); }
                catch (NumberFormatException ignored) {}

            resultSet.close();
            select.close();
        }

        catch (SQLException e) { e.printStackTrace(); }
        return result;
    }

    public boolean playerExists(Player player) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void closeConnection() {
        connection = connection();
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connection() {
        File data = new File(plugin.getDataFolder().getAbsolutePath(), "data");
        if(!data.exists()) {data.mkdir();}

        final File file = new File(data, "funds.db");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (connection != null && !connection.isClosed()) return connection;
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            return connection;
        }
        catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

}