package net.additional_jewelry;

import net.additional_jewelry.items.Group;
import net.additional_jewelry.items.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.additional_jewelry.config.Default;
import net.jewelry.config.LootConfig;
import net.jewelry.config.ItemConfig;

import net.jewelry.util.LootHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdditionalJewelry implements ModInitializer {
	public static final String MOD_ID = "additional_rpg_jewelry";
    public static final Logger LOGGER = LoggerFactory.getLogger("additional_rpg_jewelry");

	public static ConfigManager<ItemConfig> itemConfig = new ConfigManager<>
			("items_v1", Default.items)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	public static ConfigManager<LootConfig> lootConfig = new ConfigManager<>
			("loot_v2", Default.loot)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	private void registerItemGroup() {
		Group.ADDITIONAL_JEWELRY = FabricItemGroup.builder()
				.icon(() -> new ItemStack(Items.unique_ocean_necklace.item()))
				.displayName(Text.translatable("itemGroup." + MOD_ID + ".general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, Group.ADDITIONAL_JEWELRY_KEY, Group.ADDITIONAL_JEWELRY);
	}
	@Override
	public void onInitialize() {
		itemConfig.refresh();
		lootConfig.refresh();
		registerItemGroup();
		Items.register(itemConfig.value);
		itemConfig.save();

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			LootHelper.configure(id, tableBuilder, AdditionalJewelry.lootConfig.value, Items.entryMap);
		});

		LOGGER.info("Hello Fabric world!");
	}
}