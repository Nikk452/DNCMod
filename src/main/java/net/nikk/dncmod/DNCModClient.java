package net.nikk.dncmod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.client.ModHudCallback;
import net.nikk.dncmod.event.KeyInputHandler;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.screen.*;
import net.nikk.dncmod.util.IEntityDataSaver;
import net.nikk.dncmod.util.ModModelPredicate;

public class DNCModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        Networking.RegisterS2CPackets();
        ModModelPredicate.registerModModels();
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_IRON_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_WHITE_IRON_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_WHITE_IRON_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_WHITE_IRON_BUD, RenderLayer.getCutout());
        HudRenderCallback.EVENT.register(new ModHudCallback());
    }
}
