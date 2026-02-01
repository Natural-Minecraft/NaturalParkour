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

        // 1. Select Area (Left - 11)
        ItemStack areas = GUIUtils.createItem(Material.COMPASS,
                Main.getInstance().msgs.get("gui.main.items.select.name"),
                Arrays.asList(Main.getInstance().msgs.get("gui.main.items.select.lore").split("\n")));
        inv.setItem(11, areas);

        // 2. Quick Start (Center - 13)
        ItemStack play = GUIUtils.createItem(Material.SLIME_BALL,
                Main.getInstance().msgs.get("gui.main.items.quickstart.name"),
                Arrays.asList(Main.getInstance().msgs.get("gui.main.items.quickstart.lore").split("\n")));
        inv.setItem(13, play);

        // 3. Top Scores (Right - 15)
        ItemStack stats = GUIUtils.createItem(Material.GOLD_INGOT,
                Main.getInstance().msgs.get("gui.main.items.top.name"),
                Arrays.asList(Main.getInstance().msgs.get("gui.main.items.top.lore").split("\n")));
        inv.setItem(15, stats);

        player.openInventory(inv);
    }
}
