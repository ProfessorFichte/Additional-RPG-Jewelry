package net.additional_jewelry.village;

import net.additional_jewelry.items.AdditionalJewelryItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.jewelry.village.JewelryVillagers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public class VillagerTrades {

    public static void register(){
        VillagerProfession jewelerProfession = JewelryVillagers.JEWELER_PROFESSION;
        if(jewelerProfession != null){
            TradeOfferHelper.registerVillagerOffers(jewelerProfession, 4,
                    factories -> {
                        factories.add((entity, random) -> new TradeOffer(
                                new TradedItem(Items.EMERALD, 35),
                                new ItemStack(AdditionalJewelryItems.malachite_ring.item(), 1),
                                5, 13, 0.1F));
                        factories.add((entity, random) -> new TradeOffer(
                                new TradedItem(Items.EMERALD, 35),
                                new ItemStack(AdditionalJewelryItems.aquamarine_ring.item(), 1),
                                5, 13, 0.1F));
                        factories.add((entity, random) -> new TradeOffer(
                                new TradedItem(Items.EMERALD, 35),
                                new ItemStack(AdditionalJewelryItems.rage_ring.item(), 1),
                                5, 13, 0.1F));
                    });
            TradeOfferHelper.registerVillagerOffers(jewelerProfession, 5,
                    factories -> {
                        factories.add((entity, random) -> new TradeOffer(
                                new TradedItem(Items.EMERALD, 45),
                                new ItemStack(AdditionalJewelryItems.malachite_necklace.item(), 1),
                                3, 15, 0.1F));
                        factories.add((entity, random) -> new TradeOffer(
                                new TradedItem(Items.EMERALD, 45),
                                new ItemStack(AdditionalJewelryItems.aquamarine_necklace.item(), 1),
                                3, 15, 0.1F));
                        factories.add((entity, random) -> new TradeOffer(
                                new TradedItem(Items.EMERALD, 45),
                                new ItemStack(AdditionalJewelryItems.rage_necklace.item(), 1),
                                3, 15, 0.1F));
                    });
        }
    }
}
