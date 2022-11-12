package net.nikk.dncmod.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.DNCMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@SuppressWarnings("unused")
@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerWorld.getGameRules ()Lnet/minecraft/world/GameRules;", shift = At.Shift.BEFORE))
    private void syncConfigOnJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci){
        DNCMod.sendConfigSyncPacket(player);
    }
}
