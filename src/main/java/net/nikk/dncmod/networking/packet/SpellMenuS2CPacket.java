package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.nikk.dncmod.screen.CharCreationScreen1;
import net.nikk.dncmod.screen.SpellCreationScreen;
import net.nikk.dncmod.util.IEntityDataSaver;

public class SpellMenuS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        //((IEntityDataSaver) client.player).getPersistentData().put("dncmod.chart",buf.readNbt());
        boolean bl = buf.readBoolean();
        client.execute(() -> {
            // Everything in this lambda is run on the render thread
            if(bl){
                client.setScreen(new SpellCreationScreen(Text.literal("Spell Creation")));
            }else client.player.sendMessage(Text.literal("You can't use that!")
                    .fillStyle(Style.EMPTY.withColor(Formatting.RED)), true);

        });
    }
}
