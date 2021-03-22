package me.zheroandre.factioncollectors.listener;

import com.massivecraft.factions.event.FactionCreateEvent;
import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.handler.collector.CollectorHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionCreateListener implements Listener {

    private final CollectorHandler collectorHandler;

    public FactionCreateListener(FactionCollectorsPlugin plugin) {
        this.collectorHandler = plugin.getCollectorHandler();
    }

    @EventHandler
    public void factionCreateEvent(FactionCreateEvent e) {
        collectorHandler.initialize(e.getFactionTag()).load();
    }

}
