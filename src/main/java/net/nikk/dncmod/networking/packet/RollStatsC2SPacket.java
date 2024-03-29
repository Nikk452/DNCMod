package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.util.IEntityDataSaver;
import net.nikk.dncmod.util.StatData;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class RollStatsC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        server.execute(()->{
            final boolean AlphaStats = true;
            StatData.addStat((IEntityDataSaver)player,0,"stats",0);
            if(Arrays.stream(((IEntityDataSaver) player).getPersistentData().getIntArray("stats")).sum()<73){
                int[] stats = {0,0,0,0,0,0};
                if(!AlphaStats){
                    while(Arrays.stream(stats).sum() < 73){
                        for(int i = 0; i<6; i++){
                            int generateRandom = 3;
                            while(generateRandom==3) generateRandom = ThreadLocalRandom.current().nextInt(1, 9) + ThreadLocalRandom.current().nextInt(1, 9) + ThreadLocalRandom.current().nextInt(1, 9);
                            stats[i] = generateRandom>=21? 21:generateRandom;
                        }
                    }
                }else stats = new int[]{21, 19, 17, 15, 13, 11};

                for(int i=0;i<6;i++) StatData.addStat((IEntityDataSaver)player,i,"stats",stats[i]);
            }
        });
    }
}
