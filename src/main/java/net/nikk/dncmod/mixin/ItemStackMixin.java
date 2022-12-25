package net.nikk.dncmod.mixin;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void getTooltip(PlayerEntity entity, TooltipContext tooltipContext, CallbackInfoReturnable<List<Text>> info) {
        List<Text> texts = info.getReturnValue();
        texts.add(Text.literal("This item has a cool lore"));
        texts.add(Text.literal("but devs keep it a secret"));
        texts.add(Text.literal("and nobody knows why"));
        ItemTooltipCallback.EVENT.invoker().getTooltip((ItemStack) (Object) this, tooltipContext, texts);
    }
}