package net.additional_jewelry.items;

import net.additional_jewelry.AdditionalJewelry;
import net.spell_engine.Platform;
import net.jewelry.config.ItemConfig;
import net.jewelry.items.JewelryModifiers;
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
        private String name;
        private String loreText;
        private String requiredMod;

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

        public Entry name(String name) {
            this.name = name;
            return this;
        }

        public Entry loreText(String loreText) {
            this.loreText = loreText;
            return this;
        }

        public Entry requiredMod(String modId) {
            this.requiredMod = modId;
            return this;
        }

        public String requiredMod() {
            return requiredMod;
        }

        public String translatedName() {
            if (name != null) return name;
            var words = id.getPath().split("_");
            var builder = new StringBuilder();
            for (var word : words) {
                if (!builder.isEmpty()) builder.append(" ");
                builder.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            }
            return builder.toString();
        }

        public String translatedLore() {
            return loreText != null ? loreText : "";
        }

        public boolean fireproof() {
            return fireproof;
        }

        public Item create(Item.Settings settings, JewelryModifiers attributes) {
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
    private static final ItemConfig.Bonus tier_1_bonus = new ItemConfig.Bonus(tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    private static final float tier_2_multiplier = 0.08F;
    private static final ItemConfig.Bonus tier_2_bonus = new ItemConfig.Bonus(tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    private static final float tier_3_physical_multiplier = 0.12F;
    private static final float tier_3_spell_multiplier = 0.08F;
    private static final float tier_3_secondary_multiplier = 0.03F;
    private static final float tier_3_secondary_crit_damage_multiplier = 0.1F;
    private static final float lifesteal_multiplier = 0.05F;


    //CUSTOM_JEWELRY
    public static Entry driptstone_necklace = add(Identifier.of(MOD_ID, "dripstone_necklace"), new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_MAX_HEALTH, 2, EntityAttributeModifier.Operation.ADDITION),
                    new ItemConfig.AttributeModifier(DAMAGE_REFLECT, 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE))
    )).setTier(1).name("Dripstone Necklace");
    public static Entry cactea_ring = add(Identifier.of(MOD_ID, "cactea_ring"), new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_MAX_HEALTH, 2, EntityAttributeModifier.Operation.ADDITION),
                    new ItemConfig.AttributeModifier(DAMAGE_REFLECT, 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE))
    )).setTier(1).name("Cactus Ring");

    //UNCOMMON
    public static Entry aquamarine_ring = add(Identifier.of(MOD_ID, "aquamarine_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_1_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_1_bonus)
            )
    )).setTier(2).name("Aquamarine Ring");
    public static Entry malachite_ring = add(Identifier.of(MOD_ID, "malachite_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_1_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_1_bonus)
            )
    )).setTier(2).name("Malachite Ring");
    public static Entry rage_ring = add(Identifier.of(MOD_ID, "rage_ring"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.06F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(RAGE, tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier(RAGE, tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier("generic.attack_damage", tier_1_multiplier/2, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(2).name("Viking Rune Ring");

    public static Entry aquamarine_necklace = add(Identifier.of(MOD_ID, "aquamarine_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_1_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_1_bonus)
            )
    )).setTier(2).name("Aquamarine Necklace");
    public static Entry malachite_necklace = add(Identifier.of(MOD_ID, "malachite_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_1_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_1_bonus)
            )
    )).setTier(2).name("Malachite Necklace");
    public static Entry rage_necklace = add(Identifier.of(MOD_ID, "rage_necklace"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.06F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(RAGE, tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier(RAGE, tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier("generic.attack_damage", tier_1_multiplier/2, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(2).name("Viking Rune Necklace");

    //NETHERITE_VERSIONS
    public static Entry netherite_aquamarine_ring = add(Identifier.of(MOD_ID, "netherite_aquamarine_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_2_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_2_bonus)
            )
    )).setTier(3).name("Netherite Aquamarine Ring");
    public static Entry netherite_malachite_ring = add(Identifier.of(MOD_ID, "netherite_malachite_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_2_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_2_bonus)
            )
    )).setTier(3).name("Netherite Malachite Ring");
    public static Entry netherite_rage_ring = add(Identifier.of(MOD_ID, "netherite_rage_ring"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.08F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(RAGE, tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier(RAGE, tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(3).name("Netherite Rune Viking Ring");

    public static Entry netherite_aquamarine_necklace = add(Identifier.of(MOD_ID, "netherite_aquamarine_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_2_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_2_bonus)
            )
    )).setTier(3).name("Netherite Aquamarine Necklace");
    public static Entry netherite_malachite_necklace = add(Identifier.of(MOD_ID, "netherite_malachite_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_2_bonus),
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_2_bonus)
            )
    )).setTier(3).name("Netherite Malachite Necklace");
    public static Entry netherite_rage_necklace = add(Identifier.of(MOD_ID, "netherite_rage_necklace"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.08F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(RAGE, tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier(RAGE, tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(3).name("Netherite Rune Viking Necklace");


    //UNIQUE
    public static Entry unique_ocean_ring = add(Identifier.of(MOD_ID, "unique_ocean_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_crit_damage_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Poseidon's Ring").loreText("Powerful Ring that controls the tides.");
    public static Entry unique_ocean_necklace = add(Identifier.of(MOD_ID, "unique_ocean_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.WATER.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_crit_damage_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Lost Amulet of Atlantis").loreText("A lost relict from a forgotten civilisation.");
    public static Entry unique_sky_ring = add(Identifier.of(MOD_ID, "unique_sky_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Zanite Aether Ring").loreText("Highly valuable jewelry in the aether.");
    public static Entry unique_sky_necklace = add(Identifier.of(MOD_ID, "unique_sky_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.AIR.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Zanite Aether Pendant").loreText("Forged by Valkyries in the aether dimension.");
    public static Entry unique_earth_ring = add(Identifier.of(MOD_ID, "unique_earth_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_crit_damage_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Deep Obsidian Ring").loreText("Forged deep in the caves, maybe by dwarves?");
    public static Entry unique_earth_necklace = add(Identifier.of(MOD_ID, "unique_earth_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.EARTH.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.id, tier_3_secondary_crit_damage_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Obsidian Flower").loreText("Its so heavy, almost too heavy.");
    public static Entry unique_rage_ring = add(Identifier.of(MOD_ID, "unique_rage_ring"), Rarity.RARE, true, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.12F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(RAGE, 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(RAGE, 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("ᛈᛦᛋ").loreText("This ring has a intimidating aura. Who could read these runes?");
    public static Entry unique_rage_necklace = add(Identifier.of(MOD_ID, "unique_rage_necklace"), Rarity.RARE, true, ItemConfig.itemWithCondition(
            CRIT_MOD_ID,
            List.of(
                    new ItemConfig.AttributeModifier(CRITICAL_DAMAGE_ID, 0.12F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(RAGE, 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            ),
            List.of(
                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(RAGE, 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Svablods Pendant").loreText("He proclaimed to his worshippers but one commandment: Kill.");
    public static Entry unique_forest_ring = add(Identifier.of(MOD_ID, "unique_forest_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Forest's Spirit Ring").loreText("Forged by mighty Druids in a mythical forest with the Heart of the Forest.");
    public static Entry unique_forest_necklace = add(Identifier.of(MOD_ID, "unique_forest_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(MoreSpellSchools.NATURE.id, tier_3_spell_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SpellPowerMechanics.HASTE.id, tier_3_secondary_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Leshen's Amulet").loreText("Crafted by a powerful Druid from the wood and the heart of a ancient Leshen.");
    public static Entry vampire_ring = add(Identifier.of(MOD_ID, "vampire_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_ATTACK_DAMAGE, 0.12F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(LIFESTEAL, lifesteal_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Ring of Regis").loreText("The ring thirsts for blood...");
    public static Entry vampire_necklace = add(Identifier.of(MOD_ID, "vampire_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(GENERIC_ATTACK_DAMAGE, 0.12F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(LIFESTEAL, lifesteal_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Chain of Gharasham").loreText("Nac thi sel me thaur?");
    public static Entry spell_vampire_ring = add(Identifier.of(MOD_ID, "spell_vampire_ring"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(SpellSchools.GENERIC.id, 0.08F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SPELL_VAMPIRE, lifesteal_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Runic Scarlet Ring").loreText("Forged by evil blood mages, powerful spells are engraved.");
    public static Entry spell_vampire_necklace = add(Identifier.of(MOD_ID, "spell_vampire_necklace"), Rarity.RARE, true, new ItemConfig.Item(
            List.of(
                    new ItemConfig.AttributeModifier(SpellSchools.GENERIC.id, 0.08F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                    new ItemConfig.AttributeModifier(SPELL_VAMPIRE, lifesteal_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            )
    )).setTier(4).name("Sacred Scarlet Necklace").loreText("Old Necklace created with vampiric magic power.");

    /// One shared modifier id for every bonus of every piece, exactly as on 1.21.1. Under 1.20.1's
    /// UUID keying that is still safe: no item carries two bonuses on the *same* attribute, and the
    /// equipped path folds the slot mod's per-slot UUID into the key (see `JewelryModifiers`), so two
    /// pieces in two accessory slots never collide. Jewelry itself uses a per-item, per-attribute id;
    /// switch to that shape here too if a piece ever needs two modifiers on one attribute.
    private static final Identifier modifierId = Identifier.of(MOD_ID, "equipment_bonus");
    private static boolean conditionalEntriesAdded = false;

        /// The mod-gated (Witcher RPG) pieces are appended to `all` here rather than at class init.
        /// Upstream did this inside `register(...)` itself, which made a second call double-register
        /// them; the flag makes the creation half idempotent so `itemsToRegister` is safe to call
        /// from either loader path.
        private static void addConditionalEntries() {
            if (conditionalEntriesAdded) {
                return;
            }
            conditionalEntriesAdded = true;
            final String ADRENALINE = "witcher_rpg:adrenaline_modifier";
            final String SIGN_INTENSITY = "witcher_rpg:sign_intensity";
            final String QUEN_INTENSITY = "witcher_rpg:quen_intensity";
            final String AARD_INTENSITY = "witcher_rpg:aard_intensity";

            //WITCHER-RELATED STUFF
            add(Identifier.of(AdditionalJewelry.MOD_ID,"silver_sapphire_ring"),Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(
                            new ItemConfig.AttributeModifier(ADRENALINE, tier_1_multiplier  ,EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(SIGN_INTENSITY, tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    ))).setTier(2).name("Silver Sapphire Ring").requiredMod("witcher_rpg");
            add(Identifier.of(AdditionalJewelry.MOD_ID,"silver_sapphire_necklace"),Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(
                            new ItemConfig.AttributeModifier(ADRENALINE, tier_1_multiplier  ,EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(SIGN_INTENSITY, tier_1_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    ))).setTier(2).name("Silver Sapphire Necklace").requiredMod("witcher_rpg");
            add(Identifier.of(AdditionalJewelry.MOD_ID,"steel_jade_ring"),Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(
                            new ItemConfig.AttributeModifier("generic.attack_damage", 0.03F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(ADRENALINE, 0.08F , EntityAttributeModifier.Operation.MULTIPLY_BASE)
                            ))).setTier(2).name("Steel Jade Ring").requiredMod("witcher_rpg");
            add(Identifier.of(AdditionalJewelry.MOD_ID,"steel_jade_necklace"),Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(
                            new ItemConfig.AttributeModifier("generic.attack_damage", 0.03F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(ADRENALINE, 0.08F , EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    ))).setTier(2).name("Steel Jade Necklace").requiredMod("witcher_rpg");
            add(Identifier.of(AdditionalJewelry.MOD_ID, "meteorite_silver_sapphire_ring"), Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.1F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(SIGN_INTENSITY,
                                    tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    ))).setTier(3).name("Meteorite Silver Sapphire Ring").requiredMod("witcher_rpg");
            add(Identifier.of(AdditionalJewelry.MOD_ID, "meteorite_silver_sapphire_necklace"), Rarity.UNCOMMON, new ItemConfig.Item(
                    List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.1F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(SIGN_INTENSITY,
                                    tier_2_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    ))).setTier(3).name("Meteorite Silver Sapphire Necklace").requiredMod("witcher_rpg");
            add(Identifier.of(AdditionalJewelry.MOD_ID, "dark_steel_jade_ring"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
                    COMBATROLL_MOD_ID,
                    List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.14F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(COMBATROLL_RECHARGE, 0.025F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    ),
                    List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.14F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(GENERIC_ATTACK_SPEED, 0.04F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    )
            )).setTier(3).name("Dark Steel Jade Ring").requiredMod("witcher_rpg");
            add(Identifier.of(AdditionalJewelry.MOD_ID, "dark_steel_jade_necklace"), Rarity.UNCOMMON, ItemConfig.itemWithCondition(
                    COMBATROLL_MOD_ID,
                    List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.14F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                                    new ItemConfig.AttributeModifier(COMBATROLL_RECHARGE, 0.025F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    ),
                    List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.14F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                                    new ItemConfig.AttributeModifier("generic.attack_damage", 0.05F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                                    new ItemConfig.AttributeModifier(GENERIC_ATTACK_SPEED, 0.04F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    )
                    )).setTier(3).name("Dark Steel Jade Necklace").requiredMod("witcher_rpg");
            add(Identifier.of(AdditionalJewelry.MOD_ID, "unique_witcher_ring"), Rarity.RARE,true, new ItemConfig.Item(
                    List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.2F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(SIGN_INTENSITY, 0.08F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    ))).setTier(4).name("Ancient Kaer Morhen Ring").loreText("A Wolf School Ring from one of the first Witchers of Kaer Morhen.").requiredMod("witcher_rpg");
            add(Identifier.of(AdditionalJewelry.MOD_ID, "unique_witcher_necklace"), Rarity.RARE,true, new ItemConfig.Item(
                    List.of(new ItemConfig.AttributeModifier(ADRENALINE, 0.2F , EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier("generic.attack_damage", 0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE),
                            new ItemConfig.AttributeModifier(SIGN_INTENSITY, 0.08F, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                    ))).setTier(4).name("Early Silver Medallion").loreText("Unstable magical silver necklace, created by Alzur.").requiredMod("witcher_rpg");

        }

        /// Creation half of `register(...)`: configs applied, items constructed, nothing written to the
        /// registry. The Forge entrypoint feeds the returned map to the helper `RegisterEvent` hands out,
        /// because on Forge 47.0-47.3 the vanilla ITEM registry stays locked inside the window and a plain
        /// `Registry.register` throws. Idempotent - ids already in the registry are skipped.
        public static Map<Identifier, Item> itemsToRegister(ItemConfig allConfigs) {
            addConditionalEntries();
            var items = new LinkedHashMap<Identifier, Item>();
            for (var entry : all) {
                boolean modAvailable = entry.requiredMod() == null
                        || Platform.util().isModLoaded(entry.requiredMod())
                        || Platform.util().isDevelopmentEnvironment();
                if (!modAvailable) {
                    continue;
                }
                if (Registries.ITEM.containsId(entry.id())) {
                    continue;
                }
                ItemConfig.Item itemConfig = allConfigs.items.get(entry.id.toString());
                if (itemConfig == null) {
                    itemConfig = entry.config;
                    allConfigs.items.put(entry.id.toString(), entry.config);
                }

                // 1.20.1 has no attribute-modifier item component: bonuses ride Jewelry's
                // `JewelryModifiers` stand-in, whose namespaced modifier id becomes a stable UUID
                // (SpellPower's `ModifierDefinitions.uuid`) and, once equipped, is folded together with
                // the slot mod's per-slot UUID so pieces still stack across accessory slots.
                var modifiers = new ArrayList<JewelryModifiers.Entry>();
                for (var modifier : itemConfig.selectedAttributes()) {
                    var id = new Identifier(modifier.id);
                    // 1.20.1 registries have no `getEntry(Identifier)`. An unresolvable attribute (a
                    // companion mod that is not installed) simply drops that one bonus, as on 1.21.1.
                    var attribute = Registries.ATTRIBUTE.getOrEmpty(id);
                    if (attribute.isPresent()) {
                        modifiers.add(new JewelryModifiers.Entry(
                                attribute.get(), modifierId, modifier.value, modifier.operation));
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

                var item = entry.create(settings.maxCount(1), new JewelryModifiers(List.copyOf(modifiers)));

                items.put(entry.id(), item);
            }
            return items;
        }

        public static void register (ItemConfig allConfigs){
            itemsToRegister(allConfigs).forEach((id, item) -> Registry.register(Registries.ITEM, id, item));
        }
    }
