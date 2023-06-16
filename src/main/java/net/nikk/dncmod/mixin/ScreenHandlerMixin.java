package net.nikk.dncmod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ClickType;
import net.nikk.dncmod.util.WeightManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

import static net.minecraft.screen.ScreenHandler.canInsertItemIntoSlot;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {
    @Shadow
    public abstract ItemStack getCursorStack();

    @Shadow
    public abstract void setCursorStack(ItemStack stack);

    @Shadow
    private StackReference getCursorStackReference() {
        return StackReference.EMPTY;
    }

    @Inject(method = "internalOnSlotClick", at = @At("HEAD"), cancellable = true)
    private void testBannedItemSystem(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if(slotIndex >= 0){
            Slot slot = ((ScreenHandler)(Object)this).slots.get(slotIndex);
            ItemStack clickedStack = slot.getStack();
            if(clickedStack.getItem().equals(Items.ACACIA_BOAT)){
                if(!actionType.equals(SlotActionType.THROW)){
                    ci.cancel();
                }
            }
        }
    }
}
