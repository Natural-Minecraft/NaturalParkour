package id.naturalsmp.naturalparkour.utils;

import id.naturalsmp.naturalparkour.Main;
import org.bukkit.Bukkit;

public class NPLogger {

    public static void info(String message) {
        Main.getInstance().getLogger().info(ChatUtils.colorize(message));
    }

    public static void warn(String message) {
        Main.getInstance().getLogger().warning(ChatUtils.colorize(message));
    }

    public static void error(String message) {
        Main.getInstance().getLogger().severe(ChatUtils.colorize(message));
    }

    public static void debug(String message) {
        if (Main.getInstance().getAConfig().getBoolean("debug")) {
            Bukkit.getConsoleSender().sendMessage(ChatUtils.colorize("&8[&bNP-DEBUG&8] &7" + message));
        }
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colorize(message));
    }
}
