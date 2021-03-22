package me.zheroandre.factioncollectors.listener;

import com.massivecraft.factions.Faction;
import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.enums.EntityType;
import me.zheroandre.factioncollectors.handler.collector.CollectorHandler;
import me.zheroandre.factioncollectors.handler.faction.FactionHandler;
import me.zheroandre.factioncollectors.object.Collector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private final FactionHandler factionHandler;
    private final CollectorHandler collectorHandler;

    public EntityDeathListener(FactionCollectorsPlugin plugin) {
        this.factionHandler = plugin.getFactionHandler();
        this.collectorHandler = plugin.getCollectorHandler();
    }

    @EventHandler
    public void entityDeathEvent(EntityDeathEvent e) {
        EntityType entityType = EntityType.byEntity(e.getEntityType());
        if (entityType == null) return;

        Faction faction = factionHandler.get(e.getEntity().getLocation());

        if (!factionHandler.has(faction)) return;
        if (!collectorHandler.getEntityTypes().contains(entityType)) return;

        Collector collector = collectorHandler.get(faction.getTag());
        collector.increment(entityType);
    }

}
