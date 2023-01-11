package net.nikk.dncmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderLayer;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.client.ModHudCallback;
import net.nikk.dncmod.event.KeyInputHandler;
import net.nikk.dncmod.event.ToolTipCallbackEvent;
import net.nikk.dncmod.networking.Networking;
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
        ItemTooltipCallback.EVENT.register(new ToolTipCallbackEvent());
    }
}
