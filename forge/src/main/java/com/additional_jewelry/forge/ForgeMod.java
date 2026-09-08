package com.additional_jewelry.forge;

import com.additional_jewelry.forge.compat.CompatFeatures;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.resource.PathPackResources;

@Mod(AdditionalJewelry.MOD_ID)
public final class ForgeMod {
    private static final String BUILTIN_PACK_DIR = "resourcepacks/jewelry_changes";
    private static final String BUILTIN_PACK_ID = AdditionalJewelry.MOD_ID + ":jewelry_changes";

    @SuppressWarnings("removal")
    public ForgeMod() {
        CompatFeatures.init();
        AdditionalJewelry.init();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(EventPriority.NORMAL, false, RegisterEvent.class, ForgeMod::register);
        modBus.addListener(EventPriority.NORMAL, false, BuildCreativeModeTabContentsEvent.class, ForgeMod::buildTabContents);
        modBus.addListener(EventPriority.NORMAL, false, AddPackFindersEvent.class, ForgeMod::addPackFinders);
        // Villager trades are a game-bus event on Forge (fired per profession).
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, VillagerTradesEvent.class, ForgeMod::onVillagerTrades);
    }

    /// Forge 47 unfreezes exactly one registry per `RegisterEvent` window. This mod only adds items;
    /// the item group is a vanilla-only registry that never gets a window of its own and stays
    /// unfrozen for the whole phase, so `registerItems()` can create it from here.
    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ITEM, reg -> AdditionalJewelry.registerItems());
    }

    private static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(Group.ADDITIONAL_JEWELRY_KEY)) {
            return;
        }
        // `accept` takes a Supplier on Forge 47 (NeoForge's `add(Item)` does not exist).
        for (var entry : AdditionalGems.all) {
            event.accept(entry::item);
        }
        for (var entry : AdditionalJewelryItems.all) {
            if (entry.item() != null) {
                event.accept(entry::item);
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

    /// Forge 47's `AddPackFindersEvent` only hands out `addRepositorySource`; NeoForge's convenience
    /// `addPackFinders(Identifier, ResourceType, Text, PackSource, boolean, InsertionPosition)` does
    /// not exist, so the always-enabled built-in data pack is assembled by hand from the mod jar.
    private static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != ResourceType.SERVER_DATA) {
            return;
        }
        var modFile = ModList.get().getModFileById(AdditionalJewelry.MOD_ID);
        if (modFile == null) {
            return;
        }
        var packRoot = modFile.getFile().findResource(BUILTIN_PACK_DIR);
        event.addRepositorySource(profileAdder -> {
            var profile = ResourcePackProfile.create(
                    BUILTIN_PACK_ID,
                    Text.literal("Additional Jewelry Changes"),
                    true, // always enabled
                    name -> new PathPackResources(name, true, packRoot),
                    ResourceType.SERVER_DATA,
                    ResourcePackProfile.InsertionPosition.TOP,
                    ResourcePackSource.BUILTIN);
            if (profile != null) {
                profileAdder.accept(profile);
            } else {
                AdditionalJewelry.LOGGER.warn("Built-in data pack '{}' could not be read from {}",
                        BUILTIN_PACK_ID, packRoot);
            }
        });
    }
}
