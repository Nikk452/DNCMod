package net.nikk.dncmod.mixin;

import net.fabricmc.fabric.api.event.registry.RegistryIdRemapCallback;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.util.HashMapOf;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    private static final HashMap<String,Integer> primalItems = (HashMap<String, Integer>) Map.of("test",2,"test2",5);
    @Inject(method = "onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At("HEAD"), cancellable = true)
    private void injected(PlayerEntity player, CallbackInfo ci) {
        if(!((IEntityDataSaver)player).getPersistentData().getBoolean("created")){
            player.sendMessage(Text.literal("You Can't Pick That").fillStyle(Style.EMPTY.withColor(Formatting.RED).withBold(true)),true);
            ci.cancel();
        }

        ItemStack itemStack = ((ItemEntity)(Object)this).getStack();
        Item item = itemStack.getItem();
        int i = itemStack.getCount();
        RecipeManager recipeManager = player.getWorld().getRecipeManager();
        Optional<? extends Recipe<?>> x = recipeManager.get(Registry.ITEM.getId(item));
        if(x.isPresent()){
            recipeManager.listAllOfType(RecipeType.CRAFTING).get(0).getOutput();
            List<Ingredient> ingredients = x.get().getIngredients();
            for (Ingredient z:ingredients) {
                Optional<ItemStack> t = Arrays.stream(z.getMatchingStacks()).findAny();
                if(t.isPresent()) player.sendMessage(Text.literal("ingr = "+ t.get().getName()),false);
            }
            player.sendMessage(Text.literal("-----------"),false);
        }
        ci.cancel();
    }

    private static void findRegistry(){
        System.out.println("");
    }
}
