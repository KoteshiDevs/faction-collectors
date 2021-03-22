package me.zheroandre.factioncollectors.enums;

import lombok.Getter;

public enum EntityType {
    SLIME(11, 961, 1),
    IRON_GOLEM(12, 955, 1),
    SILVERFISH(13, 959, 1),
    ENDERMITE(14, 954, 1),
    VILLAGER(15, 943, 275),
    CREEPER(22, 947, 0);

    @Getter private final int slot, headID;
    @Getter private final long prize;

    EntityType(int slot, int headID, long prize) {
        this.slot = slot;
        this.headID = headID;
        this.prize = prize;
    }

    public static EntityType byEntity(org.bukkit.entity.EntityType entityType) {
        switch (entityType) {
            case SLIME:
                return SLIME;
            case IRON_GOLEM:
                return IRON_GOLEM;
            case SILVERFISH:
                return SILVERFISH;
            case ENDERMITE:
                return ENDERMITE;
            case VILLAGER:
                return VILLAGER;
            case CREEPER:
                return CREEPER;
            default:
                return null;
        }
    }

}
