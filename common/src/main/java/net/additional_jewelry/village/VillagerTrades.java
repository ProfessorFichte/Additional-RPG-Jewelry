package net.additional_jewelry.village;

import net.additional_jewelry.items.AdditionalJewelryItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VillagerTrades {

    /// 1.20.1 has no `TradedItem` record: `TradeOffer` takes the buy stack directly
    /// (`ItemStack buy, ItemStack sell, int maxUses, int merchantExperience, float priceMultiplier`).
    private static TradeOffers.Factory sell(AdditionalJewelryItems.Entry result, int emeralds,
                                            int maxUses, int experience) {
        // The item is resolved inside the factory, exactly as upstream, so trade construction never
        // depends on registration order.
        return (entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, emeralds),
                new ItemStack(result.item(), 1),
                maxUses, experience, 0.1F);
    }

    public static Map<Integer, List<TradeOffers.Factory>> createTrades() {
        Map<Integer, List<TradeOffers.Factory>> trades = new LinkedHashMap<>();
        trades.put(4, List.of(
                sell(AdditionalJewelryItems.malachite_ring, 35, 5, 13),
                sell(AdditionalJewelryItems.aquamarine_ring, 35, 5, 13),
                sell(AdditionalJewelryItems.rage_ring, 35, 5, 13)
        ));
        trades.put(5, List.of(
                sell(AdditionalJewelryItems.malachite_necklace, 45, 3, 15),
                sell(AdditionalJewelryItems.aquamarine_necklace, 45, 3, 15),
                sell(AdditionalJewelryItems.rage_necklace, 45, 3, 15)
        ));
        return trades;
    }
}
