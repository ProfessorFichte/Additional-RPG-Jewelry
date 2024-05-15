package net.additional_jewelry.items;

import net.additional_jewelry.AdditionalJewelry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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

public class Items {
    public record Entry(Identifier id, JewelryItem item, ItemConfig.Item config) {
    }

    public static final ArrayList<Entry> all = new ArrayList<>();
    public static final Map<String, Item> entryMap = new HashMap<>();

    public static Entry add(Identifier id, ItemConfig.Item config) {
        return add(id, Rarity.COMMON, config, null);
    }

    public static Entry add(Identifier id, Rarity rarity, ItemConfig.Item config) {
        return add(id, rarity, config, null);
    }

    public static Entry add(Identifier id, Rarity rarity, boolean addLore, ItemConfig.Item config) {
        return add(id, rarity, config, addLore ? ("item." + id.getNamespace() + "." + id.getPath() + ".lore") : null);
    }


    public static Entry add(Identifier id, Rarity rarity, ItemConfig.Item config, String lore) {
        var entry = new Entry(id, new JewelryItem(new FabricItemSettings().rarity(rarity), lore), config);
        all.add(entry);
        entryMap.put(id.toString(), entry.item());
        return entry;
    }

    ////RAGE
    public static Entry addRage(Identifier id, Rarity rarity, ItemConfig.Item config, float value) {
        return addRage(id, rarity, config, null, value);
    }
    public static Entry addRage(Identifier id, Rarity rarity, boolean addLore,ItemConfig.Item config, float value) {
        return addRage(id, rarity, config, addLore ? ("item." + id.getNamespace() + "." + id.getPath() + ".lore") : null, value);
    }
    public static Entry addRage(Identifier id, Rarity rarity, ItemConfig.Item config, String lore, float value) {
        var entry = new Entry(id, new RageJewelryItem(new FabricItemSettings().rarity(rarity), lore, value), config);
        all.add(entry);
        entryMap.put(id.toString(), entry.item());
        return entry;
    }
    ///DAMAGEREFLECT
    public static Entry addReflect(Identifier id, ItemConfig.Item config, float value) {
        return addReflect(id, Rarity.COMMON, config, value);
    }
    public static Entry addReflect(Identifier id, Rarity rarity, ItemConfig.Item config, float value) {
        return addReflect(id, rarity, config, null, value);
    }
    public static Entry addReflect(Identifier id, Rarity rarity, boolean addLore, ItemConfig.Item config,float value) {
        return add(id, rarity, config, addLore ? ("item." + id.getNamespace() + "." + id.getPath() + ".lore") : null);
    }
    public static Entry addReflect(Identifier id, Rarity rarity, ItemConfig.Item config, String lore, float value) {
        var entry = new Entry(id, new ReflectJewelryItem(new FabricItemSettings().rarity(rarity), lore, value), config);
        all.add(entry);
        entryMap.put(id.toString(), entry.item());
        return entry;
    }
    //LIFESTEAL
    public static Entry addLifeSteal(Identifier id, Rarity rarity, boolean addLore, ItemConfig.Item config,float value) {
        return addLifeSteal(id, rarity, config, addLore ? ("item." + id.getNamespace() + "." + id.getPath() + ".lore") : null, value);
    }
    public static Entry addLifeSteal(Identifier id, Rarity rarity, ItemConfig.Item config, String lore, float value) {
        var entry = new Entry(id, new LifeStealJewelryItem(new FabricItemSettings().rarity(rarity), lore, value), config);
        all.add(entry);
        entryMap.put(id.toString(), entry.item());
        return entry;
    }

    private static final float tier_1_multiplier = 0.04F;
    private static final ItemConfig.Bonus tier_1_bonus = new ItemConfig.Bonus(tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    private static final float tier_2_multiplier = 0.08F;
    private static final ItemConfig.Bonus tier_2_bonus = new ItemConfig.Bonus(tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    private static final float tier_3_physical_multiplier = 0.12F;
    private static final float tier_3_spell_multiplier = 0.08F;
    private static final float tier_3_secondary_multiplier = 0.03F;
    private static final float lifesteal_multiplier = 0.05F;


    //CUSTOM_JEWELRY
    public static Entry driptstone_necklace = addReflect(new Identifier(AdditionalJewelry.MOD_ID, "dripstone_necklace"), new ItemConfig.Item(
            List.of(
            )
    ),tier_1_multiplier);
    public static Entry cactea_ring = addReflect(new Identifier(AdditionalJewelry.MOD_ID, "cactea_ring"), new ItemConfig.Item(
            List.of(
            )
    ),tier_1_multiplier);

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
    public static Entry rage_ring = addRage(new Identifier(AdditionalJewelry.MOD_ID, "rage_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
            )
    ),tier_1_multiplier);

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
    public static Entry rage_necklace = addRage(new Identifier(AdditionalJewelry.MOD_ID, "rage_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
            )
    ),tier_1_multiplier);

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
    public static Entry netherite_rage_ring = addRage(new Identifier(AdditionalJewelry.MOD_ID, "netherite_rage_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
            )
    ),tier_2_multiplier);

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
    public static Entry netherite_rage_necklace = addRage(new Identifier(AdditionalJewelry.MOD_ID, "netherite_rage_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
            )
    ),tier_2_multiplier);


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


    public static Entry unique_rage_ring = addRage(new Identifier(AdditionalJewelry.MOD_ID, "unique_rage_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", tier_3_physical_multiplier/ 2, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ),tier_3_spell_multiplier);
    public static Entry unique_rage_necklace = addRage(new Identifier(AdditionalJewelry.MOD_ID, "unique_rage_necklace"), Rarity.RARE, true ,new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", tier_3_physical_multiplier/ 2, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    ),tier_3_spell_multiplier);
    public static Entry vampire_ring = addLifeSteal(new Identifier(AdditionalJewelry.MOD_ID, "vampire_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
            )
    ),lifesteal_multiplier);
    public static Entry vampire_necklace = addLifeSteal(new Identifier(AdditionalJewelry.MOD_ID, "vampire_necklace"), Rarity.RARE, true ,new ItemConfig.Item(
            List.of(
            )
    ),lifesteal_multiplier);

    public static void register(ItemConfig allConfigs) {
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
