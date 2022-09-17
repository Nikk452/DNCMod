package net.nikk.dncmod.mixin;

import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerHealthChangerMixin {
	@Inject(method = "createPlayerAttributes", at = @At("RETURN"), cancellable = true)
	private static void setMaxHealthAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir){
		DefaultAttributeContainer.Builder builder = cir.getReturnValue();
		int custom_health = 4;
		builder.add(EntityAttributes.GENERIC_MAX_HEALTH,custom_health);
	}

}