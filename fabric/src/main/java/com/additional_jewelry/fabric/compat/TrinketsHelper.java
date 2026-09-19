package com.additional_jewelry.fabric.compat;

import net.additional_jewelry.items.AdditionalJewelryFactory;
import net.jewelry.fabric.compat.trinkets.JewelryTrinketItem;

public class TrinketsHelper {
    public static void registerFactory() {
        AdditionalJewelryFactory.factory = args -> {
            var attributes = args.attributes();
            var item = new JewelryTrinketItem(args.settings(), args.lore());
            // Passed to the item rather than through Item.Settings: Trinkets asks the item for its
            // modifiers per equipped slot (1.20.1 has no attribute-modifier item component at all).
            if (attributes != null) {
                item.setConfigurableModifiers(attributes);
            }
            return item;
        };
    }
}
