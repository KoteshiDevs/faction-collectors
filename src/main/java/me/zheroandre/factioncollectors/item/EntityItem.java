package me.zheroandre.factioncollectors.item;

import de.tr7zw.nbtapi.NBTItem;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.enums.EntityType;
import me.zheroandre.factioncollectors.object.Collector;
import me.zheroandre.factioncollectors.utils.StringUtils;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EntityItem {

    private final HeadDatabaseAPI headDatabaseAPI;

    public EntityItem(FactionCollectorsPlugin plugin) {
        this.headDatabaseAPI = plugin.getHeadDatabaseAPI();
    }

    public ItemStack item(Collector collector, EntityType entityType) {
        ItemStack itemStack = headDatabaseAPI.getItemHead(String.valueOf(entityType.getHeadID()));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(StringUtils.color(" &d" + entityType.toString() + " &8( &7x" + StringUtils.formatLetters(collector.get(entityType)) + " &8) "));
        itemMeta.setLore(
                Arrays.asList(
                        StringUtils.color(""),
                        StringUtils.color(" &f&l* &dLEFT-CLICK &7per ritirare il valore di questa &dENTITA'"),
                        StringUtils.color("")
                )
        );

        itemMeta.addItemFlags(
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_UNBREAKABLE
        );
        itemStack.setItemMeta(itemMeta);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("EntityType", entityType.toString());
        nbtItem.applyNBT(itemStack);

        return itemStack;
    }

}
