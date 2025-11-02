package com.additional_jewelry.fabric;

import net.additional_jewelry.AdditionalJewelry;
import net.fabricmc.api.ModInitializer;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        AdditionalJewelry.init();
        AdditionalJewelry.registerItems();
        AdditionalJewelry.registerVillagers();
    }
}
