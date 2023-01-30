package net.nikk.dncmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.screen.*;
import net.nikk.dncmod.util.IEntityDataSaver;

import java.util.ArrayList;

public class ModHudCallback implements HudRenderCallback{
    private int dice_time = 0;
    private int current_dice = 0;
    private ArrayList<Integer> LabelTime = new ArrayList<>();
    private int[] last_label = {};
    final Identifier EXP_BAR = new Identifier(DNCMod.MOD_ID,
            "textures/gui/xp_bar_ui.png");
    final Identifier FULL_EXP_BAR = new Identifier(DNCMod.MOD_ID,
            "textures/gui/full_xp_bar_ui.png");
    final Identifier HEALTH_BAR = new Identifier(DNCMod.MOD_ID,
            "textures/gui/health_bar.png");
    final Identifier MANA_BAR = new Identifier(DNCMod.MOD_ID,
            "textures/gui/mana_bar_ui.png");

    @Override
    public void onHudRender(MatrixStack matrices, float tickDelta) {
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
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        int k = scaledWidth - 34;
        int l = scaledHeight - 34;
        //ui rendering
        if(nbt.getBoolean("created")){
            //dice rendering
            if(nbt.getIntArray("dice").length>0){
                Identifier DICE = new Identifier(DNCMod.MOD_ID,
                        "textures/gui/dice"+nbt.getIntArray("dice")[2]+".png");
                this.dice_time++;
                int t = (""+nbt.getIntArray("dice")[0]).length()>1?k+11:k+13;
                if(this.current_dice!=nbt.getIntArray("dice")[0]){
                    if(this.current_dice>0) this.dice_time = 30;
                    this.current_dice=nbt.getIntArray("dice")[0];
                }
                if(this.dice_time<=10){
                    RenderSystem.setShaderColor(this.dice_time/10f, this.dice_time/10f, this.dice_time/10f, this.dice_time/10f);
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor(this.dice_time/10f, this.dice_time/10f, this.dice_time/10f, this.dice_time/10f);
                    RenderSystem.setShaderTexture(0,DICE);
                    DrawableHelper.drawTexture(matrices, k, l,32,32, 0, 0, 32, 32,32, 32);
                } else if (this.dice_time<=40) {
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                    RenderSystem.setShaderTexture(0,DICE);
                    DrawableHelper.drawTexture(matrices, k, l,32,32, 0, 0, 32, 32,32, 32);
                    textRenderer.draw(matrices, ""+this.current_dice, t, l+13, 0);
                    textRenderer.draw(matrices, nbt.getIntArray("dice")[1]+" +", t-25, l+13, 0);
                }else if (this.dice_time<=70) {
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor((90-this.dice_time)/50f, (90-this.dice_time)/50f, (90-this.dice_time)/50f, (90-this.dice_time)/50f);
                    RenderSystem.setShaderTexture(0,DICE);
                    DrawableHelper.drawTexture(matrices, k, l,32,32, 0, 0, 32, 32,32, 32);
                    textRenderer.draw(matrices, ""+this.current_dice, t, l+13, 0);
                    textRenderer.draw(matrices, nbt.getIntArray("dice")[1]+" +", t-25, l+13, 0);
                }else if (this.dice_time<=90) {
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor((90-this.dice_time)/50f, (90-this.dice_time)/50f, (90-this.dice_time)/50f, (90-this.dice_time)/50f);
                    RenderSystem.setShaderTexture(0,DICE);
                    DrawableHelper.drawTexture(matrices, k, l,32,32, 0, 0, 32, 32,32, 32);
                } else{
                    nbt.putIntArray("dice",new IntArrayList());
                    this.current_dice=0;
                    this.dice_time=0;
                }
            }
            //label rendering
            /**
            if(nbt.getIntArray("label").length>0){
                if(this.last_label!=nbt.getIntArray("label")){
                    this.last_label=nbt.getIntArray("label");
                    
                }
                this.dice_time++;
                if(this.dice_time<=10){
                    RenderSystem.setShaderColor(this.dice_time/10f, this.dice_time/10f, this.dice_time/10f, this.dice_time/10f);
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor(this.dice_time/10f, this.dice_time/10f, this.dice_time/10f, this.dice_time/10f);
                    RenderSystem.setShaderTexture(0,DICE);
                    DrawableHelper.drawTexture(matrices, k, l,32,32, 0, 0, 32, 32,32, 32);
                } else if (this.dice_time<=90) {
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                    RenderSystem.setShaderTexture(0,DICE);
                    DrawableHelper.drawTexture(matrices, k, l,32,32, 0, 0, 32, 32,32, 32);
                    textRenderer.draw(matrices, ""+this.current_dice, t, l+13, 0);
                    textRenderer.draw(matrices, nbt.getIntArray("dice")[1]+" +", t-25, l+13, 0);
                } else{
                    nbt.putIntArray("dice",new IntArrayList());
                    this.current_dice=0;
                    this.dice_time=0;
                }
            }**/
        }
        //bars rendering
        if(!client.interactionManager.getCurrentGameMode().isCreative()&&client.interactionManager.getCurrentGameMode()!= GameMode.SPECTATOR){
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            //xp bar rendering
            int i = nbt.getInt("max_experience")>0?nbt.getInt("max_experience"):500;
            float exp_progress = nbt.getInt("experience")>=i?1.0F:(float)nbt.getInt("experience")/(float)i;
            RenderSystem.setShaderTexture(0, exp_progress==1.0F?FULL_EXP_BAR:EXP_BAR);
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
    }
    private static void addLabel(){

    }
}
