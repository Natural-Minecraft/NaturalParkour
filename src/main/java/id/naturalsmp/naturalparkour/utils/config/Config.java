package id.naturalsmp.naturalparkour.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Simple configuration wrapper for NaturalParkour
 * Provides backward compatibility with the old ajUtils Config class
 */
public class Config {

    private final JavaPlugin plugin;
    private FileConfiguration config;

    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public Object get(String path) {
        return config.get(path);
    }
}
