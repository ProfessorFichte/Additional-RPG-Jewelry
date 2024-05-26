package net.additional_jewelry.config;

import net.additional_jewelry.items.Items;
import net.jewelry.config.LootConfig;
import net.jewelry.config.ItemConfig;

import java.util.List;

public class Default {
    public static final ItemConfig items;
    public static final LootConfig loot;

    static {
        items = new ItemConfig();
        loot = new LootConfig();

        var jewelry_tier_1 = "jewelry_tier_1";
        loot.item_groups.put(jewelry_tier_1, new LootConfig.ItemGroup(List.of(
                Items.cactea_ring.id().toString(),
                Items.driptstone_necklace.id().toString()
                ),
                        1)
                        .chance(0.3F)
        );

        var jewelry_tier_2 = "jewelry_tier_2";
        loot.item_groups.put(jewelry_tier_2, new LootConfig.ItemGroup(List.of(
                Items.ocean_ring.id().toString(),
                Items.ocean_necklace.id().toString(),
                Items.earth_necklace.id().toString(),
                Items.earth_ring.id().toString(),
                //Items.sky_necklace.id().toString(),
                //Items.sky_ring.id().toString(),
                Items.rage_necklace.id().toString(),
                Items.rage_ring.id().toString()
                ),
                        1)
                        .chance(0.2F)
        );

        var jewelry_tier_3 = "jewelry_tier_3";
        loot.item_groups.put(jewelry_tier_3, new LootConfig.ItemGroup(List.of(
                Items.netherite_ocean_ring.id().toString(),
                Items.netherite_ocean_necklace.id().toString(),
                Items.netherite_earth_necklace.id().toString(),
                Items.netherite_earth_ring.id().toString(),
                //Items.netherite_sky_necklace.id().toString(),
                //Items.netherite_sky_ring.id().toString(),
                Items.netherite_rage_necklace.id().toString(),
                Items.netherite_rage_ring.id().toString()
                ),
                        1)
                        .chance(0.2F)
        );

        var jewelry_tier_4 = "jewelry_tier_4";
        loot.item_groups.put(jewelry_tier_4, new LootConfig.ItemGroup(List.of(
                Items.unique_ocean_ring.id().toString(),
                Items.unique_ocean_necklace.id().toString(),
                Items.unique_earth_necklace.id().toString(),
                Items.unique_earth_ring.id().toString(),
                //Items.unique_sky_ring.id().toString(),
                //Items.unique_sky_necklace.id().toString(),
                Items.unique_rage_necklace.id().toString(),
                Items.unique_rage_ring.id().toString(),
                Items.vampire_necklace.id().toString(),
                Items.vampire_ring.id().toString()
                ), 1)
                        .chance(0.5F)
        );

        List.of("minecraft:chests/jungle_temple",
                        "minecraft:chests/desert_pyramid",
                        "minecraft:chests/igloo_chest",
                        "minecraft:chests/shipwreck_supply")
                .forEach(id -> loot.loot_tables.put(id, List.of(jewelry_tier_1)));

        List.of("minecraft:chests/stronghold_crossing",
                        "minecraft:chests/stronghold_library",
                        "minecraft:chests/underwater_ruin_big",
                        "minecraft:chests/simple_dungeon",
                        "minecraft:chests/woodland_mansion")
                .forEach(id -> loot.loot_tables.put(id, List.of(jewelry_tier_2)));

        List.of("minecraft:chests/bastion_treasure",
                        "minecraft:chests/bastion_other",
                        "minecraft:chests/nether_bridge")
                .forEach(id -> loot.loot_tables.put(id, List.of(jewelry_tier_3)));

        List.of("minecraft:chests/ancient_city",
                        "minecraft:chests/end_city_treasure")
                .forEach(id -> loot.loot_tables.put(id, List.of(jewelry_tier_4)));
    }

}
