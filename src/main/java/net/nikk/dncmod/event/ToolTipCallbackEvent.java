package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.item.ModItems;
import net.nikk.dncmod.item.custom.ScrollItem;
import net.nikk.dncmod.item.custom.SpellBookItem;
import net.nikk.dncmod.util.WeightManager;

import java.util.List;

import static net.minecraft.util.Rarity.*;

public class ToolTipCallbackEvent implements ItemTooltipCallback{
    boolean isInPounds = DNCMod.CONFIG.isInPounds;
    @Override
    public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        lines.clear();
        lines.add(Text.literal("[Item: ").append(stack.getName()).append("]").setStyle(Style.EMPTY.withColor(stack.getRarity().formatting)));
        lines.add(Text.literal("Item Class: "+(stack.getRarity()==EPIC?"Rare":(stack.getRarity()==RARE?"Uncommon":(stack.getRarity()==UNCOMMON?"Common":"None")))).setStyle(Style.EMPTY.withColor(stack.getRarity().formatting)));
        getItemType(stack,lines, stack.getRarity());
        lines.add(Text.literal(""));
        lines.add(Text.literal("Item Description:").setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        personalLines(stack, lines, EquipmentSlot.MAINHAND, EntityAttributes.GENERIC_ATTACK_DAMAGE, 0);
        personalLines(stack, lines, EquipmentSlot.HEAD, EntityAttributes.GENERIC_ARMOR, 1);
        personalLines(stack, lines, EquipmentSlot.CHEST, EntityAttributes.GENERIC_ARMOR, 1);
        personalLines(stack, lines, EquipmentSlot.LEGS, EntityAttributes.GENERIC_ARMOR, 1);
        personalLines(stack, lines, EquipmentSlot.FEET, EntityAttributes.GENERIC_ARMOR, 1);
        float weight = WeightManager.getWeight(stack.getItem());
        if(isInPounds){
            weight = Math.round(weight/45360f);
            weight /= 100;
        }
        lines.add(Text.literal("Weight: "+(isInPounds?weight+" lb":weight/1000f+" kg")).setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)));
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
                                lines.add(Texts.setStyleIfAbsent(mutableText2, Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
                            }
                        } catch (Exception var19) {
                            nbtCompound.remove("Lore");
                        }
                    }
                }else{
                    DefaultLore(lines);
                }
            }else{
                DefaultLore(lines);
            }
        }else{
            DefaultLore(lines);
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
    private static void getItemType(ItemStack itemStack, List<Text> text, Rarity rarity){
        Item item = itemStack.getItem();
        if(item instanceof SwordItem){
            text.add(Text.literal("Type: Sword").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof ArmorItem){
            text.add(Text.literal("Type: Armor").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof AxeItem){
            text.add(Text.literal("Type: Axe").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof ToolItem || item instanceof BucketItem || item instanceof ShearsItem || item instanceof CompassItem || item.equals(Items.CLOCK) || item instanceof FlintAndSteelItem || item instanceof FishingRodItem || item instanceof SpyglassItem || item.equals(Items.RECOVERY_COMPASS)){
            text.add(Text.literal("Type: Tool").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof PotionItem || item.isFood()){
            text.add(Text.literal("Type: Consumable").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof BowItem || item instanceof CrossbowItem){
            text.add(Text.literal("Type: Ranged").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof ShieldItem){
            text.add(Text.literal("Type: Shield").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof TridentItem){
            text.add(Text.literal("Type: Trident").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof ScrollItem){
            if(itemStack.getOrCreateNbt().contains("spell")) text.add(Text.literal("Type: Magic Scroll").setStyle(Style.EMPTY.withColor(rarity.formatting)));
            else text.add(Text.literal("Type: Scroll").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        } else if(item instanceof BookItem || item instanceof WritableBookItem || item instanceof WrittenBookItem || item instanceof EnchantedBookItem || item instanceof KnowledgeBookItem){
            text.add(Text.literal("Type: Book").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof BlockItem || item.getGroup()==ItemGroup.DECORATIONS){
            text.add(Text.literal("Type: Placeable").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }else if(item instanceof ArrowItem){
            text.add(Text.literal("Type: Arrow").setStyle(Style.EMPTY.withColor(rarity.formatting)));
        }
        else text.add(Text.literal("Type: Material").setStyle(Style.EMPTY.withColor(rarity.formatting)));
    }
    private static void DefaultLore(List<Text> lines){
        lines.add(Text.literal("This item has a cool story").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
        lines.add(Text.literal("but devs keep it a secret").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
        lines.add(Text.literal("and no one knows why").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
    }
}
