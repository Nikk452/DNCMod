package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.config.ModConfig;
import net.nikk.dncmod.config.SystemTimeConfig;
import net.nikk.dncmod.config.TimeDataStorage;
import net.nikk.dncmod.tick.DefaultTicker;
import net.nikk.dncmod.tick.SystemTimeTicker;
import net.nikk.dncmod.tick.TimeTicker;
import net.nikk.dncmod.util.ITimeOperations;

import java.util.HashMap;

public class SyncConfigS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if(buf.readableBytes() > 0 ){
            boolean skyAnglePatch = buf.readBoolean();
            boolean syncWithSysTime = buf.readBoolean();
            boolean sysTimePerDim = buf.readBoolean();
            boolean nightSkip = buf.readBoolean();
            int speed = buf.readInt();
            boolean threshold = buf.readBoolean();
            int percentage = buf.readInt();
            boolean flatS = buf.readBoolean();
            String sunrise = buf.readString();
            String sunset = buf.readString();
            String timeZone = buf.readString();

            HashMap<String, TimeDataStorage> map = (HashMap<String, TimeDataStorage>) buf.readMap(PacketByteBuf::readString, packetByteBuf -> new TimeDataStorage(packetByteBuf.readLong(), packetByteBuf.readLong()));
            HashMap<String, SystemTimeConfig> sysMap = (HashMap<String, SystemTimeConfig>) buf.readMap(PacketByteBuf::readString, packetByteBuf -> new SystemTimeConfig(buf.readString(), buf.readString(), buf.readString()));
            DNCMod.timeDataMap = map;
            DNCMod.sysTimeMap = sysMap;


            DNCMod.CONFIG = new ModConfig(skyAnglePatch, syncWithSysTime, sysTimePerDim, nightSkip, speed, threshold, percentage, flatS);
            DNCMod.systemTimeConfig = new SystemTimeConfig(sunrise, sunset, timeZone);
            DNCMod.LOGGER.info("[Dungeons & Crafting] Configuration synchronized");
            ClientWorld clientWorld = MinecraftClient.getInstance().world;
            if(clientWorld != null) {
                String worldId = clientWorld.getRegistryKey().toString();
                ITimeOperations timeOps = (ITimeOperations) clientWorld;
                if(syncWithSysTime){
                    if(sysTimePerDim && sysMap.containsKey(worldId)) {
                        timeOps.setTimeTicker(new SystemTimeTicker((ITimeOperations) clientWorld, sysMap.get(worldId)));
                    } else timeOps.setTimeTicker(new SystemTimeTicker((ITimeOperations) clientWorld, DNCMod.systemTimeConfig));
                    DNCMod.LOGGER.info("[Dungeons & Crafting] System time ticker synchronized");
                }
                else {
                    if (map.containsKey(worldId)) {
                        TimeDataStorage storage = map.get(worldId);
                        timeOps.setTimeTicker(new TimeTicker(storage.dayDuration, storage.nightDuration));
                        DNCMod.LOGGER.info("[Dungeons & Crafting] Custom time ticker for world " + worldId + " synchronized");
                    } else timeOps.setTimeTicker(new DefaultTicker());
                }
            }
        }
    }
}
