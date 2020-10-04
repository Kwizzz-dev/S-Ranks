package com.github.minedroidnetwork;

import com.github.minedroidnetwork.data.DataFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;

public class Core extends JavaPlugin
{
    private static Connection database;
    private static DataFile config;

    @Override
    public void onEnable() {
        // Startup Func.
        this.config = new DataFile(this, "config", true);

        database = loadDatabase();
    }

    @Override
    public void onDisable() {
        // Shutdown Func.
    }

    public Connection loadDatabase(){
        database = null;
        getLogger().log(Level.INFO, "Attempting to connect to SQL database...");
        try{
            String address = config.getString("my-sql.address");
            String db = config.getString("my-sql.database");
            String username = config.getString("my-sql.username");
            String password = config.getString("my-sql.password");

            database = DriverManager.getConnection("jdbc:mysql://" + address + "/" + db, username, password);

            getLogger().log(Level.INFO, "Successfully connected to SQL database.");
        }catch (Exception e){
            getLogger().log(Level.SEVERE, "Failed to connect to SQL database, please check the database credentials and status.");
        }
        return database;
    }
}
