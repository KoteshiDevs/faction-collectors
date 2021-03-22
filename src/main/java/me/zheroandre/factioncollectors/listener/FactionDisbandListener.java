package me.zheroandre.factioncollectors.listener;

import com.massivecraft.factions.event.FactionDisbandEvent;
import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.handler.collector.CollectorHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionDisbandListener implements Listener {

    private final CollectorHandler collectorHandler;

    public FactionDisbandListener(FactionCollectorsPlugin plugin) {
        this.collectorHandler = plugin.getCollectorHandler();
    }

    @EventHandler
    public void factionDisbandEvent(FactionDisbandEvent e) {
        collectorHandler.delete(e.getFaction().getTag());
    }

}
