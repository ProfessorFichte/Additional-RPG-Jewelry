package net.additional_jewelry.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;

import static net.additional_jewelry.AdditionalJewelry.MOD_ID;

public class AdditionalGems {
    public record Entry(Identifier id, Item item) { }
    public static ArrayList<Entry> all = new ArrayList<>();
    public static Entry gem(Identifier id) {
        var entry = new Entry(id, new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));
        all.add(entry);
        return entry;
    }

    public static final Entry aquamarine = gem(Identifier.of(MOD_ID, "aquamarine"));
    public static final Entry malachite = gem(Identifier.of(MOD_ID, "malachite"));

    public static void register() {
        for (var entry : all) {
            Registry.register(Registries.ITEM, entry.id(), entry.item());
        }
    }
}
