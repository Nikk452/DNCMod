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
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
            final Identifier EXP_BAR = new Identifier(DNCMod.MOD_ID,
                    "textures/gui/xp_bar_ui.png");
            final Identifier FULL_EXP_BAR = new Identifier(DNCMod.MOD_ID,
                    "textures/gui/full_xp_bar_ui.png");
            final Identifier HEALTH_BAR = new Identifier(DNCMod.MOD_ID,
                    "textures/gui/health_bar.png");
            final Identifier MANA_BAR = new Identifier(DNCMod.MOD_ID,
                    "textures/gui/mana_bar_ui.png");
            MinecraftClient client = MinecraftClient.getInstance();
            NbtCompound nbt = ((IEntityDataSaver)client.player).getPersistentData();
            if(nbt.isEmpty()){
                if(!(client.currentScreen instanceof CharCreationScreen1 || client.currentScreen instanceof CharCreationScreen2 || client.currentScreen instanceof CharCreationScreen3 || client.currentScreen instanceof CharCreationScreen4 || client.currentScreen instanceof CharCreationScreen5)){
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBoolean(true);
                    ClientPlayNetworking.send(Networking.REFRESH_STATS_ID, buf);
                }
            }
            TextRenderer textRenderer = client.textRenderer;
            int scaledHeight = client.getWindow().getScaledHeight();
            int scaledWidth = client.getWindow().getScaledWidth();
            int x = scaledWidth / 2 - 91;
            int i = nbt.getInt("max_experience")>0?nbt.getInt("max_experience"):500;
            float exp_progress = nbt.getInt("experience")>=i?1.0F:(float)nbt.getInt("experience")/(float)i;
            if(!client.interactionManager.getCurrentGameMode().isCreative()&&client.interactionManager.getCurrentGameMode()!=GameMode.SPECTATOR){
                //xp bar rendering
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, exp_progress==1.0F?FULL_EXP_BAR:EXP_BAR);
                int k;
                int l;
                if (i > 0) {
                    k = (int)(exp_progress * 182.0F);
                    l = scaledHeight - 32 + 3;
                    DrawableHelper.drawTexture(matrices, x, l, 0, 0, 182, 5,182, 10);
                    if (k > 0) {
                        DrawableHelper.drawTexture(matrices, x, l, 0, 5, k, 5,182, 10);
                    }
                }
                if (nbt.getInt("total_level") > 0) {
                    String string = "" + nbt.getInt("total_level");
                    k = (scaledWidth - textRenderer.getWidth(string)) / 2;
                    l = scaledHeight - 31 - 4;
                    textRenderer.draw(matrices, string, (float)(k + 1), (float)l, 0);
                    textRenderer.draw(matrices, string, (float)(k - 1), (float)l, 0);
                    textRenderer.draw(matrices, string, (float)k, (float)(l + 1), 0);
                    textRenderer.draw(matrices, string, (float)k, (float)(l - 1), 0);
                    textRenderer.draw(matrices, string, (float)k, (float)l, exp_progress==1.0F?16448889:3328754);
                    client.getProfiler().pop();
                }
                //health bar rendering
                RenderSystem.setShaderTexture(0,HEALTH_BAR);
                float max_hp = client.player.getMaxHealth();
                float cur_hp = client.player.getHealth();
                if (cur_hp >= 0) {
                    k = (int)(cur_hp/max_hp * 80.0F);
                    l = scaledHeight - 32 - 7;
                    DrawableHelper.drawTexture(matrices, x, l,80,9, 0, 0, 80, 9,80, 18);
                    if (k > 0) {
                        DrawableHelper.drawTexture(matrices, x, l,k,9, 0, 9, 80, 9,80, 18);
                    }
                }
            }
        });
    }
}
