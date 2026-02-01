package id.naturalsmp.naturalparkour.gui;

import id.naturalsmp.naturalparkour.Main;
import id.naturalsmp.naturalparkour.game.PkArea;
import id.naturalsmp.naturalparkour.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class AreaGUI implements InventoryHolder {

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }

    public static void open(Player player) {
        List<PkArea> areas = Main.getInstance().man.getAreas();
        int size = Math.min(54, ((areas.size() / 9) + 1) * 9);

        Inventory inv = GUIUtils.createGUI(new AreaGUI(), size, Main.getInstance().msgs.get("gui.area.title"));
        GUIUtils.fillBorder(inv, Material.BLACK_STAINED_GLASS_PANE);

        int slot = 0;
        for (PkArea area : areas) {
            if (slot >= size)
                break;

            Material icon = Material.PAPER; // Default
            // We could add custom icon support in PkArea later

            String difficulty = area.getDifficulty().toString();
            int jumps = -1; // We might need to expose jumps count in PkArea, let's assume unknown for now
                            // or fetch if possible.
            // PkArea doesn't seem to store jumps count directly exposed easily or I missed
            // it.
            // Actually PkArea has getFallPos, getPos1, getPos2, etc. Jumps are stored in
            // JumpsManager or inferred.

            ItemStack item = GUIUtils.createItem(icon, "<yellow><bold>" + area.getName(),
                    Arrays.asList(
                            "<gray>Difficulty: <white>" + difficulty,
                            "",
                            "<green>Click to play!"));
            inv.setItem(slot, item);
            slot++;
        }

        player.openInventory(inv);
    }
}
