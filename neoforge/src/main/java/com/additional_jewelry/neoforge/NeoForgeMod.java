package com.additional_jewelry.neoforge;

import com.additional_jewelry.neoforge.compat.CompatFeatures;
import net.additional_jewelry.AdditionalJewelry;
import net.additional_jewelry.items.AdditionalGems;
import net.additional_jewelry.items.AdditionalJewelryItems;
import net.additional_jewelry.items.Group;
import net.additional_jewelry.village.VillagerTrades;
import net.jewelry.village.JewelryVillagers;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(AdditionalJewelry.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        CompatFeatures.init();
        AdditionalJewelry.init();
        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
        modBus.addListener(BuildCreativeModeTabContentsEvent.class, NeoForgeMod::buildTabContents);
        modBus.addListener(AddPackFindersEvent.class, NeoForgeMod::addPackFinders);
        NeoForge.EVENT_BUS.addListener(VillagerTradesEvent.class, NeoForgeMod::onVillagerTrades);
    }

    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ITEM, reg -> {
            AdditionalJewelry.registerItems();
        });
    }

    private static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(Group.ADDITIONAL_JEWELRY_KEY)) {
            return;
        }
        for (var entry : AdditionalGems.all) {
            event.add(entry.item());
        }
        for (var entry : AdditionalJewelryItems.all) {
            if (entry.item() != null) {
                event.add(entry.item());
            }
        }
    }

    private static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() != JewelryVillagers.JEWELER_PROFESSION) {
            return;
        }
        VillagerTrades.createTrades().forEach((tier, factories) -> {
            var tierList = event.getTrades().get(tier.intValue());
            if (tierList != null) {
                tierList.addAll(factories);
            }
        });
    }

    private static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != ResourceType.SERVER_DATA) {
            return;
        }
        event.addPackFinders(
                Identifier.of(AdditionalJewelry.MOD_ID, "resourcepacks/jewelry_changes"),
                ResourceType.SERVER_DATA,
                Text.literal("Additional Jewelry Changes"),
                ResourcePackSource.BUILTIN,
                true,
                ResourcePackProfile.InsertionPosition.TOP
        );
    }
}
