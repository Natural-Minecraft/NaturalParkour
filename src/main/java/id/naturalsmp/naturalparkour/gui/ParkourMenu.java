package id.naturalsmp.naturalparkour.gui;

import id.naturalsmp.naturalparkour.Main;
import id.naturalsmp.naturalparkour.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ParkourMenu implements InventoryHolder {

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }

    public static void open(Player player) {
        Inventory inv = GUIUtils.createGUI(new ParkourMenu(), 27, Main.getInstance().msgs.get("gui.main.title"));
        GUIUtils.fillBorder(inv, Material.GRAY_STAINED_GLASS_PANE);

        // Quick Start
        ItemStack play = GUIUtils.createItem(Material.SLIME_BALL, "<green><bold>Quick Start",
                Arrays.asList("<gray>Click to start parkour", "<gray>in a random area!"));
        inv.setItem(11, play);

        // Area Selector
        ItemStack areas = GUIUtils.createItem(Material.COMPASS, "<yellow><bold>Select Area",
                Arrays.asList("<gray>Browse available parkour", "<gray>areas and difficulties."));
        inv.setItem(13, areas);

        // Stats / Leaderboard
        ItemStack stats = GUIUtils.createItem(Material.GOLD_INGOT, "<gold><bold>Top Scores",
                Arrays.asList("<gray>View the best parkour", "<gray>players on the server."));
        inv.setItem(15, stats);

        player.openInventory(inv);
    }
}
