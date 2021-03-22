package me.zheroandre.factioncollectors.handler.collector;

import com.massivecraft.factions.Faction;
import lombok.Getter;
import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.enums.EntityType;
import me.zheroandre.factioncollectors.handler.db.DBHandler;
import me.zheroandre.factioncollectors.handler.faction.FactionHandler;
import me.zheroandre.factioncollectors.object.Collector;
import net.herospvp.database.lib.Musician;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectorHandler {

    @Getter private final List<EntityType> entityTypes = Arrays.asList(EntityType.SLIME, EntityType.IRON_GOLEM, EntityType.SILVERFISH, EntityType.ENDERMITE, EntityType.VILLAGER, EntityType.CREEPER);
    @Getter private final List<Collector> collectors = new ArrayList<>();

    private final FactionCollectorsPlugin plugin;
    private final FactionHandler factionHandler;
    private final DBHandler dbHandler;

    private final Musician musician;

    public CollectorHandler(FactionCollectorsPlugin plugin) {
        this.plugin = plugin;
        this.factionHandler = plugin.getFactionHandler();
        this.dbHandler = plugin.getDbHandler();
        this.musician = plugin.getMusician();
    }

    public Collector initialize(String faction) {
        Collector collector = new Collector(plugin, faction);
        this.collectors.add(collector);


        return collector;
    }

    public void delete(String faction) {
        musician.offer(dbHandler.delete(faction));

        Collector collector = this.get(faction);
        this.collectors.remove(collector);
    }

    public Collector get(String faction) {
        for (Collector collector : this.collectors) {
            if (collector.getFaction().equals(faction)) {
                return collector;
            }
        }
        return null;
    }

    public void loadAll() {
        for (Faction faction : factionHandler.factions()) {
            if (factionHandler.has(faction)) {
                this.initialize(faction.getTag()).load();
            }
        }
    }

    public void saveAll() {
        if (this.collectors.size() != 0) musician.offer(dbHandler.saveAll());
    }

}
