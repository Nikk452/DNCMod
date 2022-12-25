package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import java.util.Objects;

public class ExclusiveServer implements ServerLifecycleEvents.ServerStarted {
    @Override
    public void onServerStarted(MinecraftServer server) {
        String ip = ""+server.getServerIp()+":"+server.getServerPort();
        if(server.isRemote()) if(!Objects.equals(ip, "51.79.7.98:25566")){
            throw new RuntimeException();
        }
    }
}
