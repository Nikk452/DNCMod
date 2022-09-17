package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.nikk.dncmod.util.IEntityDataSaver;
import net.nikk.dncmod.util.StatData;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class RollStatsC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Everything here happens ONLY on the Server!
        StatData.addStat((IEntityDataSaver)player,0,"stats",0);
        if(Arrays.stream(((IEntityDataSaver) player).getCharacterData().getCompound("dncmod.chart").getIntArray("stats")).sum()<73){
            int[] stats = {0,0,0,0,0,0};
            while(Arrays.stream(stats).sum() < 73){
                for(int i = 0; i<6; i++){
                    int generateRandom = 3;
                    while(generateRandom==3) generateRandom = ThreadLocalRandom.current().nextInt(1, 9) + ThreadLocalRandom.current().nextInt(1, 9) + ThreadLocalRandom.current().nextInt(1, 9);
                    stats[i] = Math.min(generateRandom, 21);
                }
            }

            for(int i=0;i<6;i++) StatData.addStat((IEntityDataSaver)player,i,"stats",stats[i]);
        }
    }
}
