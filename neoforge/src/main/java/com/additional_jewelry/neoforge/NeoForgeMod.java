package com.additional_jewelry.neoforge;

import net.additional_jewelry.AdditionalJewelry;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.bus.api.IEventBus;

@Mod(AdditionalJewelry.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        AdditionalJewelry.init();
        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
    }

    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ITEM, reg -> {
            AdditionalJewelry.registerItems();
        });
        event.register(RegistryKeys.VILLAGER_PROFESSION, reg -> {
            AdditionalJewelry.registerVillagers();
        });
    }
}
