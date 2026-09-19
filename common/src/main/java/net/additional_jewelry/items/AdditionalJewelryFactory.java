package net.additional_jewelry.items;

import net.jewelry.items.JewelryModifiers;
import net.jewelry.items.VanillaJewelryItem;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class AdditionalJewelryFactory {
    /// 1.20.1: attribute bonuses ride Jewelry's `JewelryModifiers` (its stand-in for 1.21's
    /// `AttributeModifiersComponent`), not an item component. `Item.Settings#attributeModifiers` does
    /// not exist here, so the modifiers are handed to the item itself in every branch.
    public record ItemArgs(Item.Settings settings, @Nullable JewelryModifiers attributes, @Nullable String lore, @Nullable String slot) { }

    public static Function<ItemArgs, Item> factory = args ->
            new VanillaJewelryItem(args.settings(), args.attributes(), args.lore());

    public static Function<ItemArgs, Item> getFactory() {
        return factory;
    }
}
