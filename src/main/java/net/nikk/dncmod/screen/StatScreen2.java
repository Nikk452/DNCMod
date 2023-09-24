package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.screen.custom.ModButtonWidget;
import net.nikk.dncmod.util.IEntityDataSaver;

import java.util.ArrayList;
import java.util.List;
public class StatScreen2 extends Screen {
    PlayerEntity player = MinecraftClient.getInstance().player;
    NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
    int backgroundWidth = 412;
    int backgroundHeight = 256;
    int line = backgroundHeight/30;
    int collum = backgroundWidth/30;

    public StatScreen2() {
        super(Text.literal("Stat2"));
    }

    @Override
    protected void init() {
        this.addDrawableChild(new ModButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> this.client.setScreen(new StatScreen3())));
        this.addDrawableChild(new ModButtonWidget(width/2-158, height/2+70, 75, 20, Text.literal("Previous Page"), (button) -> this.client.setScreen(new StatScreen1(false))));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //texture drawing
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
        renderBackground(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
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
        ArrayList<Text> texts = new ArrayList<>();
        texts.add(Text.literal("Status"));
        texts.add(Text.literal("   Class   ").styled(style -> style.withUnderline(true)));
        texts.add(Text.literal("   Trained   ").styled(style -> style.withUnderline(true)));
        texts.add(Text.literal("    Skills    ").styled(style -> style.withUnderline(true)));
        ArrayList<Integer> locsX = new ArrayList<>();
        locsX.add(width/2-18);locsX.add(x+collum*5);locsX.add(x+collum*13);locsX.add(x+collum*22);
        List<Integer> locsY = new ArrayList<>();
        locsY.add(y+20+line*2/3);locsY.add(y+20+line*2+line);locsY.add(y+20+line*2+line);locsY.add(y+20+line*2+line);
        int[] skills = nbt.getIntArray("skills");
        int[] skill_type = new int[]{0,0,0,0,1,1,1,1,1,2,2,3,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5};
        String[] ids = {"grapleskill","mineskill","jumpskill","swimskill","acrobaticsskill","stealthskill","stealingskill","lockpickskill","craftingskill","concentrationskill","enduranceskill","lore_arcaneskill","lore_divineskill","lore_primalskill","investigationskill","medicineskill","researchskill","healskill",
                "perceptionskill","appraiseskill","insightskill","meditationskill","magicaldeviceskill","persuasionskill","deceptionskill","intimidationskill","performanceskill"};
        String[] names = {"skills.dncmod.grapple","skills.dncmod.mine","skills.dncmod.jump","skills.dncmod.swim","skills.dncmod.acrobatics","skills.dncmod.stealth","skills.dncmod.stealing","skills.dncmod.lockpick","skills.dncmod.crafting","skills.dncmod.concentration","skills.dncmod.endurance","skills.dncmod.lore_arcane",
                "skills.dncmod.lore_divine","skills.dncmod.lore_primal","skills.dncmod.investigation","skills.dncmod.medicine","skills.dncmod.research","skills.dncmod.heal","skills.dncmod.perception","skills.dncmod.appraise","skills.dncmod.insight","skills.dncmod.meditation",
                "skills.dncmod.magical_device","skills.dncmod.persuasion","skills.dncmod.deception","skills.dncmod.intimidation","skills.dncmod.performance"};
        int confirmed_skills = 0;
        ArrayList<String> images = new ArrayList<>();
        for(int i=0;i<skills.length;i++){
            if(skills[i]>=0){
                String[] skill_p = {"U","T","P","E","M"};
                String skill_lvl = skills[i]==4?"Max":""+skill_p[skills[i]];
                images.add(ids[i]);
                texts.add(Text.translatable(names[i]).append(Text.literal(" ["+skill_lvl+"] ("+nbt.getIntArray("stat_mod")[skill_type[i]]+")")));
                locsX.add((x*100/72)+collum*30);
                locsY.add((y*100/72)+line*(10+confirmed_skills*2)+line*confirmed_skills/10);
                confirmed_skills+=1;
            }
        }
        for(int i = 0; i< (long) texts.size(); i++){
            textRendererMatrixStack.scale(1f,1f,1f);
            if(i==4) textRendererMatrixStack.scale(0.72f,0.72f,0.72f);
            if(i>=4){
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/"+images.get(i-4)+".png"));
                drawTexture(matrices,x+collum*21-2,y+20+line*i+line-3+((i-4)*15/4),8,8,0,0,16,16,16,16);
            }
            textRenderer.draw(textRendererMatrixStack, texts.get(i), locsX.get(i), locsY.get(i), 	15859709);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }
}
