package net.nikk.dncmod.event;

import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;

public class ModifyItemsAttributesEvent implements ModifyItemAttributeModifiersCallback {
    @Override
    public void modifyAttributeModifiers(ItemStack stack, EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
        //attributeModifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, MODIFIER);
    }
}
