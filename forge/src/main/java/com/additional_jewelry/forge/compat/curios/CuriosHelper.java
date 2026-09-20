package com.additional_jewelry.forge.compat.curios;

import net.additional_jewelry.items.AdditionalJewelryFactory;
import net.jewelry.forge.compat.curios.JewelryCurioItem;

public class CuriosHelper {
    public static void registerFactory() {
        AdditionalJewelryFactory.factory = args -> {
            var attributes = args.attributes();
            var item = new JewelryCurioItem(args.settings(), args.lore());
            if (attributes != null) {
                item.setConfigurableModifiers(attributes);
            }
            return item;
        };
    }
}
