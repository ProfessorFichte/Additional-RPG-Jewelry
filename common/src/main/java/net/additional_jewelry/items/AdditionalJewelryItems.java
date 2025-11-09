package net.additional_jewelry.items;

import net.additional_jewelry.AdditionalJewelry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.jewelry.items.JewelryFactory;
import net.jewelry.config.ItemConfig;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.*;

import static net.additional_jewelry.AdditionalJewelry.MOD_ID;
import static net.jewelry.items.JewelryItems.GENERIC_ATTACK_DAMAGE;
import static net.jewelry.items.JewelryItems.GENERIC_MAX_HEALTH;

public class AdditionalJewelryItems {
    public static final ArrayList<Entry> all = new ArrayList<>();
    public static final class Entry {
        private final Identifier id;
        private final Rarity rarity;
        private final ItemConfig.Item config;
        private final String lore;
        private boolean fireproof;
        int tier = 0;

        public Item item;

        public Entry(Identifier id, Rarity rarity, ItemConfig.Item config, String lore, boolean fireproof) {
            this.id = id;
            this.rarity = rarity;
            this.config = config;
            this.lore = lore;
            this.fireproof = fireproof;
        }

        public Identifier id() {
            return id;
        }


        public Rarity rarity() {
            return rarity;
        }

        public ItemConfig.Item config() {
            return config;
        }

        public String lore() {
            return lore;
        }

        public boolean fireproof() {
            return fireproof;
        }

        public Item create(Item.Settings settings, AttributeModifiersComponent attributes) {
            var slot = (id.getPath().contains("ring") ? "ring" : (id.getPath().contains("necklace") ? "necklace" : null));
            item = AdditionalJewelryFactory.getFactory().apply(new AdditionalJewelryFactory.ItemArgs(settings, attributes, lore, slot));
            return item;
        }

        public Item item() {
            return item;
        }

        public Entry setTier(int tier) {
            this.tier = tier;
            this.fireproof = tier >= 3;
            return this;
        }

        public int tier() {
            return tier;
        }
    }

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
        var entry = new Entry(id, rarity, config, lore, fireproof);
        all.add(entry);
        return entry;
    }

    public static final String GENERIC_ATTACK_SPEED = "generic.attack_speed";

    public static final String COMBATROLL_MOD_ID = "combat_roll";
    public static final String COMBATROLL_RECHARGE = "combat_roll:recharge";

    public static final String CRIT_MOD_ID = "critical_strike";
    public static final String CRITICAL_CHANCE_ID = CRIT_MOD_ID + ":chance";
    public static final String CRITICAL_DAMAGE_ID = CRIT_MOD_ID + ":damage";

    public static final String DAMAGE_REFLECT = "more_rpg_classes:damage_reflect_modifier";
    public static final String RAGE = "more_rpg_classes:rage_modifier";
    public static final String LIFESTEAL = "more_rpg_classes:lifesteal_modifier";
    public static final String SPELL_VAMPIRE = "more_rpg_classes:spell_vampire";



    private static final float tier_1_multiplier = 0.04F;
    private static final ItemConfig.Bonus tier_1_bonus = new ItemConfig.Bonus(tier_1_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    private static final float tier_2_multiplier = 0.08F;
    private static final ItemConfig.Bonus tier_2_bonus = new ItemConfig.Bonus(tier_2_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    private static final float tier_3_physical_multiplier = 0.12F;
    private static final float tier_3_spell_multiplier = 0.08F;
    private static final float tier_3_secondary_multiplier = 0.03F;
    private static final float tier_3_secondary_crit_damage_multiplier = 0.1F;
    private static final float lifesteal_multiplier = 0.05F;


    //CUSTOM_JEWELRY
    public static Entry driptstone_necklace = add(Identifier.of(MOD_ID, "dripstone_necklace"), new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_MAX_HEALTH, 2, EntityAttributeModifier.Operation.ADD_VALUE),
                    new ItemConfig.AttributeModifier(DAMAGE_REFLECT, 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
    )).setTier(1);
    public static Entry cactea_ring = add(Identifier.of(MOD_ID, "cactea_ring"), new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_MAX_HEALTH, 2, EntityAttributeModifier.Operation.ADD_VALUE),
                    new ItemConfig.AttributeModifier(DAMAGE_REFLECT, 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
    )).setTier(1);

    //UNCOMMON
    public static Entry aquamarine_ring = add(Identifier.of(MOD_ID, "aquamarine_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_1_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_1_bonus)
            )
    )).setTier(2);
    public static Entry malachite_ring = add(Identifier.of(MOD_ID, "malachite_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_1_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_1_bonus)
            )
    )).setTier(2);
    public static Entry rage_ring = add(Identifier.of(MOD_ID, "rage_ring"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.06F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(RAGE, tier_1_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier(RAGE, tier_1_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier("generic.attack_damage", tier_1_multiplier/2, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(2);

    public static Entry aquamarine_necklace = add(Identifier.of(MOD_ID, "aquamarine_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_1_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_1_bonus)
            )
    )).setTier(2);
    public static Entry malachite_necklace = add(Identifier.of(MOD_ID, "malachite_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_1_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_1_bonus)
            )
    )).setTier(2);
    public static Entry rage_necklace = add(Identifier.of(MOD_ID, "rage_necklace"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.06F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(RAGE, tier_1_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier(RAGE, tier_1_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier("generic.attack_damage", tier_1_multiplier/2, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(2);

    //NETHERITE_VERSIONS
    public static Entry netherite_aquamarine_ring = add(Identifier.of(MOD_ID, "netherite_aquamarine_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_2_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_2_bonus)
            )
    )).setTier(3);
    public static Entry netherite_malachite_ring = add(Identifier.of(MOD_ID, "netherite_malachite_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_2_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_2_bonus)
            )
    )).setTier(3);
    public static Entry netherite_rage_ring = add(Identifier.of(MOD_ID, "netherite_rage_ring"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.08F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(RAGE, tier_2_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier(RAGE, tier_2_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(3);

    public static Entry netherite_aquamarine_necklace = add(Identifier.of(MOD_ID, "netherite_aquamarine_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_2_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_2_bonus)
            )
    )).setTier(3);
    public static Entry netherite_malachite_necklace = add(Identifier.of(MOD_ID, "netherite_malachite_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_2_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_2_bonus)
            )
    )).setTier(3);
    public static Entry netherite_rage_necklace = add(Identifier.of(MOD_ID, "netherite_rage_necklace"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.08F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(RAGE, tier_2_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier(RAGE, tier_2_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(3);


    //UNIQUE
    public static Entry unique_ocean_ring = add(Identifier.of(MOD_ID, "unique_ocean_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_crit_damage_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry unique_ocean_necklace = add(Identifier.of(MOD_ID, "unique_ocean_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_crit_damage_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry unique_sky_ring = add(Identifier.of(MOD_ID, "unique_sky_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry unique_sky_necklace = add(Identifier.of(MOD_ID, "unique_sky_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry unique_earth_ring = add(Identifier.of(MOD_ID, "unique_earth_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_crit_damage_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry unique_earth_necklace = add(Identifier.of(MOD_ID, "unique_earth_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_crit_damage_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry unique_rage_ring = add(Identifier.of(MOD_ID, "unique_rage_ring"), Rarity.RARE, true, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.12F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(RAGE, 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(RAGE, 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry unique_rage_necklace = add(Identifier.of(MOD_ID, "unique_rage_necklace"), Rarity.RARE, true, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.12F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(RAGE, 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(RAGE, 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    ///ENABLE WHEN DRUIDS MOD BY RULFT44 is released!!!
    /*
    public static Entry unique_forrest_ring = add(Identifier.of(MOD_ID, "unique_forrest_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry unique_forrest_necklace = add(Identifier.of(MOD_ID, "unique_forrest_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
     */
    public static Entry vampire_ring = add(Identifier.of(MOD_ID, "vampire_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_ATTACK_DAMAGE, 0.12F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(LIFESTEAL, lifesteal_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry vampire_necklace = add(Identifier.of(MOD_ID, "vampire_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_ATTACK_DAMAGE, 0.12F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(LIFESTEAL, lifesteal_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry spell_vampire_ring = add(Identifier.of(MOD_ID, "spell_vampire_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(SpellSchools.GENERIC.id, 0.08F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SPELL_VAMPIRE, lifesteal_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);
    public static Entry spell_vampire_necklace = add(Identifier.of(MOD_ID, "spell_vampire_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(SpellSchools.GENERIC.id, 0.08F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new ItemConfig.AttributeModifier(SPELL_VAMPIRE, lifesteal_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            )
    )).setTier(4);

    private static final Identifier modifierId = Identifier.of(MOD_ID, "equipment_bonus");
        public static void register (ItemConfig allConfigs){
            final String ADRENALINE = "witcher_rpg:adrenaline_modifier";
            final String SIGN_INTENSITY = "witcher_rpg:sign_intensity";
            final String QUEN_INTENSITY = "witcher_rpg:quen_intensity";
            final String AARD_INTENSITY = "witcher_rpg:aard_intensity";

            //WITCHER-RELATED STUFF
            if(FabricLoader.getInstance().isModLoaded("witcher_rpg")){
                add(Identifier.of(AdditionalJewelry.MOD_ID,"silver_sapphire_ring"),Rarity.UNCOMMON, new ItemConfig.Item(
                        List.of(
                                new ItemConfig.AttributeModifier(ADRENALINE, tier_1_multiplier  ,EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier(SIGN_INTENSITY, tier_1_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        ))).setTier(2);
                add(Identifier.of(AdditionalJewelry.MOD_ID,"silver_sapphire_necklace"),Rarity.UNCOMMON, new ItemConfig.Item(
                        List.of(
                                new ItemConfig.AttributeModifier(ADRENALINE, tier_1_multiplier  ,EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier(SIGN_INTENSITY, tier_1_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        ))).setTier(2);
                add(Identifier.of(AdditionalJewelry.MOD_ID,"steel_jade_ring"),Rarity.UNCOMMON, new ItemConfig.Item(
                        List.of(
                                new ItemConfig.AttributeModifier(ADRENALINE, 0.08F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                                ))).setTier(2);
                add(Identifier.of(AdditionalJewelry.MOD_ID,"steel_jade_necklace"),Rarity.UNCOMMON, new ItemConfig.Item(
                        List.of(
                                new ItemConfig.AttributeModifier(ADRENALINE, 0.08F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        ))).setTier(2);
                add(Identifier.of(AdditionalJewelry.MOD_ID, "meteorite_silver_sapphire_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
                        List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.1F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier(SIGN_INTENSITY,
                                        tier_2_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        ))).setTier(3);
                add(Identifier.of(AdditionalJewelry.MOD_ID, "meteorite_silver_sapphire_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
                        List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.1F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier(SIGN_INTENSITY,
                                        tier_2_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        ))).setTier(3);
                add(Identifier.of(AdditionalJewelry.MOD_ID, "dark_steel_jade_ring"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
                        COMBATROLL_MOD_ID,
                        List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.14F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier(COMBATROLL_RECHARGE, 0.025F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        ),
                        List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.14F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier(GENERIC_ATTACK_SPEED, 0.04F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        )
                )).setTier(3);
                add(Identifier.of(AdditionalJewelry.MOD_ID, "dark_steel_jade_necklace"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
                        COMBATROLL_MOD_ID,
                        List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.14F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                        new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                        new ItemConfig.AttributeModifier(COMBATROLL_RECHARGE, 0.025F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        ),
                        List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.14F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                        new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                        new ItemConfig.AttributeModifier(GENERIC_ATTACK_SPEED, 0.04F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        )
                        )).setTier(3);
                add(Identifier.of(AdditionalJewelry.MOD_ID, "unique_witcher_ring"), Rarity.RARE,true, new ItemConfig.Item(
                        List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.2F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier(SIGN_INTENSITY, 0.08F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        ))).setTier(4);
                add(Identifier.of(AdditionalJewelry.MOD_ID, "unique_witcher_necklace"), Rarity.RARE,true, new ItemConfig.Item(
                        List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.2F , EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                                new ItemConfig.AttributeModifier(SIGN_INTENSITY, 0.08F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                        ))).setTier(4);
            }

            for (var entry : all) {
                ItemConfig.Item itemConfig = allConfigs.items.get(entry.id.toString());
                if (itemConfig == null) {
                    itemConfig = entry.config;
                    allConfigs.items.put(entry.id.toString(), entry.config);
                }

                AttributeModifiersComponent.Builder attributes = AttributeModifiersComponent.builder();
                for (var modifier : itemConfig.selectedAttributes()) {
                    var id = Identifier.of(modifier.id);
                    var attribute = Registries.ATTRIBUTE.getEntry(id);
                    if (attribute.isPresent()) {
                        attributes.add(attribute.get(),
                                new EntityAttributeModifier(
                                        modifierId,
                                        modifier.value,
                                        modifier.operation), AttributeModifierSlot.ANY);
                    } else {
                        System.err.println("Failed to resolve EntityAttribute with id: " + modifier.id);
                    }
                }
                var settings = new Item.Settings()
                        .rarity(entry.rarity)
                        .maxCount(1);
                if (entry.fireproof()) {
                    settings = settings.fireproof();
                }

                var item = entry.create(settings.maxCount(1), attributes.build());

                Registry.register(Registries.ITEM, entry.id(), item);
            }
            ItemGroupEvents.modifyEntriesEvent(Group.ADDITIONAL_JEWELRY_KEY).register((content) -> {
                for (var entry : all) {
                    content.add(entry.item());
                }
            });
        }
    }
