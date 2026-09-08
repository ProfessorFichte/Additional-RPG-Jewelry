package net.additional_jewelry.datagen;

import net.additional_jewelry.items.AdditionalGems;
import net.additional_jewelry.items.AdditionalJewelryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static net.additional_jewelry.AdditionalJewelry.MOD_ID;

public class LangGenerator extends FabricLanguageProvider {
    public LangGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add("itemGroup." + MOD_ID + ".general", "Additional Jewelry");

        for (var entry : AdditionalJewelryItems.all) {
            if (entry.item() != null && !entry.translatedName().isEmpty()) {
                builder.add(entry.item(), entry.translatedName());
            }
            if (!entry.translatedLore().isEmpty() && entry.lore() != null) {
                builder.add(entry.lore(), entry.translatedLore());
            }
        }

        for (var gem : AdditionalGems.all) {
            String name = gem.id().getPath();
            String translatedName = name.substring(0, 1).toUpperCase() + name.substring(1);
            builder.add(gem.item(), translatedName);
        }
    }
}
