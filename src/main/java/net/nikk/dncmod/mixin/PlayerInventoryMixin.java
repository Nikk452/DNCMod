package net.nikk.dncmod.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Inject(method = "addStack(Lnet/minecraft/item/ItemStack;)I", at = @At("HEAD"), cancellable = true)
    private void injected(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        NbtCompound nbt = ((IEntityDataSaver)((PlayerInventory)(Object)this).player).getPersistentData();
        int str = nbt.getIntArray("stats")[0];
        int count = stack.getCount();
        for(int i = 0; i<((PlayerInventory)(Object)this).main.size(); ++i){
            count+=((PlayerInventory)(Object)this).main.get(i).getCount();
        }
        for(int i = 0; i<((PlayerInventory)(Object)this).armor.size(); ++i){
            count+=((PlayerInventory)(Object)this).armor.get(i).getCount();
        }
        count+=((PlayerInventory)(Object)this).offHand.get(0).getCount();
        if(str<count) {
            ((PlayerInventory)(Object)this).player.sendMessage(Text.literal("You Can't Pick That").fillStyle(Style.EMPTY.withColor(Formatting.RED)),true);
            cir.setReturnValue(stack.getCount());
        }
    }
}
