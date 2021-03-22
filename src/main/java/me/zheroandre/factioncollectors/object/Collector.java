package me.zheroandre.factioncollectors.object;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Role;
import lombok.Getter;
import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.enums.EntityType;
import me.zheroandre.factioncollectors.handler.collector.CollectorHandler;
import me.zheroandre.factioncollectors.handler.db.DBHandler;
import me.zheroandre.factioncollectors.handler.faction.FactionHandler;
import me.zheroandre.factioncollectors.item.EntityItem;
import me.zheroandre.factioncollectors.utils.MessageUtils;
import me.zheroandre.factioncollectors.utils.StringUtils;
import net.herospvp.database.lib.Musician;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Collector {

    private final Musician musician;
    private final Economy economy;
    private final DBHandler dbHandler;
    private final FactionHandler factionHandler;
    private final CollectorHandler collectorHandler;
    private final EntityItem entityItem;

    @Getter private final String faction;

    private final Inventory inventory;

    private int slime, ironGolem, silverfish, endermite, villager, creeper;
    private boolean recruit, normal, moderator, coLeader;

    public Collector(FactionCollectorsPlugin plugin, String faction) {
        this.faction = faction;
        this.inventory = Bukkit.createInventory(null, 36, "Faction Collector");

        this.musician = plugin.getMusician();
        this.economy = plugin.getEconomy();
        this.dbHandler = plugin.getDbHandler();
        this.factionHandler = plugin.getFactionHandler();
        this.collectorHandler = plugin.getCollectorHandler();
        this.entityItem = plugin.getEntityItem();
    }

    /* COLLECTOR INVENTORY */
    public void setup() {
        for (EntityType entityType : collectorHandler.getEntityTypes()) {
            this.inventory.setItem(entityType.getSlot(), entityItem.item(this, entityType));
        }
    }

    public Inventory getInventory() {
        this.setup();

        return this.inventory;
    }

    /* COLLECTOR MOBS */
    public int get(EntityType entityType) {
        switch (entityType) {
            case SLIME:
                return this.slime;
            case IRON_GOLEM:
                return this.ironGolem;
            case SILVERFISH:
                return this.silverfish;
            case ENDERMITE:
                return this.endermite;
            case VILLAGER:
                return this.villager;
            case CREEPER:
                return this.creeper;
            default:
                throw new IllegalStateException("Unexpected value: " + entityType);
        }
    }

    public void increment(EntityType entityType) {
        switch (entityType) {
            case SLIME:
                this.slime += 1;
                break;
            case IRON_GOLEM:
                this.ironGolem += 1;
                break;
            case SILVERFISH:
                this.silverfish += 1;
                break;
            case ENDERMITE:
                this.endermite += 1;
                break;
            case VILLAGER:
                this.villager += 1;
                break;
            case CREEPER:
                this.creeper += 1;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + entityType);
        }
    }

    public void reset(EntityType entityType) {
        switch (entityType) {
            case SLIME:
                this.slime = 0;
                break;
            case IRON_GOLEM:
                this.ironGolem = 0;
                break;
            case SILVERFISH:
                this.silverfish = 0;
                break;
            case ENDERMITE:
                this.endermite = 0;
                break;
            case VILLAGER:
                this.villager = 0;
                break;
            case CREEPER:
                this.creeper = 0;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + entityType);
        }
    }

    public void withdraw(Player player, EntityType entityType) {
        Faction faction = factionHandler.get(player);

        if (entityType != EntityType.CREEPER) {
            economy.depositPlayer(player, (entityType.getPrize() * this.get(entityType)));
            player.sendMessage(StringUtils.color(MessageUtils.SuccessMessage.WITHDRAW_SUCCESS.MESSAGE
                    .replace("[ENTITY-AMOUNT]", StringUtils.formatLetters(this.get(entityType)))
                    .replace("[ENTITY-TYPE]", entityType.toString())
                    .replace("[PRIZE]", String.valueOf(StringUtils.formatLetters((entityType.getPrize() * this.get(entityType)))))
            ));
        } else {
            factionHandler.addTnt(faction, this.get(entityType));
            player.sendMessage(StringUtils.color(MessageUtils.SuccessMessage.WITHDRAW_SUCCESS_TNT.MESSAGE)
                    .replace("[ENTITY-AMOUNT]", StringUtils.formatLetters(this.get(EntityType.CREEPER)))
                    .replace("[ENTITY-TYPE]", entityType.toString())
            );
        }

        this.reset(entityType);
        this.inventory.setItem(entityType.getSlot(), entityItem.item(this, entityType));
    }

    /* COLLECTOR PERMS */
    public boolean hasPerms(Player player) {
        Role role = factionHandler.role(player);

        switch (role) {
            case RECRUIT:
                return this.recruit;
            case NORMAL:
                return this.normal;
            case MODERATOR:
                return this.moderator;
            case COLEADER:
                return this.coLeader;
            case LEADER:
                return true;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
    }

    public void setPerms(Role role) {
        switch (role) {
            case RECRUIT:
                this.recruit = !this.recruit;
                break;
            case NORMAL:
                this.normal = !this.normal;
                break;
            case MODERATOR:
                this.moderator = !this.moderator;
                break;
            case COLEADER:
                this.coLeader = !this.coLeader;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
    }

    /* LOAD & GET - IMPORT ALL VALUE */
    public void load() {
        musician.offer(dbHandler.load(this));
    }

    public Object[] getAllValues() {
        return new Object[]{this.slime, this.ironGolem, this.silverfish, this.endermite, this.villager, this.creeper, this.recruit, this.normal, this.moderator, this.coLeader};
    }

    public void importAllValues(Object[] values) {
        if (values.length == 10) {
            this.slime = (int) values[0];
            this.ironGolem = (int) values[1];
            this.silverfish = (int) values[2];
            this.endermite = (int) values[3];
            this.villager = (int) values[4];
            this.creeper = (int) values[5];

            this.recruit = (boolean) values[6];
            this.normal = (boolean) values[7];
            this.moderator = (boolean) values[8];
            this.coLeader = (boolean) values[9];
        }
    }

}
