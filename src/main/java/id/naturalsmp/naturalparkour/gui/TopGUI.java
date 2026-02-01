package id.naturalsmp.naturalparkour.gui;

import id.naturalsmp.naturalparkour.Main;
import id.naturalsmp.naturalparkour.top.TopEntry;
import id.naturalsmp.naturalparkour.top.TopManager;
import id.naturalsmp.naturalparkour.utils.ChatUtils;
import id.naturalsmp.naturalparkour.utils.GUIUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TopGUI implements InventoryHolder {

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }

    public static void open(Player player) {
        Inventory inv = GUIUtils.createGUI(new TopGUI(), 54, Main.getInstance().msgs.get("gui.top.title"));
        GUIUtils.fillBorder(inv, Material.BLACK_STAINED_GLASS_PANE);

        int topShown = Main.getInstance().getAConfig().getInt("top-shown");

        // Podium positions similar to TierTopGUI? Or just list?
        // Let's list for now for simplicity, or podium if I want to be fancy.
        // User asked for "polish", so podium is nicer.
        // 1 -> 13, 2 -> 21, 3 -> 23.

        for (int i = 1; i <= topShown; i++) {
            TopEntry entry = TopManager.getInstance().getTop(i, null);
            if (entry == null || entry.getName().equals("--"))
                continue;

            OfflinePlayer p = Bukkit.getOfflinePlayer(entry.getName()); // entry probably stores name

            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            if (p != null)
                meta.setOwningPlayer(p);

            String rankColor = "&f";
            if (i == 1)
                rankColor = "&e&l🥇 #1";
            else if (i == 2)
                rankColor = "&7&l🥈 #2";
            else if (i == 3)
                rankColor = "&6&l🥉 #3";
            else
                rankColor = "&f#" + i;

            meta.displayName(ChatUtils.toComponent(rankColor + " &e" + entry.getName()));

            List<Component> loreComp = new ArrayList<>();
            loreComp.add(Component.empty());
            loreComp.add(ChatUtils.toComponent("&7Score: &f" + entry.getScore() + " jumps"));
            // Time formatting if needed
            int time = entry.getTime();
            if (time > 0) {
                int min = time / 60;
                int sec = time % 60;
                loreComp.add(ChatUtils.toComponent("&7Time: &f" + min + "m " + sec + "s"));
            }

            meta.lore(loreComp);
            head.setItemMeta(meta);

            int slot = getSlotForRank(i);
            if (slot != -1)
                inv.setItem(slot, head);
        }

        player.openInventory(inv);
    }

    private static int getSlotForRank(int rank) {
        switch (rank) {
            case 1:
                return 13;
            case 2:
                return 21;
            case 3:
                return 23;
            default:
                // Fill roughly below
                // 4 -> 29, 5 -> 30...
                if (rank >= 4 && rank <= 10)
                    return 25 + rank;
                return -1;
        }
    }
}
