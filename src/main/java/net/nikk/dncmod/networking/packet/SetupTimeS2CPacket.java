package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.config.TimeDataStorage;
import net.nikk.dncmod.tick.DefaultTicker;
import net.nikk.dncmod.tick.SystemTimeTicker;
import net.nikk.dncmod.tick.TimeTicker;
import net.nikk.dncmod.util.IEntityDataSaver;
import net.nikk.dncmod.util.ITimeOperations;

public class SetupTimeS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ClientWorld clientWorld = MinecraftClient.getInstance().world;
        if (clientWorld != null) {
            String worldId = clientWorld.getRegistryKey().toString();
            ITimeOperations timeOps = (ITimeOperations) clientWorld;
            if (DNCMod.CONFIG.syncWithSystemTime) {
                if(DNCMod.CONFIG.systemTimePerDimensions && DNCMod.sysTimeMap.containsKey(worldId)) {
                    timeOps.setTimeTicker(new SystemTimeTicker((ITimeOperations) clientWorld, DNCMod.sysTimeMap.get(worldId)));
                } else timeOps.setTimeTicker(new SystemTimeTicker((ITimeOperations) clientWorld, DNCMod.systemTimeConfig));
            }
            else if (DNCMod.timeDataMap != null && DNCMod.timeDataMap.containsKey(worldId)) {
                TimeDataStorage storage = DNCMod.timeDataMap.get(worldId);
                timeOps.setTimeTicker(new TimeTicker(storage.dayDuration, storage.nightDuration));
            } else timeOps.setTimeTicker(new DefaultTicker());
            DNCMod.LOGGER.info("[Time & Wind] Timedata reloaded on client");
        }
    }
}
