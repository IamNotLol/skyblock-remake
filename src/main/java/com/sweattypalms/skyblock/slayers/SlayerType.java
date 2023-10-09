package com.sweattypalms.skyblock.slayers;

import lombok.Getter;
import org.bukkit.entity.EntityType;

@Getter
public enum SlayerType {
    REVENANT_HORROR("Zombies", EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER),
    TARANTULA_BROODFATHER("Spiders", EntityType.SPIDER, EntityType.CAVE_SPIDER),
    SVEN_PACKMASTER("Svens", EntityType.WOLF),
    VOIDLING("Endermen", EntityType.ENDERMAN),
    ;

    private final String mobType;
    private final EntityType[] possibleHarvestableEntities;
    SlayerType(String mobType, EntityType ... entityTypes) {
        this.mobType = mobType;
        this.possibleHarvestableEntities = entityTypes;
    }
}