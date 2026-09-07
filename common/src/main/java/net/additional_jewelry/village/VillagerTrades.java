package net.additional_jewelry.village;

import net.additional_jewelry.items.AdditionalJewelryItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VillagerTrades {

    public static Map<Integer, List<TradeOffers.Factory>> createTrades() {
        Map<Integer, List<TradeOffers.Factory>> trades = new LinkedHashMap<>();
        trades.put(4, List.of(
                (entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 35),
                        new ItemStack(AdditionalJewelryItems.malachite_ring.item(), 1),
                        5, 13, 0.1F),
                (entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 35),
                        new ItemStack(AdditionalJewelryItems.aquamarine_ring.item(), 1),
                        5, 13, 0.1F),
                (entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 35),
                        new ItemStack(AdditionalJewelryItems.rage_ring.item(), 1),
                        5, 13, 0.1F)
        ));
        trades.put(5, List.of(
                (entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 45),
                        new ItemStack(AdditionalJewelryItems.malachite_necklace.item(), 1),
                        3, 15, 0.1F),
                (entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 45),
                        new ItemStack(AdditionalJewelryItems.aquamarine_necklace.item(), 1),
                        3, 15, 0.1F),
                (entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 45),
                        new ItemStack(AdditionalJewelryItems.rage_necklace.item(), 1),
                        3, 15, 0.1F)
        ));
        return trades;
    }
}
