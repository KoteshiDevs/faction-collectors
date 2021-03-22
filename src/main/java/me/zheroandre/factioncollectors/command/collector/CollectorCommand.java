package me.zheroandre.factioncollectors.command.collector;

import com.massivecraft.factions.Faction;
import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.handler.collector.CollectorHandler;
import me.zheroandre.factioncollectors.handler.faction.FactionHandler;
import me.zheroandre.factioncollectors.object.Collector;
import me.zheroandre.factioncollectors.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CollectorCommand implements CommandExecutor {

    private final FactionHandler factionHandler;
    private final CollectorHandler collectorHandler;

    public CollectorCommand(FactionCollectorsPlugin plugin) {
        this.factionHandler = plugin.getFactionHandler();
        this.collectorHandler = plugin.getCollectorHandler();

        plugin.getCommand("collector").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (factionHandler.has(player)) {
                Faction faction = factionHandler.get(player);
                Collector collector = collectorHandler.get(faction.getTag());
                if (collector.hasPerms(player)) {
                    player.openInventory(collector.getInventory());
                } else {
                    sender.sendMessage(MessageUtils.message(MessageUtils.ErrorMessage.NO_FACTION_PERMISSION));
                }
            } else {
                sender.sendMessage(MessageUtils.message(MessageUtils.ErrorMessage.NO_FACTION));
            }
        } else {
            sender.sendMessage(MessageUtils.message(MessageUtils.ErrorMessage.NO_CONSOLE));
        }
        return true;
    }

}
