package com.sweattypalms.skyblock.core.items.types.test.armor;

import com.sweattypalms.skyblock.core.items.builder.Rarity;
import com.sweattypalms.skyblock.core.items.builder.SkyblockItem;
import com.sweattypalms.skyblock.core.items.builder.SkyblockItemType;
import com.sweattypalms.skyblock.core.items.builder.abilities.Ability;
import com.sweattypalms.skyblock.core.items.builder.abilities.AbilityManager;
import com.sweattypalms.skyblock.core.items.builder.abilities.IHasAbility;
import com.sweattypalms.skyblock.core.items.builder.armor.interfaces.IDyedArmor;
import com.sweattypalms.skyblock.core.player.Stats;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LightningLeggings extends SkyblockItem implements IHasAbility, IDyedArmor {
    public static final String ID = "lightning_leggings";
    private static final Map<Stats, Double> stats = new HashMap<>(Map.of(
            Stats.HEALTH, 25d
    ));

    public LightningLeggings() {
        super(
                ID,
                "Lightning Armor Leggings",
                Material.LEATHER_LEGGINGS,
                List.of(
                        "$7Each piece of this armor grants",
                        "$a+50% $7bonus experience when",
                        "$7mining ores."
                ),
                stats,
                Rarity.SPECIAL,
                SkyblockItemType.LEGGINGS
        );
    }

    @Override
    public List<Ability> getAbilities() {
        return List.of(AbilityManager.LIGHTNING_ARMOR_ABILITY);
    }

    @Override
    public String getHexColor() {
        return "0000ff";
    }
}
