package net.nikk.dncmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.nikk.dncmod.block.ModBlocks;
import net.nikk.dncmod.client.ModHudCallback;
import net.nikk.dncmod.entity.ModEntities;
import net.nikk.dncmod.entity.client.GoblinEntityModel;
import net.nikk.dncmod.entity.client.GoblinEntityRenderer;
import net.nikk.dncmod.entity.client.SpellEntityRenderer;
import net.nikk.dncmod.entity.layer.ModModelLayers;
import net.nikk.dncmod.event.KeyInputHandler;
import net.nikk.dncmod.event.ToolTipCallbackEvent;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.screen.ModScreenHandlers;
import net.nikk.dncmod.screen.SpellBookScreen;
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
        EntityRendererRegistry.register(ModEntities.SPELL, SpellEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.SPELL_BOOK_SCREEN_HANDLER, SpellBookScreen::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.GOBLIN, GoblinEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.GOBLIN, GoblinEntityRenderer::new);
    }
}
