package net.nikk.dncmod.networking.packet;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.config.ModConfig;

public class SyncConfigS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if(buf.readableBytes() > 0 ){
            int xp_per_lvl_multi = buf.readInt();
            boolean isRace_human_approved = buf.readBoolean();
            boolean isRace_dwarf_approved = buf.readBoolean();
            boolean isRace_elf_approved = buf.readBoolean();
            boolean isClass_fighter_approved = buf.readBoolean();
            boolean isClass_druid_approved = buf.readBoolean();
            boolean isClass_cleric_approved = buf.readBoolean();
            boolean isClass_wizard_approved = buf.readBoolean();
            boolean isClass_sorcerer_approved = buf.readBoolean();
            boolean isClass_monk_approved = buf.readBoolean();


            DNCMod.CONFIG = new ModConfig(xp_per_lvl_multi, isRace_human_approved,
                    isRace_dwarf_approved, isRace_elf_approved,
                    isClass_fighter_approved, isClass_druid_approved,
                    isClass_cleric_approved, isClass_wizard_approved,
                    isClass_sorcerer_approved, isClass_monk_approved);
            DNCMod.LOGGER.info("[Dungeons & Crafting] Configuration synchronized");
        }
    }
}
