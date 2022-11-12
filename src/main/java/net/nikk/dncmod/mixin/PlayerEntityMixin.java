package net.nikk.dncmod.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
@SuppressWarnings("unused")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private int restTimer = 0;
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "createPlayerAttributes", at = @At("RETURN"), cancellable = true)
    private static void setMaxHealthAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir){
        DefaultAttributeContainer.Builder builder = cir.getReturnValue();
        int custom_health = 4;
        builder.add(EntityAttributes.GENERIC_MAX_HEALTH,custom_health);
    }

    @Inject(method = "getName", at = @At("RETURN"))
    private Text getNewName(CallbackInfoReturnable<Text> cir){
        if(((IEntityDataSaver)(PlayerEntity) (Object) this).getPersistentData().getBoolean("created"))
            return Text.literal(((IEntityDataSaver)(PlayerEntity) (Object) this).getPersistentData().getString("first_name")+" "+((IEntityDataSaver)(PlayerEntity) (Object) this).getPersistentData().getString("last_name"));
        return cir.getReturnValue();
    }
    int successTimes = 0;
    @Inject(method = "getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F", at = @At("RETURN"), cancellable = true)
    private void mineSkill(BlockState state, CallbackInfoReturnable<Float> cir) {
        Random random = new Random();
        NbtCompound nbt = ((IEntityDataSaver)(PlayerEntity) (Object) this).getPersistentData();
        float H = Math.min(state.getBlock().getHardness(), 5f);
        int DC = (int)(5*((Math.pow(H<1?1:H,2))/2f));
        int Jump_mod = nbt.getIntArray("skills")[1]+nbt.getIntArray("stat_mod")[0];
        int Dice = this.random.nextBetween(1,20);
        int Roll = Dice+Jump_mod;
        if(this.successTimes>=4) {
            if(!(this.getWorld().isClient())) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(Dice);
                ServerPlayNetworking.send((ServerPlayerEntity)(Object)this,Networking.DICE_ID, buf);
            }
            this.successTimes = 0;
            cir.setReturnValue(cir.getReturnValue() * random.nextFloat(0f,(Jump_mod>0?(float)Jump_mod:0.1f)/(Roll>DC*2?Roll>DC*4?1f:2f:4f)));
        }
        else if(Roll>DC){
            this.successTimes +=Roll>DC*2?Roll>DC*4?1:2:4;
            cir.setReturnValue(8.1E-4F*cir.getReturnValue());
        }
        else cir.setReturnValue(8.1E-4F*cir.getReturnValue());
    }
}
