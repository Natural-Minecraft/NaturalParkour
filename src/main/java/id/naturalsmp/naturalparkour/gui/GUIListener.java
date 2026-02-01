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
        if (event.getInventory().getHolder() instanceof ParkourMenu) {
            event.setCancelled(true);
            if (!(event.getWhoClicked() instanceof Player))
                return;
            Player player = (Player) event.getWhoClicked();

            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR)
                return;

            if (item.getType() == Material.SLIME_BALL) {
                player.closeInventory();
                player.performCommand("npk start");
            } else if (item.getType() == Material.COMPASS) {
                player.closeInventory();
                player.performCommand("npk areas");
            } else if (item.getType() == Material.GOLD_INGOT) {
                player.closeInventory();
                player.performCommand("npk top");
            }
        }
    }
}
