package me.zheroandre.factioncollectors.handler.faction;

import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.Role;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionHandler {

    public List<Faction> factions() {
        return Factions.getInstance().getAllFactions();
    }

    public boolean has(Player player) {
        return FPlayers.getInstance().getByPlayer(player).hasFaction();
    }

    public boolean has(Faction faction) {
        return !faction.isWilderness() && !faction.isSafeZone() && !faction.isWarZone();
    }

    public Faction get(Player player) {
        return FPlayers.getInstance().getByPlayer(player).getFaction();
    }

    public Faction get(Location location) {
        return Board.getInstance().getFactionAt(new FLocation(location));
    }

    public Role role(Player player) {
        return FPlayers.getInstance().getByPlayer(player).getRole();
    }

    public void addTnt(Faction faction, int value) {
        faction.addTnt(value);
    }

}
