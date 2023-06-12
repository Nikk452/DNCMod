package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.util.WeightManager;

import java.util.Objects;

public class ServerStartedEvent implements ServerLifecycleEvents.ServerStarted {
    @Override
    public void onServerStarted(MinecraftServer server) {
        if(DNCMod.isExclusive){
            String ip = ""+server.getServerIp()+":"+server.getServerPort();
            if(server.isRemote()) if(!Objects.equals(ip, "51.79.7.98:25566")){
                throw new RuntimeException();
            }
        }
        WeightManager.initialize(server);
    }
}
