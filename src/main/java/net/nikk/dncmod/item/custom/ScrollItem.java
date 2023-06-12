package net.nikk.dncmod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.nikk.dncmod.entity.custom.SpellEntity;

public class ScrollItem extends Item {
    public ScrollItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(itemStack.getOrCreateNbt().contains("spell")){
            if(!world.isClient()){
                SpellEntity abstractarrowentity = createArrow(world, itemStack, user);
                abstractarrowentity.setVelocity(user, user.getPitch(), user.getYaw(),
                        0.0F, 3.0F, 0.0F);
                abstractarrowentity.setDamage(2.5);
                abstractarrowentity.age = 35;
                abstractarrowentity.hasNoGravity();
                world.spawnEntity(abstractarrowentity);
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
                return TypedActionResult.consume(itemStack);
            }
            return TypedActionResult.consume(itemStack);
        }
        else return super.use(world, user, hand);
    }
    public SpellEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        SpellEntity arrowentity = new SpellEntity(worldIn, shooter);
        return arrowentity;
    }

    public static float getArrowVelocity(int charge) {
        float f = (float) charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }
    @Override
    public Rarity getRarity(ItemStack itemStack) {
        return itemStack.getOrCreateNbt().contains("spell")?Rarity.RARE:super.getRarity(itemStack);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getOrCreateNbt().contains("spell") || super.hasGlint(stack);
    }
}
