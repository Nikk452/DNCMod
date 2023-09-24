package net.nikk.dncmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.screen.*;
import net.nikk.dncmod.util.IEntityDataSaver;

import java.util.ArrayList;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class ModHudCallback implements HudRenderCallback{
    private int dice_time = 0;
    private int label_time = 0;
    private int current_dice = 0;
    private ArrayList<Integer> LabelTime = new ArrayList<>();
    private ArrayList<Integer> labelX = new ArrayList<>();
    private ArrayList<Integer> labelY = new ArrayList<>();
    private ArrayList<String[]> Labels = new ArrayList<>();
    private ArrayList<String> label_id = new ArrayList<>();
    private ArrayList<Integer> label_max = new ArrayList<>();
    private int last_label = 0;
    final Identifier EXP_BAR = new Identifier(DNCMod.MOD_ID,
            "textures/gui/xp_bar_ui.png");
    final Identifier GUI_ICONS_TEXTURE = new Identifier("textures/gui/icons.png");
    final Identifier UI = new Identifier(DNCMod.MOD_ID,"textures/gui/uifrag.png");
    final Identifier FULL_EXP_BAR = new Identifier(DNCMod.MOD_ID,
            "textures/gui/full_xp_bar_ui.png");
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
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
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
                    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor(this.dice_time/10f, this.dice_time/10f, this.dice_time/10f, this.dice_time/10f);
                    RenderSystem.setShaderTexture(0,DICE);
                    DrawableHelper.drawTexture(matrices, k, l,32,32, 0, 0, 32, 32,32, 32);
                } else if (this.dice_time<=40) {
                    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                    RenderSystem.setShaderTexture(0,DICE);
                    DrawableHelper.drawTexture(matrices, k, l,32,32, 0, 0, 32, 32,32, 32);
                    textRenderer.draw(matrices, ""+this.current_dice, t, l+13, 0);
                    textRenderer.draw(matrices, nbt.getIntArray("dice")[1]+" +", t-25, l+13, 0);
                }else if (this.dice_time<=70) {
                    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor((90-this.dice_time)/50f, (90-this.dice_time)/50f, (90-this.dice_time)/50f, (90-this.dice_time)/50f);
                    RenderSystem.setShaderTexture(0,DICE);
                    DrawableHelper.drawTexture(matrices, k, l,32,32, 0, 0, 32, 32,32, 32);
                    textRenderer.draw(matrices, ""+this.current_dice, t, l+13, 0);
                    textRenderer.draw(matrices, nbt.getIntArray("dice")[1]+" +", t-25, l+13, 0);
                }else if (this.dice_time<=90) {
                    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
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
            if(false) if(this.last_label!=nbt.getInt("label")){
                this.last_label=nbt.getInt("label");
                String[] temp = new String[this.last_label-1];
                int max = 0;
                for(int i = 0;i<this.last_label;i++) {
                    String key = "label"+i;
                    if(i!=0){
                        temp[i-1] = nbt.getString(key);
                        if(temp[i-1].length() > max) max = temp[i-1].length();
                    }
                    else this.label_id.add(nbt.getString(key));
                }
                this.label_max.add(max);
                this.Labels.add(temp);
                this.LabelTime.add((int) (client.world.getTime()+90));
                this.labelX.add(client.world.random.nextBetween(1,k+34-(max+4)*412/60));
                this.labelY.add(client.world.random.nextBetween(1,l+34-(temp.length+6)*256/25));
                this.last_label=0;
                nbt.putInt("label",0);
            }
            if(this.LabelTime.size()>0){
                for(int i = 0;i<this.LabelTime.size();i++){
                    if(this.LabelTime.get(i)>client.world.getTime()){
                        int deltaTime = (int) (this.LabelTime.get(i)-client.world.getTime());
                        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();
                        RenderSystem.setShaderColor(1.2f, 1.2f, 1.2f, 0.75f);
                        RenderSystem.setShaderTexture(0,new Identifier(DNCMod.MOD_ID, this.label_id.get(i)));
                        k=412;
                        l=256;
                        DrawableHelper.drawTexture(matrices, this.labelX.get(i), this.labelY.get(i),(this.label_max.get(i)+2)*k/60,(this.Labels.get(i).length+6)*l/25, 0, 0, 64, 32,64, 32);
                        for(int s=0;s<this.Labels.get(i).length;s++){
                            textRenderer.draw(matrices, ""+this.Labels.get(i)[s], this.labelX.get(i)+(((this.label_max.get(i)+2)*k/60)-textRenderer.getWidth(this.Labels.get(i)[s]))/2, this.labelY.get(i)+(3+s)*l/25, 15859709);
                        }
                    }else{
                        this.label_id.remove(i);
                        this.label_max.remove(i);
                        this.Labels.remove(i);
                        this.LabelTime.remove(i);
                        this.labelX.remove(i);
                        this.labelY.remove(i);
                    }
                }
            }
        }
        //bars rendering
        if(!client.interactionManager.getCurrentGameMode().isCreative()&&client.interactionManager.getCurrentGameMode()!= GameMode.SPECTATOR&&!client.options.hudHidden){
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
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
            RenderSystem.setShaderTexture(0,GUI_ICONS_TEXTURE);
            float max_hp = client.player.getMaxHealth();
            float cur_hp = client.player.getHealth();
            int state = fromPlayerState(client.player);
            if (cur_hp >= 0) {
                float f = (cur_hp/max_hp)*10f;
                f = (f-(int)f)*9f;
                l = scaledHeight - 39;
                for(int hearts = 9;hearts>=0;hearts--){
                    int v = 9 * (client.world.getLevelProperties().isHardcore() ? 5 : 0);
                    int p = x + (hearts % 10) * 8;
                    DrawableHelper.drawTexture(matrices, p, l,getU(true,false,0), v,9, 9, 256, 256);
                    if(hearts<cur_hp/max_hp*10) DrawableHelper.drawTexture(matrices, p, l,getU(false,false,state),v, hearts+1<=cur_hp/max_hp*10?9:(int)f, 9, 256, 256);
                }
            }
        }
    }
    private static int fromPlayerState(PlayerEntity player) {
        int state;
        if (player.hasStatusEffect(StatusEffects.POISON)) {
            state = 4;
        } else if (player.hasStatusEffect(StatusEffects.WITHER)) {
            state = 6;
        } else if (player.isFrozen()) {
            state = 9;
        } else {
            state = 2;
        }
        return state;
    }
    public static int getU(boolean Container, boolean blinking,int state) {
        int i;
        if (Container) {
            i = blinking ? 1 : 0;
        } else {
            i = blinking ? 2 : 0;
        }
        return 16 + (Container?0:state * 2 + i) * 9;
    }
}
