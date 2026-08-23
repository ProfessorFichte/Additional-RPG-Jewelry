package net.additional_jewelry.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.additional_jewelry.items.AdditionalJewelryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.additional_jewelry.AdditionalJewelry.MOD_ID;

/**
 * Recipe Provider for Additional Jewelry crafting recipes
 * Generates all crafting recipes using datagen instead of manual JSON files
 */
public class AdditionalJewelryRecipeProvider implements DataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final FabricDataOutput output;
    private final List<RecipeData> recipes = new ArrayList<>();

    public AdditionalJewelryRecipeProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        generate(); // Populate recipes

        return CompletableFuture.allOf(recipes.stream().map(recipeData -> {
            JsonObject recipe = buildRecipeJson(recipeData);
            Path path = output.getResolver(net.minecraft.data.DataOutput.OutputType.DATA_PACK, "recipe")
                    .resolveJson(Identifier.of(MOD_ID, recipeData.name));

            return DataProvider.writeToPath(writer, recipe, path);
        }).toArray(CompletableFuture[]::new));
    }

    public void generate() {
        // ==========================================
        // BASIC JEWELRY (NO MOD DEPENDENCIES)
        // ==========================================

        // Tier 1: Basic Jewelry
        createNecklaceRecipe("dripstone_necklace", "minecraft:string", "minecraft:pointed_dripstone");
        createRingRecipe("cactea_ring", "minecraft:cactus");
        createRingRecipe("rage_ring", "minecraft:iron_ingot", "minecraft:chain");

        // Tier 2: Gold-based Jewelry
        createGoldNecklaceRecipe("aquamarine_necklace", "additional_rpg_jewelry:aquamarine");
        createGoldRingRecipe("aquamarine_ring", "additional_rpg_jewelry:aquamarine");
        createGoldNecklaceRecipe("malachite_necklace", "additional_rpg_jewelry:malachite");
        createGoldRingRecipe("malachite_ring", "additional_rpg_jewelry:malachite");
        createNecklaceRecipe("rage_necklace", "minecraft:string", "minecraft:bone");

        // Tier 3: Netherite-upgraded Jewelry
        createNetheriteNecklaceRecipe("netherite_aquamarine_necklace", "additional_rpg_jewelry:aquamarine");
        createNetheriteRingRecipe("netherite_aquamarine_ring", "additional_rpg_jewelry:aquamarine");
        createNetheriteNecklaceRecipe("netherite_malachite_necklace", "additional_rpg_jewelry:malachite");
        createNetheriteRingRecipe("netherite_malachite_ring", "additional_rpg_jewelry:malachite");
        createNetheriteNecklaceRecipe("netherite_rage_necklace", "minecraft:bone");
        createNetheriteRingRecipe("netherite_rage_ring", "minecraft:chain");

        // ==========================================
        // WITCHER RPG JEWELRY (WITH MOD CONDITIONS)
        // ==========================================

        // Tier 2: Witcher Silver + Sapphire Jewelry
        createWitcherNecklaceRecipe("silver_sapphire_necklace", "witcher_rpg:silver_ingot", "jewelry:sapphire");
        createWitcherRingRecipe("silver_sapphire_ring", "witcher_rpg:silver_ingot", "jewelry:sapphire");

        // Tier 2: Witcher Steel + Jade Jewelry (uses tag for steel ingots)
        createWitcherNecklaceRecipeWithTag("steel_jade_necklace", "witcher_rpg:steel_ingots", "jewelry:jade");
        createWitcherRingRecipeWithTag("steel_jade_ring", "witcher_rpg:steel_ingots", "jewelry:jade");

        // Tier 3: Meteorite Silver + Sapphire Jewelry
        createWitcherNecklaceRecipe("meteorite_silver_sapphire_necklace", "witcher_rpg:meteorite_silver_ingot", "jewelry:sapphire");
        createWitcherRingRecipe("meteorite_silver_sapphire_ring", "witcher_rpg:meteorite_silver_ingot", "jewelry:sapphire");

        // Tier 3: Dark Steel + Jade Jewelry
        createWitcherNecklaceRecipe("dark_steel_jade_necklace", "witcher_rpg:dark_steel_ingot", "jewelry:jade");
        createWitcherRingRecipe("dark_steel_jade_ring", "witcher_rpg:dark_steel_ingot", "jewelry:jade");
    }

    // ==========================================
    // RECIPE HELPER METHODS
    // ==========================================

    /**
     * Creates a simple ring recipe with cactus pattern
     */
    private void createRingRecipe(String name, String material) {
        recipes.add(new RecipeData(
                name,
                new String[]{" A ", "A A", " A "},
                new String[]{"A", material},
                null, // No tags
                null // No mod conditions
        ));
    }

    /**
     * Creates a ring recipe with custom pattern (for rage ring)
     */
    private void createRingRecipe(String name, String materialA, String materialB) {
        recipes.add(new RecipeData(
                name,
                new String[]{" B ", "A A", " A "},
                new String[]{"A", materialA, "B", materialB},
                null, // No tags
                null // No mod conditions
        ));
    }

    /**
     * Creates a basic necklace recipe with string pattern
     */
    private void createNecklaceRecipe(String name, String string, String material) {
        recipes.add(new RecipeData(
                name,
                new String[]{" A ", " A ", " B "},
                new String[]{"A", string, "B", material},
                null, // No tags
                null // No mod conditions
        ));
    }

    /**
     * Creates a gold-based necklace recipe
     */
    private void createGoldNecklaceRecipe(String name, String gem) {
        recipes.add(new RecipeData(
                name,
                new String[]{" A ", " B ", " C "},
                new String[]{"A", "minecraft:string", "B", "minecraft:gold_ingot", "C", gem},
                null, // No tags
                null // No mod conditions
        ));
    }

    /**
     * Creates a gold-based ring recipe
     */
    private void createGoldRingRecipe(String name, String gem) {
        recipes.add(new RecipeData(
                name,
                new String[]{" B ", "A A", " A "},
                new String[]{"A", "minecraft:gold_ingot", "B", gem},
                null, // No tags
                null // No mod conditions
        ));
    }

    /**
     * Creates a netherite-upgraded necklace recipe
     */
    private void createNetheriteNecklaceRecipe(String name, String gem) {
        recipes.add(new RecipeData(
                name,
                new String[]{" B ", "ACA", " D "},
                new String[]{"A", "minecraft:gold_ingot", "B", "minecraft:string", "C", "minecraft:netherite_ingot", "D", gem},
                null, // No tags
                null // No mod conditions
        ));
    }

    /**
     * Creates a netherite-upgraded ring recipe
     */
    private void createNetheriteRingRecipe(String name, String gem) {
        recipes.add(new RecipeData(
                name,
                new String[]{" B ", "A A", " C "},
                new String[]{"A", "minecraft:gold_ingot", "B", gem, "C", "minecraft:netherite_ingot"},
                null, // No tags
                null // No mod conditions
        ));
    }

    /**
     * Creates a Witcher RPG necklace recipe with mod load conditions
     */
    private void createWitcherNecklaceRecipe(String name, String metalIngot, String gem) {
        recipes.add(new RecipeData(
                name,
                new String[]{" A ", " B ", " C "},
                new String[]{"A", "minecraft:string", "B", metalIngot, "C", gem},
                null, // No tags
                new String[]{"witcher_rpg"} // Requires witcher_rpg mod
        ));
    }

    /**
     * Creates a Witcher RPG ring recipe with mod load conditions
     */
    private void createWitcherRingRecipe(String name, String metalIngot, String gem) {
        recipes.add(new RecipeData(
                name,
                new String[]{" B ", "A A", " A "},
                new String[]{"A", metalIngot, "B", gem},
                null, // No tags
                new String[]{"witcher_rpg"} // Requires witcher_rpg mod
        ));
    }

    /**
     * Creates a Witcher RPG necklace recipe with tag support (for steel ingots)
     */
    private void createWitcherNecklaceRecipeWithTag(String name, String metalTag, String gem) {
        recipes.add(new RecipeData(
                name,
                new String[]{" A ", " B ", " C "},
                new String[]{"A", "minecraft:string", "C", gem},
                new String[]{"B", metalTag}, // Tag for metal
                new String[]{"witcher_rpg"} // Requires witcher_rpg mod
        ));
    }

    /**
     * Creates a Witcher RPG ring recipe with tag support (for steel ingots)
     */
    private void createWitcherRingRecipeWithTag(String name, String metalTag, String gem) {
        recipes.add(new RecipeData(
                name,
                new String[]{" B ", "A A", " A "},
                new String[]{"B", gem},
                new String[]{"A", metalTag}, // Tag for metal
                new String[]{"witcher_rpg"} // Requires witcher_rpg mod
        ));
    }

    // ==========================================
    // JSON BUILDING
    // ==========================================

    private JsonObject buildRecipeJson(RecipeData data) {
        JsonObject recipe = new JsonObject();

        // Add Load Conditions if mod dependencies exist
        if (data.requiredMods != null && data.requiredMods.length > 0) {
            // Fabric Load Conditions
            JsonArray fabricLoadConditions = new JsonArray();
            JsonObject fabricCondition = new JsonObject();
            fabricCondition.addProperty("condition", "fabric:all_mods_loaded");
            JsonArray modValues = new JsonArray();
            for (String mod : data.requiredMods) {
                modValues.add(mod);
            }
            fabricCondition.add("values", modValues);
            fabricLoadConditions.add(fabricCondition);
            recipe.add("fabric:load_conditions", fabricLoadConditions);

            // NeoForge Conditions
            JsonArray neoforgeConditions = new JsonArray();
            if (data.requiredMods.length == 1) {
                JsonObject neoforgeCondition = new JsonObject();
                neoforgeCondition.addProperty("type", "neoforge:mod_loaded");
                neoforgeCondition.addProperty("modid", data.requiredMods[0]);
                neoforgeConditions.add(neoforgeCondition);
            } else {
                // Multiple mods: use "and" condition
                JsonObject andCondition = new JsonObject();
                andCondition.addProperty("type", "neoforge:and");
                JsonArray innerConditions = new JsonArray();
                for (String mod : data.requiredMods) {
                    JsonObject modCondition = new JsonObject();
                    modCondition.addProperty("type", "neoforge:mod_loaded");
                    modCondition.addProperty("modid", mod);
                    innerConditions.add(modCondition);
                }
                andCondition.add("conditions", innerConditions);
                neoforgeConditions.add(andCondition);
            }
            recipe.add("neoforge:conditions", neoforgeConditions);
        }

        // Recipe Type
        recipe.addProperty("type", "minecraft:crafting_shaped");

        // Key (ingredient mapping)
        JsonObject key = new JsonObject();

        // First add all item-based ingredients
        if (data.keyMappings != null) {
            for (int i = 0; i < data.keyMappings.length; i += 2) {
                String keyChar = data.keyMappings[i];
                String itemId = data.keyMappings[i + 1];
                JsonObject keyEntry = new JsonObject();
                keyEntry.addProperty("item", itemId);
                key.add(keyChar, keyEntry);
            }
        }

        // Then add all tag-based ingredients
        if (data.tagMappings != null) {
            for (int i = 0; i < data.tagMappings.length; i += 2) {
                String keyChar = data.tagMappings[i];
                String tagId = data.tagMappings[i + 1];
                JsonObject keyEntry = new JsonObject();
                keyEntry.addProperty("tag", tagId);
                key.add(keyChar, keyEntry);
            }
        }

        recipe.add("key", key);

        // Pattern
        JsonArray pattern = new JsonArray();
        for (String patternLine : data.pattern) {
            pattern.add(patternLine);
        }
        recipe.add("pattern", pattern);

        // Result
        JsonObject result = new JsonObject();
        result.addProperty("id", MOD_ID + ":" + data.name);
        recipe.add("result", result);

        return recipe;
    }

    @Override
    public String getName() {
        return "Additional Jewelry Crafting Recipes";
    }

    /**
     * Internal data class for storing recipe information
     *
     * @param name Recipe name (without namespace)
     * @param pattern Crafting pattern (3 strings representing the 3x3 grid)
     * @param keyMappings Item-based ingredient mappings (pairs of key char and item ID)
     * @param tagMappings Tag-based ingredient mappings (pairs of key char and tag ID)
     * @param requiredMods Required mod IDs for load conditions (null if no conditions needed)
     */
    private record RecipeData(
            String name,
            String[] pattern,
            String[] keyMappings,
            String[] tagMappings,
            String[] requiredMods
    ) {}
}
