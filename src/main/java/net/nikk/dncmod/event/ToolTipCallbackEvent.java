package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import java.util.List;

public class ToolTipCallbackEvent implements ItemTooltipCallback{
    @Override
    public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        lines.clear();
        lines.add(stack.getName());
        lines.add(Text.literal(""));
        lines.add(Text.literal("Item Description:").setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        personalLines(stack, lines, EquipmentSlot.MAINHAND, EntityAttributes.GENERIC_ATTACK_DAMAGE, 0);
        personalLines(stack, lines, EquipmentSlot.HEAD, EntityAttributes.GENERIC_ARMOR, 1);
        personalLines(stack, lines, EquipmentSlot.CHEST, EntityAttributes.GENERIC_ARMOR, 1);
        personalLines(stack, lines, EquipmentSlot.LEGS, EntityAttributes.GENERIC_ARMOR, 1);
        personalLines(stack, lines, EquipmentSlot.FEET, EntityAttributes.GENERIC_ARMOR, 1);
        if (stack.hasNbt()) {
            if (stack.getNbt().contains("display", 10)) {
                NbtCompound nbtCompound = stack.getNbt().getCompound("display");
                if (nbtCompound.getType("Lore") == 9) {
                    NbtList nbtList = nbtCompound.getList("Lore", 8);
                    for(int j = 0; j < nbtList.size(); ++j) {
                        String string = nbtList.getString(j);
                        try {
                            MutableText mutableText2 = Text.Serializer.fromJson(string);
                            if (mutableText2 != null) {
                                lines.add(Texts.setStyleIfAbsent(mutableText2, Style.EMPTY.withColor(Formatting.DARK_PURPLE)));
                            }
                        } catch (Exception var19) {
                            nbtCompound.remove("Lore");
                        }
                    }
                }else{
                    lines.add(Text.literal("This item has a cool story").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
                    lines.add(Text.literal("but devs keep it a secret").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
                    lines.add(Text.literal("and no one knows why").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
                }
            }else{
                lines.add(Text.literal("This item has a cool story").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
                lines.add(Text.literal("but devs keep it a secret").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
                lines.add(Text.literal("and no one knows why").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
            }
        }else{
            lines.add(Text.literal("This item has a cool story").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
            lines.add(Text.literal("but devs keep it a secret").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
            lines.add(Text.literal("and no one knows why").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
        }
    }

    private static void personalLines(ItemStack stack, List<Text> lines, EquipmentSlot equipmentSlot, EntityAttribute entityAttribute, int st){
        List<EntityAttributeModifier> value = stack.getAttributeModifiers(equipmentSlot).get(entityAttribute).stream().toList();
        if(value.size()>0) if(value.get(0).getValue()>0){
            if(st == 0){
                lines.add(Text.literal("  Not Proficient").setStyle(Style.EMPTY.withColor(Formatting.RED)));
                lines.add(Text.literal("  1-"+(int)(value.get(0).getValue()+(value.get(0).getValue()%2==0?0:1))+" Slashing Damage").setStyle(Style.EMPTY.withColor(Formatting.WHITE)));
            }
            if(st == 1){
                lines.add(Text.literal( "  "+(int)(value.get(0).getValue())+" Armor Class").setStyle(Style.EMPTY.withColor(Formatting.BLUE)));
            }
        }
    }
}
