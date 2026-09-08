package com.additional_jewelry.forge.compat.curios;

import net.additional_jewelry.items.AdditionalJewelryFactory;
import net.jewelry.forge.compat.curios.JewelryCurioItem;

public class CuriosHelper {
    public static void registerFactory() {
        AdditionalJewelryFactory.factory = args -> {
            var attributes = args.attributes();
            var item = new JewelryCurioItem(args.settings(), args.lore());
            // Passed to the item rather than through Item.Settings: Curios asks the item for its
            // modifiers per equipped slot (1.20.1 has no attribute-modifier item component at all).
            if (attributes != null) {
                item.setConfigurableModifiers(attributes);
            }
            return item;
        };
    }
}
