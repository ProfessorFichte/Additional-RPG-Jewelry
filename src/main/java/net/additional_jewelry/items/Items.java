package net.additional_jewelry.items;

import net.additional_jewelry.AdditionalJewelry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.jewelry.api.AttributeResolver;
import net.jewelry.api.JewelryItem;
import net.jewelry.config.ItemConfig;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_power.api.SpellPowerMechanics;

import java.util.*;

import static net.jewelry.items.JewelryItems.GENERIC_ATTACK_DAMAGE;
import static net.jewelry.items.JewelryItems.GENERIC_MAX_HEALTH;

public class Items {
    public record Entry(Identifier id, JewelryItem item, ItemConfig.Item config, boolean fireproof) {  }
    public static final ArrayList<Entry> all = new ArrayList<>();
    public static final Map<String, Item> entryMap = new HashMap<>();

    public static Entry add(Identifier id, ItemConfig.Item config) {
        return add(id, Rarity.COMMON, config, null, false);
    }

    public static Entry add(Identifier id, Rarity rarity, ItemConfig.Item config) {
        return add(id, rarity, config, null, false);
    }

    public static Entry add(Identifier id, Rarity rarity, ItemConfig.Item config, boolean fireproof) {
        return add(id, rarity, config, null, fireproof);
    }

    public static Entry add(Identifier id, Rarity rarity, boolean addLore, ItemConfig.Item config) {
        return add(id, rarity, config, addLore ? ("item." + id.getNamespace() + "." + id.getPath() + ".lore") : null, false);
    }

    public static Entry add(Identifier id, Rarity rarity, ItemConfig.Item config, String lore, boolean fireproof) {
        var settings = new FabricItemSettings().rarity(rarity);
        if (fireproof) {
            settings = settings.fireproof();
        }
        var entry = new Entry(id, new JewelryItem(settings, lore), config, fireproof);
        all.add(entry);
        entryMap.put(id.toString(), entry.item());
        return entry;
    }
    public static final String COMBATROLL_RECHARGE = "combatroll:recharge";
    private static final float tier_1_multiplier = 0.04F;
    private static final ItemConfig.Bonus tier_1_bonus = new ItemConfig.Bonus(tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    private static final float tier_2_multiplier = 0.08F;
    private static final ItemConfig.Bonus tier_2_bonus = new ItemConfig.Bonus(tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    private static final float tier_3_physical_multiplier = 0.12F;
    private static final float tier_3_spell_multiplier = 0.08F;
    private static final float tier_3_secondary_multiplier = 0.03F;
    private static final float lifesteal_multiplier = 0.05F;


    //CUSTOM_JEWELRY
    public static Entry driptstone_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "dripstone_necklace"), new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_MAX_HEALTH, 2, EntityAttributeModifier.Operation.ADDITION),
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:damage_reflect_modifier")),
                            0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE))
    ));
    public static Entry cactea_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "cactea_ring"), new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_MAX_HEALTH, 2, EntityAttributeModifier.Operation.ADDITION),
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:damage_reflect_modifier")),
                            0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE))
    ));

    //UNCOMMON
    public static Entry ocean_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "ocean_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_1_bonus)
            )
    ));
    public static Entry sky_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "sky_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_1_bonus)
            )
    ));
    public static Entry earth_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "earth_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_1_bonus)
            )
    ));
    public static Entry rage_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "rage_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:rage_modifier")),
                            0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));

    public static Entry ocean_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "ocean_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_1_bonus)
            )
    ));
    public static Entry sky_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "sky_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_1_bonus)
            )
    ));
    public static Entry earth_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "earth_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_1_bonus)
            )
    ));
    public static Entry rage_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "rage_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:rage_modifier")),
                            0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));

    //NETHERITE_VERSIONS
    public static Entry netherite_ocean_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "netherite_ocean_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_2_bonus)
            )
    ));
    public static Entry netherite_sky_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "netherite_sky_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_2_bonus)
            )
    ));
    public static Entry netherite_earth_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "netherite_earth_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_2_bonus)
            )
    ));
    public static Entry netherite_rage_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "netherite_rage_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:rage_modifier")),
                            0.15F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));

    public static Entry netherite_ocean_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "netherite_ocean_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_2_bonus)
            )
    ));
    public static Entry netherite_sky_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "netherite_sky_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_2_bonus)
            )
    ));
    public static Entry netherite_earth_necklace= add(new Identifier(AdditionalJewelry.MOD_ID, "netherite_earth_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_2_bonus)
            )
    ));
    public static Entry netherite_rage_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "netherite_rage_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:rage_modifier")),
                            0.15F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));


    //UNIQUE
    public static Entry unique_ocean_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "unique_ocean_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static Entry unique_ocean_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "unique_ocean_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static Entry unique_sky_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "unique_sky_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static Entry unique_sky_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "unique_sky_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static Entry unique_earth_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "unique_earth_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static Entry unique_earth_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "unique_earth_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static Entry unique_rage_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "unique_rage_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:rage_modifier")),
                            0.2F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static Entry unique_rage_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "unique_rage_necklace"), Rarity.RARE, true ,new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:rage_modifier")),
                            0.2F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static Entry vampire_ring = add(new Identifier(AdditionalJewelry.MOD_ID, "vampire_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_ATTACK_DAMAGE, 0.08F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:lifesteal_modifier")),
                            lifesteal_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static Entry vampire_necklace = add(new Identifier(AdditionalJewelry.MOD_ID, "vampire_necklace"), Rarity.RARE, true ,new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_ATTACK_DAMAGE, 0.08F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(Objects.requireNonNull(
                            Identifier.tryParse("more_rpg_classes:lifesteal_modifier")),
                            lifesteal_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ));
    public static void register(ItemConfig allConfigs) {
        //WITCHER-RELATED STUFF
        if(FabricLoader.getInstance().isModLoaded("witcher_rpg")){
            //RINGS
            add(new Identifier(AdditionalJewelry.MOD_ID,"silver_sapphire_ring"),Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:adrenaline_modifier")), tier_1_multiplier  , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:sign_intensity")), tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE))));

           add(new Identifier(AdditionalJewelry.MOD_ID, "meteorite_silver_sapphire_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:adrenaline_modifier")), 0.1F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:sign_intensity")),
                                    tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE))));

           add(new Identifier(AdditionalJewelry.MOD_ID,"steel_jade_ring"),Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:adrenaline_modifier")), 0.08F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier("generic.attack_damage", tier_1_multiplier/2, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    )));

           add(new Identifier(AdditionalJewelry.MOD_ID, "dark_steel_jade_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:adrenaline_modifier")), 0.14F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(COMBATROLL_RECHARGE, 0.025F, EntityAttributeModifier.Operation.MULTIPLY_BASE)

                    )));

            //NECKLACES
           add(new Identifier(AdditionalJewelry.MOD_ID, "bear_school_medallion"), Rarity.RARE, true ,new ItemConfig.Item(
                    List.of(
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:quen_intensity")), 2 , EntityAttributeModifier.Operation.ADDITION),
                            new ItemConfig.AttributeModifier(GENERIC_MAX_HEALTH, 6.0F, EntityAttributeModifier.Operation.ADDITION),
                            new ItemConfig.AttributeModifier("generic.attack_damage", tier_3_physical_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:adrenaline_modifier")), 0.2F , EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    )));
           add(new Identifier(AdditionalJewelry.MOD_ID, "cat_school_medallion"), Rarity.RARE, true ,new ItemConfig.Item(
                    List.of(
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:aard_intensity")), 2 , EntityAttributeModifier.Operation.ADDITION),
                            new ItemConfig.AttributeModifier("generic.attack_speed", 0.06F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:adrenaline_modifier")), 0.2F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier("generic.attack_damage", tier_3_physical_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(COMBATROLL_RECHARGE, 0.050F, EntityAttributeModifier.Operation.MULTIPLY_BASE)

                    )));
           add(new Identifier(AdditionalJewelry.MOD_ID, "griffin_school_medallion"),  Rarity.RARE, true ,new ItemConfig.Item(
                    List.of(new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:sign_intensity")), tier_3_spell_multiplier , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:adrenaline_modifier")), 0.12F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    )));
           add(new Identifier(AdditionalJewelry.MOD_ID, "wolf_school_medallion"),  Rarity.RARE, true ,new ItemConfig.Item(
                    List.of(
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:sign_intensity")), tier_3_spell_multiplier , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(Objects.requireNonNull(Identifier.tryParse("witcher_rpg:adrenaline_modifier")), 0.15F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier("generic.attack_speed", 0.06F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier("generic.attack_damage", tier_3_physical_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    )));
        }


        for (var entry : all) {
            ItemConfig.Item itemConfig = allConfigs.items.get(entry.id.toString());
            if (itemConfig == null) {
                itemConfig = entry.config;
                allConfigs.items.put(entry.id.toString(), entry.config);
            }

            var modifiers = new ArrayList<JewelryItem.Modifier>();
            for (var modifier : itemConfig.attributes) {
                var attribute = AttributeResolver.get(new Identifier(modifier.id));
                if (attribute == null) {
                    System.err.println("Failed to resolve EntityAttribute with id: " + modifier.id);
                    continue;
                }
                modifiers.add(new JewelryItem.Modifier(
                        attribute,
                        "Jewelry modifier",
                        modifier.value,
                        modifier.operation));
            }
            entry.item().setConfigurableModifiers(modifiers);
            Registry.register(Registries.ITEM, entry.id(), entry.item());
        }

        ItemGroupEvents.modifyEntriesEvent(Group.ADDITIONAL_JEWELRY_KEY).register((content) -> {
            for (var entry : all) {
                content.add(entry.item());
            }
        });
    }

}
