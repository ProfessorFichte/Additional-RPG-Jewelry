package net.additional_jewelry.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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

    /// Creation half of `register()`, kept separate so the Forge entrypoint can write these through the
    /// helper `RegisterEvent` hands out: on Forge 47.0-47.3 the vanilla ITEM registry stays locked even
    /// inside the correct window, so a plain `Registry.register` throws there. Idempotent - the items are
    /// built once at class init, and ids already in the registry are skipped.
    public static Map<Identifier, Item> itemsToRegister() {
        var items = new LinkedHashMap<Identifier, Item>();
        for (var entry : all) {
            if (Registries.ITEM.containsId(entry.id())) {
                continue;
            }
            items.put(entry.id(), entry.item());
        }
        return items;
    }

    public static void register() {
        itemsToRegister().forEach((id, item) -> Registry.register(Registries.ITEM, id, item));
    }
}
