package net.nikk.dncmod.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
@SuppressWarnings("unused")
@Mixin(PlayerListS2CPacket.class)
public class PlayerListS2CPacketMixin {

    @Redirect(method = "entryFromPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;getGameProfile()Lcom/mojang/authlib/GameProfile;"))
    private static GameProfile modifyGameProfile(ServerPlayerEntity instance) {
        NbtCompound nbt = ((IEntityDataSaver)instance).getPersistentData();
        String full_name = nbt.getString("first_name")+" "+nbt.getString("last_name");
        return new GameProfile(instance.getGameProfile().getId(), full_name.length()>2?full_name:"Player");
    }
}
