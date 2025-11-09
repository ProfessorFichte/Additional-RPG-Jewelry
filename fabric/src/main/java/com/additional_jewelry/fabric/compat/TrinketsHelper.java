package com.additional_jewelry.fabric.compat;

import net.additional_jewelry.items.AdditionalJewelryFactory;
import net.jewelry.fabric.compat.trinkets.JewelryTrinketItem;

public class TrinketsHelper {
    public static void registerFactory() {
        AdditionalJewelryFactory.factory = args -> {
            var attributes = args.attributes();
            var item = new JewelryTrinketItem(args.settings(), args.lore());
            // Passing attributes here instead Item.Settings, because Trinkets ignores `AttributeModifiersComponent`
            if (attributes != null) {
                item.setConfigurableModifiers(attributes);
            }
            return item;
        };
    }
}
