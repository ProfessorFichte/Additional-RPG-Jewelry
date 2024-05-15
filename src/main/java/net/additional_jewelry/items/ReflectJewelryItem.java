package net.additional_jewelry.items;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.jewelry.api.JewelryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.more_rpg_classes.entity.attribute.MRPGCEntityAttributes;

import java.util.UUID;

public class ReflectJewelryItem extends JewelryItem {
    public float value = 0;
    public ReflectJewelryItem(Settings settings, String lore, float value) {
        super(settings, lore);
        this.value = value;
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        modifiers.put(MRPGCEntityAttributes.DAMAGE_REFLECT_MODIFIER, new EntityAttributeModifier(
                uuid, "more_rpg_classes:damage_reflect_attribute", value, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        return modifiers;
    }
}
