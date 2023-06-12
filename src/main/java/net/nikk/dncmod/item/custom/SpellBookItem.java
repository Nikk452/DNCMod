package net.nikk.dncmod.item.custom;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.nikk.dncmod.screen.SpellBookScreenHandler;

public class SpellBookItem extends BookItem {
    private static final String TAG_ITEMS = "Items";
    public SpellBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
                ItemStack book = player.getStackInHand(hand);
                NbtList itemsTag = book.getOrCreateNbt().getList(TAG_ITEMS, NbtType.COMPOUND);
                return new SpellBookScreenHandler(i, playerInventory);
            }, Text.of("Spell Book")));

            return TypedActionResult.success(player.getStackInHand(hand));
        }

        return TypedActionResult.pass(player.getStackInHand(hand));
    }
}
