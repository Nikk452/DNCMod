package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.nikk.dncmod.screen.CharCreationScreen1;
import net.nikk.dncmod.screen.StatScreen1;
import net.nikk.dncmod.util.IEntityDataSaver;

public class ExampleS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ((IEntityDataSaver) client.player).getPersistentData().put("dncmod.chart",buf.readNbt());
        client.execute(() -> {
            // Everything in this lambda is run on the render thread
            client.player.sendMessage(Text.literal("Test: " + ((IEntityDataSaver) client.player).getPersistentData().getBoolean("created"))
                    .fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), false);
            client.setScreen(new CharCreationScreen1("","","","", new int[]{0, 0, 0, 0, 0, 0},0, new int[]{0, 1, 2, 3, 4, 5},true));
        });
    }
}
