package net.additional_jewelry.datagen;

import net.additional_jewelry.items.AdditionalGems;
import net.additional_jewelry.items.AdditionalJewelryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (var entry : AdditionalJewelryItems.all) {
            if (entry.item() != null) {
                itemModelGenerator.register(entry.item(), Models.GENERATED);
            }
        }

        for (var gem : AdditionalGems.all) {
            itemModelGenerator.register(gem.item(), Models.GENERATED);
        }
    }
}
