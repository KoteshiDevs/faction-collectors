package me.zheroandre.factioncollectors.listener;

import com.massivecraft.factions.Faction;
import de.tr7zw.nbtapi.NBTItem;
import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.enums.EntityType;
import me.zheroandre.factioncollectors.handler.collector.CollectorHandler;
import me.zheroandre.factioncollectors.handler.faction.FactionHandler;
import me.zheroandre.factioncollectors.object.Collector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private final FactionHandler factionHandler;
    private final CollectorHandler collectorHandler;

    public InventoryClickListener(FactionCollectorsPlugin plugin) {
        this.factionHandler = plugin.getFactionHandler();
        this.collectorHandler = plugin.getCollectorHandler();
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();

        if (itemStack == null) return;
        NBTItem nbtItem = new NBTItem(itemStack);

        if (!factionHandler.has(player)) return;
        Faction faction = factionHandler.get(player);
        Collector collector = collectorHandler.get(faction.getTag());

        if (inventory.getType() != InventoryType.PLAYER) {
            if (inventory.getTitle().equals(collector.getInventory().getTitle())) {
                e.setCancelled(true);
                if (nbtItem.hasKey("EntityType")) {
                    EntityType entityType = EntityType.valueOf(nbtItem.getString("EntityType"));
                    if (collector.get(entityType) != 0) collector.withdraw(player, entityType);
                }
            }
        } else {
            Inventory openInventory = player.getOpenInventory().getTopInventory();
            if (openInventory.getTitle().equals(collector.getInventory().getTitle())) {
                e.setCancelled(true);
            }
        }
    }

}
