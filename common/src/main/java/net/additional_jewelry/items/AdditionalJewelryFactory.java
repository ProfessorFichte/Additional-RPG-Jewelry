package net.additional_jewelry.items;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.jewelry.items.VanillaJewelryItem;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.Item;

import java.util.function.Function;

public class AdditionalJewelryFactory {
    public record ItemArgs(Item.Settings settings, @Nullable AttributeModifiersComponent attributes, @Nullable String lore, @Nullable String slot) { }

    public static Function<ItemArgs, Item> factory = args -> {
        var settings = args.settings;
        if (args.attributes != null) {
            settings.attributeModifiers(args.attributes);
        }
        return new VanillaJewelryItem(settings, args.lore);
    };

    public static Function<ItemArgs, Item> getFactory() {
        return factory;
    }
}
