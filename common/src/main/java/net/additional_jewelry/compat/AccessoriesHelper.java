package net.additional_jewelry.compat;

import io.wispforest.accessories.api.components.AccessoriesDataComponents;
import io.wispforest.accessories.api.components.AccessoryItemAttributeModifiers;
import net.additional_jewelry.items.AdditionalJewelryFactory;
import net.jewelry.compat.JewelryAccessoriesItem;
import net.jewelry.util.SoundHelper;

public class AccessoriesHelper {
    public static void registerFactory() {
        AdditionalJewelryFactory.factory = args -> {
            var settings = args.settings();
            var attributes = args.attributes();
            var slot = args.slot() != null ? args.slot() : "ring"; // Use provided slot or default

            if (attributes != null) {
                var builder = AccessoryItemAttributeModifiers.builder();
                for (var bonus : attributes.modifiers()) {
                    builder = builder.addForSlot(bonus.attribute(), bonus.modifier(), slot, true);
                }
                settings = settings.component(AccessoriesDataComponents.ATTRIBUTES, builder.build());
            }

            return new JewelryAccessoriesItem(
                    settings,
                    args.lore(),
                    () -> SoundHelper.JEWELRY_EQUIP_ENTRY);
        };
    }
}
