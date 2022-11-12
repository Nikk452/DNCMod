package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.util.AttributeData;
import net.nikk.dncmod.util.IEntityDataSaver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatScreen1 extends Screen {
    PlayerEntity player = MinecraftClient.getInstance().player;
    NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
    int backgroundWidth = 412;
    int backgroundHeight = 256;
    private float shade_color;
    private double anime;
    private boolean animate;

    public StatScreen1(boolean animate) {
        super(Text.literal("Stat"));
        this.animate = animate;
    }

    @Override
    protected void init() {
        if (this.animate){
            this.anime = 300d;
            this.shade_color = 0f;
        }else{this.anime = -40;}
        this.addDrawableChild(new ButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> this.client.setScreen(new StatScreen2())));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //texture drawing
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if (this.anime>y || this.anime>-35) {
            this.anime -= 5;
            this.shade_color = this.shade_color >= 1f ? 1f : this.shade_color + 0.013f;
            RenderSystem.setShaderColor(this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f);
            renderBackground(matrices);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f);
            RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"));
            drawTexture(matrices, x, Math.max((int) this.anime, y), 0, 0, backgroundWidth, backgroundHeight, 412, 256);
        }else{
            if(this.animate) this.animate = false;
            RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
            renderBackground(matrices);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
            RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"));
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight,412,256);
            //drawTexture(matrices, LocationX, LocationY, Z?... 1 , u is 0, v is 0,ActualImageWidth,ActualImageHeight,texturewidthScaled,textureheightScaled);
            //text drawing
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            MatrixStack textRendererMatrixStack = new MatrixStack();
            textRendererMatrixStack.scale(1.0F, 1.0F, 1.0F);
            int line = backgroundHeight/30;
            int collum = backgroundWidth/30;
            String[] class_names = {"Fighter","Wizard","Druid","Cleric","Sorcerer","Monk"};
            int[] classes = nbt.getIntArray("classes");
            int idx = AttributeData.getIndexOfLargest(classes);
            Text[] texts = {
                    Text.literal("Status "),
                    Text.literal(" Information ").styled(style -> style.withUnderline(true)),
                    Text.literal("[Name: "+nbt.getString("first_name")+" "+nbt.getString("last_name")+"]"),
                    Text.literal("[Race: "+nbt.getString("race")+"]"),
                    Text.literal("[Health: "+(double) Math.round(player.getHealth()*10)/10+"/"+player.getMaxHealth()+"]"),
                    Text.literal("[Level: "+nbt.getInt("total_level")+" ("+nbt.getInt("experience")+"/"+nbt.getInt("max_experience")+")"+"]"),
                    Text.literal("[Hit Dice :"+nbt.getInt("hit_dice")+"]"),
                    Text.literal("[Proficiency Bonus: "+nbt.getInt("proficiency_modifier")+"]"),
                    Text.literal("[Ki Points: "+nbt.getInt("ki")+"]"),
                    Text.literal(" Class List ").styled(style -> style.withUnderline(true)),
                    Text.literal("   Stats   ").styled(style -> style.withUnderline(true)),
                    Text.literal("[Str"+"-  "+nbt.getIntArray("stats")[0]+" ("+nbt.getIntArray("stat_mod")[0]+")("+nbt.getIntArray("stat_throws")[0]+")]"),
                    Text.literal("[Dex"+"- "+nbt.getIntArray("stats")[1]+" ("+nbt.getIntArray("stat_mod")[1]+")("+nbt.getIntArray("stat_throws")[1]+")]"),
                    Text.literal("[Con"+"- "+nbt.getIntArray("stats")[2]+" ("+nbt.getIntArray("stat_mod")[2]+")("+nbt.getIntArray("stat_throws")[2]+")]"),
                    Text.literal("[Int"+"-  "+nbt.getIntArray("stats")[3]+" ("+nbt.getIntArray("stat_mod")[3]+")("+nbt.getIntArray("stat_throws")[3]+")]"),
                    Text.literal("[Wis"+"-  "+nbt.getIntArray("stats")[4]+" ("+nbt.getIntArray("stat_mod")[4]+")("+nbt.getIntArray("stat_throws")[4]+")]"),
                    Text.literal("[Cha"+"- "+nbt.getIntArray("stats")[5]+" ("+nbt.getIntArray("stat_mod")[5]+")("+nbt.getIntArray("stat_throws")[5]+")]"),
                    Text.literal(" ["+class_names[idx]+" Lv."+classes[idx]+"]")};
            int[] locsX = {width/2-18,x+collum*4,x+collum*4,x+collum*4,x+collum*4,x+collum*4,x+collum*4,x+collum*4,x+collum*4,x+collum*13+collum/2,x+collum*23,x+collum*22-4,x+collum*22-4,x+collum*22-4,x+collum*22-4,x+collum*22-4,x+collum*22-4,x+collum*13+4};
            int[] locsY = {y+20+line*2/3, y+20+line*2+line,y+20+line*4+line,y+20+line*6+line,y+20+line*8+line,y+20+line*10+line,y+20+line*12+line,y+20+line*14+line,y+20+line*16+line,y+20+line*2+line,y+20+line*2+line,y+20+line*4+line,y+20+line*6+line,y+20+line*8+line,y+20+line*10+line,y+20+line*12+line,y+20+line*14+line,y+20+line*4+line};
            for(int i=0;i<18;i++) textRenderer.draw(textRendererMatrixStack, texts[i], locsX[i], locsY[i], 	15859709);
            super.render(matrices, mouseX, mouseY, delta);
        }
    }
}
