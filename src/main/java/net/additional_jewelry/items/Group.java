package net.additional_jewelry.items;

import net.additional_jewelry.AdditionalJewelry;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class Group {
    public static Identifier ID = new Identifier(AdditionalJewelry.MOD_ID, "generic");
    public static RegistryKey<ItemGroup> ADDITIONAL_JEWELRY_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),new Identifier(AdditionalJewelry.MOD_ID,"generic"));
    public static ItemGroup ADDITIONAL_JEWELRY;

    public static void registerItemGroups() {


        AdditionalJewelry.LOGGER.info("Registering Item Groups for " + AdditionalJewelry.MOD_ID);
    }

}
