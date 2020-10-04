package com.github.minedroidnetwork.data;

import com.google.common.io.Files;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class DataFile
{
    private JavaPlugin plugin;
    private FileConfiguration configuration;
    private boolean hasDefault;
    private File file;
    private String fileName;

    public DataFile(JavaPlugin plugin, String fileName, boolean hasDefault) {
        this.plugin = plugin;
        this.hasDefault = hasDefault;
        this.fileName = fileName;
        file = new File(plugin.getDataFolder() + File.separator + fileName + ".yml");
        reload();
    }

    public void reload() {
        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                if (hasDefault) {
                    plugin.saveResource(fileName + ".yml", false);
                } else {
                    file.createNewFile();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        loadConfig();
    }

    public void loadConfig() {
        configuration = new YamlConfiguration();
        try {
            configuration.loadFromString(Files.toString(file, Charset.forName("UTF-8")));

        } catch (IOException | InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

    public String getString(String key) {
        return configuration.getString(key);
    }

    public double getDouble(String key) {
        return configuration.getDouble(key);
    }

    public void saveConfig() {
        try {
            configuration.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }
}