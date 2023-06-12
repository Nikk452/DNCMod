package net.nikk.dncmod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
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
    private void injected(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        Slot slot = slotIndex < 0 ? null : ((ScreenHandler)(Object)this).slots.get(slotIndex);
        if (slot != null){
            ItemStack clickedStack = slot.getStack();
            int n;
            if(!(slot.inventory instanceof PlayerInventory)){
                if(actionType.equals(SlotActionType.PICKUP) && (button == 0 || button == 1)){
                    ClickType clickType = button == 0 ? ClickType.LEFT : ClickType.RIGHT;
                    ItemStack cursorStack = this.getCursorStack();

                    player.onPickupSlotClick(cursorStack, slot.getStack(), clickType);
                    if (!cursorStack.onStackClicked(slot, clickType, player) && !clickedStack.onClicked(cursorStack, slot, clickType, player, this.getCursorStackReference())) {
                        if (clickedStack.isEmpty()) {
                            if (!cursorStack.isEmpty()) {
                                n = clickType == ClickType.LEFT ? cursorStack.getCount() : 1;
                                this.setCursorStack(slot.insertStack(cursorStack, n));
                            }
                        } else if (slot.canTakeItems(player)) {
                            if (cursorStack.isEmpty()) {
                                n = clickType == ClickType.LEFT ? clickedStack.getCount() : (clickedStack.getCount() + 1) / 2;
                                n = WeightManager.canPlayerPickup(player,clickedStack)?n:Math.min(WeightManager.getMaxPickUp(player,clickedStack),n);
                                Optional<ItemStack> optional = slot.tryTakeStackRange(n, Integer.MAX_VALUE, player);
                                optional.ifPresent((stack) -> {
                                    this.setCursorStack(stack);
                                    slot.onTakeItem(player, stack);
                                });
                            } else if (slot.canInsert(cursorStack)) {
                                if (ItemStack.canCombine(clickedStack, cursorStack)) {
                                    n = clickType == ClickType.LEFT ? cursorStack.getCount() : 1;
                                    n = WeightManager.canPlayerPickup(player,clickedStack)?n:WeightManager.getMaxPickUp(player,clickedStack);
                                    this.setCursorStack(slot.insertStack(cursorStack, n));
                                } else if (WeightManager.canPlayerPickup(player, clickedStack) &&(cursorStack.getCount() <= slot.getMaxItemCount(cursorStack))) {
                                    this.setCursorStack(clickedStack);
                                    slot.setStack(cursorStack);
                                }
                            } else if (ItemStack.canCombine(clickedStack, cursorStack)) {
                                Optional<ItemStack> optional2 = slot.tryTakeStackRange(clickedStack.getCount(), cursorStack.getMaxCount() - cursorStack.getCount(), player);
                                optional2.ifPresent((stack) -> {
                                    cursorStack.increment(WeightManager.canPlayerPickup(player,stack)?stack.getCount():WeightManager.getMaxPickUp(player,clickedStack));
                                    slot.onTakeItem(player, stack);
                                });
                            }
                        }
                    }

                    slot.markDirty();
                    ci.cancel();
                }else if(actionType.equals(SlotActionType.SWAP)){
                    if(!clickedStack.isEmpty()) {
                        ItemStack LostStack = player.getInventory().getStack(button);
                        if(!WeightManager.canPlayerPickup(player,clickedStack,LostStack)){
                            ci.cancel();
                        }
                    }
                }else if (actionType == SlotActionType.QUICK_MOVE) {
                    if (!WeightManager.canPlayerPickup(player,clickedStack)) {
                        ci.cancel();
                    }
                }
            }
        }
        if(actionType.equals(SlotActionType.PICKUP_ALL)){
            int x = WeightManager.getMaxPickUp(player,this.getCursorStack())+WeightManager.getItemCountInInv(player,this.getCursorStack());
            int y=0;
            for(int i = 0;((ScreenHandler)(Object)this).slots.size()>i;i++){
                y = ((ScreenHandler)(Object)this).slots.get(i).getStack().getItem().equals(this.getCursorStack().getItem())?y+((ScreenHandler)(Object)this).slots.get(i).getStack().getCount():y;
            }
            if(y<=x) ci.cancel();
        }
    }
}
