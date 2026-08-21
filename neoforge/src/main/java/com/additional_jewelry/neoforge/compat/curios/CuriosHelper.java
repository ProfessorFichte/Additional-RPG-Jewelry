package com.additional_jewelry.neoforge.compat.curios;

import net.additional_jewelry.items.AdditionalJewelryFactory;
import net.jewelry.neoforge.compat.curios.JewelryCurioItem;

public class CuriosHelper {
    public static void registerFactory() {
        AdditionalJewelryFactory.factory = args -> {
            var attributes = args.attributes();
            var item = new JewelryCurioItem(args.settings(), args.lore());
            // Passing attributes here instead of Item.Settings, because Curios ignores `AttributeModifiersComponent`
            if (attributes != null) {
                item.setConfigurableModifiers(attributes);
            }
            return item;
        };
    }
}
