package net.nikk.dncmod.mixin;

import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerList.class)
public class ServerListMixin {
    @Redirect(method = "loadFile()V", at = @At(value = "INVOKE", target ="net/minecraft/nbt/NbtCompound.getList (Ljava/lang/String;I)Lnet/minecraft/nbt/NbtList;"))
    private NbtList addOfficialServer(NbtCompound nbtCompound, String string, int i){
        NbtList nbtList = nbtCompound.getList(string,i);

        NbtCompound OfficialServer = (new ServerInfo("Official Server", "51.79.7.98:25566",false)).toNbt();
        if(nbtList.isEmpty()){
            OfficialServer.putBoolean("hidden", false);
            nbtList.add(0, OfficialServer);
        }
        return nbtList;
    }
}
