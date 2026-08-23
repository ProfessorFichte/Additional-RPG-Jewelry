package net.additional_jewelry.datagen;

import net.additional_jewelry.items.AdditionalJewelryItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AdditionalJewelryDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(LangGenerator::new);
        pack.addProvider(AdditionalJewelryRecipeProvider::new);
        pack.addProvider(ModelProvider::new);
        pack.addProvider(UnsmeltGenerator::new);
        pack.addProvider(ItemTagGenerator::new);
    }

    public static class UnsmeltGenerator extends FabricRecipeProvider {
        public UnsmeltGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        public static int UNSMELT_TIME = 300;

        @Override
        public void generate(RecipeExporter exporter) {
            disassemble(exporter, List.of(AdditionalJewelryItems.driptstone_necklace.item), Items.POINTED_DRIPSTONE);
            disassemble(exporter, List.of(AdditionalJewelryItems.cactea_ring.item), Items.GREEN_DYE);
            disassemble(exporter, List.of(AdditionalJewelryItems.rage_ring.item), Items.IRON_NUGGET);
            disassemble(exporter, List.of(AdditionalJewelryItems.rage_necklace.item), Items.IRON_NUGGET);
            disassemble(exporter,
                    AdditionalJewelryItems.all.stream()
                            .filter(entry -> entry.tier() == 2 && !entry.id().getPath().contains("rage") && entry.item() != null)
                            .map(entry -> (ItemConvertible) entry.item()).toList(),
                    Items.GOLD_NUGGET);
            disassemble(exporter,
                    AdditionalJewelryItems.all.stream()
                            .filter(entry -> entry.id().getPath().contains("netherite") && entry.item() != null)
                            .map(entry -> (ItemConvertible) entry.item()).toList(),
                    Items.NETHERITE_SCRAP);
        }

        private static void disassemble(RecipeExporter exporter, List<ItemConvertible> items, Item output) {
            FabricRecipeProvider.offerSmelting(exporter,
                    items,
                    RecipeCategory.MISC,
                    output,
                    0.1f,
                    UNSMELT_TIME,
                    "disassemble"
            );
            FabricRecipeProvider.offerBlasting(exporter,
                    items,
                    RecipeCategory.MISC,
                    output,
                    0.1f,
                    UNSMELT_TIME / 2,
                    "disassemble"
            );
        }
    }
}
