package me.zheroandre.factioncollectors.listener;

import com.massivecraft.factions.event.FactionDisbandEvent;
import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.handler.collector.CollectorHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FactionDisbandListener implements Listener {

    private final CollectorHandler collectorHandler;

    public FactionDisbandListener(FactionCollectorsPlugin plugin) {
        this.collectorHandler = plugin.getCollectorHandler();
    }

    @EventHandler
    public void factionDisbandEvent(FactionDisbandEvent e) {
        collectorHandler.delete(e.getFaction().getTag());
    }

    @EventHandler
    public void a(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.getClickedBlock().setType(e.getPlayer().getItemInHand().getType());
        }
    }

}
