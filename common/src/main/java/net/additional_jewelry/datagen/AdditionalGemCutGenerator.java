package net.additional_jewelry.datagen;

import net.additional_jewelry.items.AdditionalGemCuts;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.jewelry.api.datagen.GemCutGenerator;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class AdditionalGemCutGenerator extends GemCutGenerator {
    public AdditionalGemCutGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void generate(Entries entries) {
        AdditionalGemCuts.all.forEach(entries::add);
    }

    @Override
    public String getName() {
        return "Additional Jewelry Gem Cuts";
    }
}
