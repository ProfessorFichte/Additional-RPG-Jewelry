package net.additional_jewelry.datagen;

import net.additional_jewelry.items.AdditionalJewelryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.spell_engine.rpg_series.tags.RPGSeriesItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    private static final TagKey<Item> RINGS = TagKey.of(RegistryKeys.ITEM, Identifier.of("jewelry", "rings"));
    private static final TagKey<Item> NECKLACES = TagKey.of(RegistryKeys.ITEM, Identifier.of("jewelry", "necklaces"));

    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        for (var entry : AdditionalJewelryItems.all) {
            var path = entry.id().getPath();
            if (path.contains("ring")) {
                getOrCreateTagBuilder(RINGS).addOptional(entry.id());
            } else if (path.contains("necklace")) {
                getOrCreateTagBuilder(NECKLACES).addOptional(entry.id());
            }

            if (entry.tier() > 0) {
                getOrCreateTagBuilder(RPGSeriesItemTags.LootTiers.get(entry.tier(), RPGSeriesItemTags.LootCategory.ACCESSORIES))
                        .addOptional(entry.id());
            }
        }
    }
}
