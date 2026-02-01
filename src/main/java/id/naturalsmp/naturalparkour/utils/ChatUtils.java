package id.naturalsmp.naturalparkour.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer SECTION_SERIALIZER = LegacyComponentSerializer.legacySection();
    private static final LegacyComponentSerializer AMPERSAND_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    public static String colorize(String message) {
        if (message == null || message.isEmpty())
            return "";

        if (message.contains("<")) {
            try {
                return SECTION_SERIALIZER.serialize(MINI_MESSAGE.deserialize(message));
            } catch (Exception ignored) {
            }
        }

        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            try {
                String hexCode = matcher.group(1);
                matcher.appendReplacement(buffer, ChatColor.of("#" + hexCode).toString());
            } catch (Exception e) {
                matcher.appendReplacement(buffer, "");
            }
        }
        matcher.appendTail(buffer);

        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }

    public static Component toComponent(String message) {
        if (message == null || message.isEmpty())
            return Component.empty();

        if (message.contains("<")) {
            try {
                return MINI_MESSAGE.deserialize(message);
            } catch (Exception e) {
                return SECTION_SERIALIZER.deserialize(colorize(message).replace("&", "§"));
            }
        }
        return SECTION_SERIALIZER.deserialize(colorize(message).replace("&", "§"));
    }

    public static List<String> colorize(List<String> list) {
        if (list == null)
            return null;
        List<String> colored = new ArrayList<>();
        for (String s : list) {
            colored.add(colorize(s));
        }
        return colored;
    }

    public static String stripColor(String message) {
        if (message == null)
            return "";
        return ChatColor.stripColor(colorize(message));
    }
}
