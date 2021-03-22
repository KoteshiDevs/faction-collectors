package me.zheroandre.factioncollectors.handler.db;

import me.zheroandre.factioncollectors.FactionCollectorsPlugin;
import me.zheroandre.factioncollectors.handler.collector.CollectorHandler;
import me.zheroandre.factioncollectors.object.Collector;
import net.herospvp.database.lib.Musician;
import net.herospvp.database.lib.items.Notes;
import net.herospvp.database.lib.items.Papers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHandler {

    private final FactionCollectorsPlugin plugin;

    private final Notes notes;

    public DBHandler(FactionCollectorsPlugin plugin) {
        this.plugin = plugin;

        Musician musician = plugin.getMusician();

        this.notes = new Notes("collectors");

        musician.offer(startup());
    }

    public Papers startup() {
        return (connection, instrument) -> {
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        notes.createTable(
                                new String[]{"FACTION VARCHAR(20)", "SLIME INTEGER", "IRON_GOLEM INTEGER", "SILVERFISH INTEGER", "ENDERMITE INTEGER", "VILLAGER INTEGER", "CREEPER INTEGER", "RECRUIT BOOLEAN", "NORMAL BOOLEAN", "MODERATOR BOOLEAN", "CO_LEADER BOOLEAN"}
                        )
                );
                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                instrument.close(ps);
            }
        };
    }

    public Papers delete(String faction) {
        return (connection, instrument) -> {
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        "DELETE FROM " + notes.getTable() + " WHERE FACTION = ?;"
                );
                ps.setString(1, faction);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instrument.close(ps);
            }
        };
    }

    public Papers load(Collector collector) {
        return (connection, instrument) -> {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement(
                        notes.selectAllWhere("FACTION", collector.getFaction())
                );
                rs = ps.executeQuery();

                if (!rs.next()) {
                    ps = connection.prepareStatement(
                            notes.insert(
                                    new String[]{"FACTION", "SLIME", "IRON_GOLEM", "SILVERFISH", "ENDERMITE", "VILLAGER", "CREEPER", "RECRUIT", "NORMAL", "MODERATOR", "CO_LEADER"},
                                    new Object[]{collector.getFaction(), 0, 0, 0, 0, 0, 0, true, true, true, true}
                            )
                    );
                    ps.executeUpdate();
                } else {
                    collector.importAllValues(new Object[]{rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getBoolean(8), rs.getBoolean(9), rs.getBoolean(10), rs.getBoolean(11)});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instrument.close(ps, rs);
            }
        };
    }

    public Papers saveAll() {
        CollectorHandler collectorHandler = plugin.getCollectorHandler();

        return (connection, instrument) -> {
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(
                        notes.pendingUpdate(
                                new String[]{"SLIME", "IRON_GOLEM", "SILVERFISH", "ENDERMITE", "VILLAGER", "CREEPER", "RECRUIT", "NORMAL", "MODERATOR", "CO_LEADER"}, "FACTION"
                        )
                );

                for (Collector collector : collectorHandler.getCollectors()) {
                    for (int i = 0; i < 10; i++) {
                        if (i <= 5) {
                            ps.setInt(i + 1, (Integer) collector.getAllValues()[i]);
                        } else {
                            ps.setBoolean(i + 1, (Boolean) collector.getAllValues()[i]);
                        }
                    }
                    ps.setString(11, collector.getFaction());
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instrument.close(ps);
            }
        };
    }

}
