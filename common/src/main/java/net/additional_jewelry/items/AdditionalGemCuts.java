package net.additional_jewelry.items;

import com.mrpg_lib.compat.spell_power.MoreSpellSchools;
import net.jewelry.api.GemCutBuilder;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static net.additional_jewelry.AdditionalJewelry.MOD_ID;

public class AdditionalGemCuts {
    public static final List<GemCutBuilder.Entry> all = new ArrayList<>();

    private static final int AQUAMARINE_COLOR = 0x2FD8CB;
    private static final int MALACHITE_COLOR = 0x0BDA51;

    private static GemCutBuilder cut(String name, AdditionalGems.Entry gem, int color) {
        return GemCutBuilder.create(Identifier.of(MOD_ID, name), gem.item()).color(color);
    }

    private static GemCutBuilder.Entry add(GemCutBuilder builder) {
        var entry = builder.build();
        all.add(entry);
        return entry;
    }

    public static final GemCutBuilder.Entry BRINY_AQUAMARINE = add(cut("briny_aquamarine", AdditionalGems.aquamarine, AQUAMARINE_COLOR)
            .multiplyBase(MoreSpellSchools.WATER.id, 0.04F));
    public static final GemCutBuilder.Entry TEMPEST_AQUAMARINE = add(cut("tempest_aquamarine", AdditionalGems.aquamarine, AQUAMARINE_COLOR)
            .multiplyBase(MoreSpellSchools.AIR.id, 0.04F));
    public static final GemCutBuilder.Entry TENACIOUS_AQUAMARINE = add(cut("tenacious_aquamarine", AdditionalGems.aquamarine, AQUAMARINE_COLOR)
            .multiplyBase(Identifier.of(AdditionalJewelryItems.TENACITY), 0.03F));

    public static final GemCutBuilder.Entry RUGGED_MALACHITE = add(cut("rugged_malachite", AdditionalGems.malachite, MALACHITE_COLOR)
            .multiplyBase(MoreSpellSchools.EARTH.id, 0.04F));
    public static final GemCutBuilder.Entry BUDDING_MALACHITE = add(cut("budding_malachite", AdditionalGems.malachite, MALACHITE_COLOR)
            .multiplyBase(MoreSpellSchools.NATURE.id, 0.04F));
    public static final GemCutBuilder.Entry FURIOUS_MALACHITE = add(cut("furious_malachite", AdditionalGems.malachite, MALACHITE_COLOR)
            .multiplyBase(Identifier.of(AdditionalJewelryItems.RAGE), 0.05F));
    public static final GemCutBuilder.Entry VERDANT_MALACHITE = add(cut("verdant_malachite", AdditionalGems.malachite, MALACHITE_COLOR)
            .multiplyBase(Identifier.of(AdditionalJewelryItems.DAMAGE_REFLECT), 0.03F));
}
