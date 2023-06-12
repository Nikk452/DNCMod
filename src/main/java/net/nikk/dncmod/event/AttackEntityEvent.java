package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AttackEntityEvent implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if(!player.world.isClient){
            if (entity.isAttackable()) if (!entity.handleAttack(player)) {
                if (entity instanceof LivingEntity && hand != Hand.OFF_HAND && !player.isSpectator()) {
                    NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
                    if (nbt.getBoolean("created")){
                        Item item = player.getStackInHand(hand).getItem();
                        float Damage = (float) nbt.getIntArray("stat_mod")[0]>nbt.getIntArray("stat_mod")[1]?nbt.getIntArray("stat_mod")[0]:nbt.getIntArray("stat_mod")[1];
                        int StatMod = (int)Damage;
                        if(item != Items.AIR){
                            List<EntityAttributeModifier> attr = item.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_DAMAGE).stream().toList();
                            if(attr.size()>0) {
                                int Roll = player.getRandom().nextBetween(1,(int)attr.get(0).getValue());
                                Damage+=Roll;
                                PacketByteBuf buf = PacketByteBufs.create();
                                buf.writeIntArray(new int[]{Roll,StatMod,chooseDice((int)attr.get(0).getValue())});
                                ServerPlayNetworking.send((ServerPlayerEntity)player, Networking.DICE_ID, buf);
                            }
                            else Damage*=0.50;
                        }else Damage*=0.75;
                        int weapon_mod = 0;
                        float AfterDot = player.getRandom().nextBetween(1, 20) + weapon_mod + StatMod;
                        Damage += chooseCrit(item)*0.01 + (float) (AfterDot / Math.pow(10,(""+AfterDot).length()));
                        ((LivingEntity)entity).damage(DamageSource.player(player), Damage);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        return ActionResult.PASS;
    }
    private static int chooseDice(int x){
        if(x>12) return 20;
        else if (x % 2 == 0 && x != 0) return x;
        else if (x==0) return 2;
        else return x+1;
    }
    private static int chooseCrit(Item X){
        if (Items.AIR.equals(X)) {
            return 1;
        } else if (Items.BEDROCK.equals(X)) {
            return 10;
        } else {
            return 2;
        }
    }
}
