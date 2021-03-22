package me.zheroandre.factioncollectors;

import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.zheroandre.factioncollectors.command.collector.CollectorCommand;
import me.zheroandre.factioncollectors.handler.collector.CollectorHandler;
import me.zheroandre.factioncollectors.handler.db.DBHandler;
import me.zheroandre.factioncollectors.handler.faction.FactionHandler;
import me.zheroandre.factioncollectors.item.EntityItem;
import me.zheroandre.factioncollectors.listener.EntityDeathListener;
import me.zheroandre.factioncollectors.listener.FactionCreateListener;
import me.zheroandre.factioncollectors.listener.FactionDisbandListener;
import me.zheroandre.factioncollectors.listener.InventoryClickListener;
import net.herospvp.database.DatabaseLib;
import net.herospvp.database.lib.Director;
import net.herospvp.database.lib.Musician;
import net.herospvp.database.lib.items.Instrument;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public final class FactionCollectorsPlugin extends JavaPlugin {

    private FactionCollectorsPlugin instance;

    private Economy economy;
    private HeadDatabaseAPI headDatabaseAPI;

    private Director director;
    private Musician musician;

    private DBHandler dbHandler;
    private FactionHandler factionHandler;
    private CollectorHandler collectorHandler;

    private EntityItem entityItem;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        if (!this.setupEconomyHook()) return;

        this.headDatabaseAPI = new HeadDatabaseAPI();

        director = getPlugin(DatabaseLib.class).getDirector();
        Instrument instrument = new Instrument(
                getConfig().getString("db.ip"),
                getConfig().getString("db.port"),
                getConfig().getString("db.database"),
                getConfig().getString("db.user"),
                getConfig().getString("db.password"),
                getConfig().getString("db.url"),
                getConfig().getString("db.driver"),
                null,
                true,
                getConfig().getInt("db.max-pool-size")
        );
        this.director.addInstrument("faction-collectors", instrument);
        this.musician = new Musician(director, instrument, false);

        this.dbHandler = new DBHandler(this);
        this.factionHandler = new FactionHandler();
        this.collectorHandler = new CollectorHandler(this);

        this.entityItem = new EntityItem(this);

        this.registerCommand();
        this.registerListener();

        this.collectorHandler.loadAll();
    }

    @Override
    public void onDisable() {
        this.collectorHandler.saveAll();
    }

    private boolean setupEconomyHook() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    private void registerCommand() {
        new CollectorCommand(this);
    }

    private void registerListener() {
        Arrays.asList(
                new EntityDeathListener(this),
                new InventoryClickListener(this),

                new FactionCreateListener(this),
                new FactionDisbandListener(this)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }

}
