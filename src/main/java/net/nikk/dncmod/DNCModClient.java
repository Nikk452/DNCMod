package net.nikk.dncmod;

import net.fabricmc.api.ClientModInitializer;
import net.nikk.dncmod.event.KeyInputHandler;
import net.nikk.dncmod.networking.Networking;

public class DNCModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        Networking.RegisterS2CPackets();
    }
}
