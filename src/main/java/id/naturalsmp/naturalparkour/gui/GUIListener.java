package id.naturalsmp.naturalparkour.gui;

import id.naturalsmp.naturalparkour.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    private final Main plugin;

    public GUIListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (event.getInventory().getHolder() instanceof ParkourMenu) {
            event.setCancelled(true);
            if (item == null || item.getType() == Material.AIR)
                return;

            if (item.getType() == Material.SLIME_BALL) {
                player.closeInventory();
                player.performCommand("npk start");
            } else if (item.getType() == Material.COMPASS) {
                AreaGUI.open(player);
            } else if (item.getType() == Material.GOLD_INGOT) {
                TopGUI.open(player);
            }
        } else if (event.getInventory().getHolder() instanceof AreaGUI) {
            event.setCancelled(true);
            if (item == null || item.getType() == Material.AIR)
                return;

            // Extract area name from item display name
            // Format: <yellow><bold>AreaName
            // We need to strip colors.
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                // Suppress deprecation for getDisplayName as we need string for stripping
                @SuppressWarnings("deprecation")
                String rawName = item.getItemMeta().getDisplayName();
                String name = net.md_5.bungee.api.ChatColor.stripColor(rawName);

                // stripColor might leave spaces if format was weird, but here it should be
                // fine.
                // Actually, using ChatUtils.stripColor is safer if available.
                // Checking Imports... Main uses ChatColor. But we removed BungeeCord dependency
                // check?
                // ChatUtils.stripColor is available in utils.
                name = id.naturalsmp.naturalparkour.utils.ChatUtils.stripColor(name);

                player.closeInventory();
                player.performCommand("npk start " + name);
            }
        } else if (event.getInventory().getHolder() instanceof TopGUI) {
            event.setCancelled(true);
        }
    }
}
