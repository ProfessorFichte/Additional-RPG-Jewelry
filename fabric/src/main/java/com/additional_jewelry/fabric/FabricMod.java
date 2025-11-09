package com.additional_jewelry.fabric;

import com.additional_jewelry.fabric.compat.CompatFeatures;
import net.additional_jewelry.AdditionalJewelry;
import net.fabricmc.api.ModInitializer;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CompatFeatures.init();
        AdditionalJewelry.init();
        AdditionalJewelry.registerItems();
        AdditionalJewelry.registerVillagers();
    }
}
