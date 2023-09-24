package net.nikk.dncmod.screen;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class SpellBookScreenHandler extends ScreenHandler {
    NbtList itemsTag;
    public static final int SLOT_COUNT = 5;
    private final Inventory inventory;
    public SpellBookScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5));
    }
    public SpellBookScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.SPELL_BOOK_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        checkSize(inventory, 5);
        inventory.onOpen(playerInventory.player);

        int j;
        for(j = 0; j < 5; ++j) {
            this.addSlot(new Slot(inventory, j, 44 + j * 18, 20));
        }

        for(j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, j * 18 + 51));
            }
        }

        for(j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 109));
        }

    }
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.inventory.size()) {
                if (!this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    private void loadItemsFromNBT() {
        NbtList itemsTag = inventory.getStack(0).getOrCreateNbt().getList("Items", NbtType.COMPOUND);
        for (int i = 0; i < itemsTag.size(); i++) {
            NbtCompound itemTag = itemsTag.getCompound(i);
            ItemStack stack = ItemStack.fromNbt(itemTag);
            this.slots.get(i).setStack(stack);
        }
    }

    private void saveItemsToNBT() {
        NbtList itemsTag = new NbtList();
        for (int i = 0; i < slots.size(); i++) {
            ItemStack stack = this.slots.get(i).getStack();
            if (!stack.isEmpty()) {
                itemsTag.add(stack.getNbt());
            }
        }
        inventory.getStack(0).getOrCreateNbt().put("Items", itemsTag);
    }
}
