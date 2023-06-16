package net.nikk.dncmod.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.nikk.dncmod.event.PickItemCallBack;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@SuppressWarnings("unused")
@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    //private static final HashMap<String,Integer> primalItems = (HashMap<String, Integer>) Map.of("test",2,"test2",5);
    @Inject(method = "onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At("HEAD"), cancellable = true)
    private void testBannedItemSystem(PlayerEntity player, CallbackInfo ci) {
        ActionResult result = PickItemCallBack.EVENT.invoker().pickup(player, (ItemEntity) (Object) this);

        if(result == ActionResult.FAIL) {
            player.sendMessage(Text.literal("You Can't Pick That").fillStyle(Style.EMPTY.withColor(Formatting.RED).withBold(true)),true);
            ci.cancel();
        }
    }
}
