package id.naturalsmp.naturalparkour.utils;

import id.naturalsmp.naturalparkour.Main;
import org.bukkit.Bukkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NPLogger {

    public static void info(String message) {
        Main.getInstance().getLogger().info(stripColor(ChatUtils.colorize(message)));
    }

    public static void warn(String message) {
        Main.getInstance().getLogger().warning(stripColor(ChatUtils.colorize(message)));
    }

    public static void error(String message) {
        Main.getInstance().getLogger().severe(stripColor(ChatUtils.colorize(message)));
    }

    public static void debug(String message) {
        if (Main.getInstance().getAConfig().getBoolean("debug")) {
            Bukkit.getConsoleSender().sendMessage(ChatUtils.colorize("&8[&bNP-DEBUG&8] &7" + message));
        }
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatUtils.colorize(message));
    }

    private static String stripColor(String message) {
        // Simple strip for legacy logger to avoid weird characters
        return org.bukkit.ChatColor.stripColor(message);
    }
}
