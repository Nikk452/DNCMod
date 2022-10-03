package net.nikk.dncmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

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
}
