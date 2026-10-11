package com.additional_jewelry.fabric;

import com.additional_jewelry.fabric.compat.CompatFeatures;
import net.additional_jewelry.AdditionalJewelry;
import net.additional_jewelry.items.AdditionalGems;
import net.additional_jewelry.items.AdditionalJewelryItems;
import net.additional_jewelry.items.Group;
import net.additional_jewelry.village.VillagerTrades;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.jewelry.items.Gems;
import net.jewelry.items.JewelryItem;
import net.jewelry.village.JewelryVillagers;
import net.minecraft.util.Identifier;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CompatFeatures.init();
        AdditionalJewelry.init();
        AdditionalJewelry.registerItems();
        registerVillagers();
        registerBuiltinResourcePack();

        ItemGroupEvents.modifyEntriesEvent(Group.ADDITIONAL_JEWELRY_KEY).register((content) -> {
            for (var entry : AdditionalJewelryItems.all) {
                if (entry.item() != null) {
                    content.add(entry.item());
                }
            }
        });

        ItemGroupEvents.modifyEntriesEvent(net.jewelry.items.Group.GEMS_KEY).register((content) -> {
            content.addAfter(Gems.tanzanite.item(), AdditionalGems.aquamarine.item(), AdditionalGems.malachite.item());
        });
    }

    private static void registerVillagers() {
        var jewelerProfession = JewelryVillagers.JEWELER_PROFESSION;
        if (jewelerProfession == null) {
            return;
        }
        VillagerTrades.createTrades().forEach((tier, factories) ->
                TradeOfferHelper.registerVillagerOffers(jewelerProfession, tier, list -> list.addAll(factories)));
    }

    private static void registerBuiltinResourcePack() {
        FabricLoader.getInstance().getModContainer(AdditionalJewelry.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(
                    Identifier.of(AdditionalJewelry.MOD_ID, "jewelry_changes"),
                    modContainer,
                    ResourcePackActivationType.ALWAYS_ENABLED
            );
        });
    }
}
