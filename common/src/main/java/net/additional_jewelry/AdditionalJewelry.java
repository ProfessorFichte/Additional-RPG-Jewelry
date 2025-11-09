package net.additional_jewelry;

import net.additional_jewelry.items.AdditionalGems;
import net.additional_jewelry.items.Group;
import net.additional_jewelry.items.AdditionalJewelryItems;
import net.additional_jewelry.village.VillagerTrades;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.additional_jewelry.config.Default;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.jewelry.config.ItemConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
		FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "jewelry_changes"),
					modContainer,
					ResourcePackActivationType.ALWAYS_ENABLED
			);
		});
	}

	public static void registerItems() {
		Group.ADDITIONAL_JEWELRY = FabricItemGroup.builder()
				.icon(() -> new ItemStack(AdditionalJewelryItems.malachite_ring.item()))
				.displayName(Text.translatable("itemGroup." + MOD_ID + ".general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, Group.ADDITIONAL_JEWELRY_KEY, Group.ADDITIONAL_JEWELRY);
		AdditionalGems.register();
		AdditionalJewelryItems.register(itemConfig.value);
		itemConfig.save();
	}

	public static void registerVillagers() {
		VillagerTrades.register();
	}

}