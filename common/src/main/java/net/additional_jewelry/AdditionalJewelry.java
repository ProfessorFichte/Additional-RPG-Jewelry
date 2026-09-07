package net.additional_jewelry;

import net.additional_jewelry.items.AdditionalGems;
import net.additional_jewelry.items.Group;
import net.additional_jewelry.items.AdditionalJewelryItems;
import net.additional_jewelry.config.Default;
import net.jewelry.config.ItemConfig;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.tiny_config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdditionalJewelry{
	public static final String MOD_ID = "additional_rpg_jewelry";
    public static final Logger LOGGER = LoggerFactory.getLogger("additional_rpg_jewelry");

	public static ConfigManager<ItemConfig> itemConfig = new ConfigManager<>
			("items_v2", Default.items)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	public static void init() {
		itemConfig.refresh();
	}

	public static void registerItems() {
		// FabricItemGroup.builder() is Fabric-API-only; a vanilla ItemGroup.Builder works identically on both loaders.
		Group.ADDITIONAL_JEWELRY = new ItemGroup.Builder(ItemGroup.Row.TOP, 0)
				.icon(() -> new ItemStack(AdditionalJewelryItems.malachite_ring.item()))
				.displayName(Text.translatable("itemGroup." + MOD_ID + ".general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, Group.ADDITIONAL_JEWELRY_KEY, Group.ADDITIONAL_JEWELRY);
		AdditionalGems.register();
		AdditionalJewelryItems.register(itemConfig.value);
		itemConfig.save();
	}

}