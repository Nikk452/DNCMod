package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
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

public class StatScreen4 extends Screen {
    /**
     "Leatherworker",
     "Cartographer",
     "Woodworker",
     "Glassworker",
     "Calligrapher",
     "Carpenter",

     "Brewer",
     "Painter",
     "Alchemist",
     "Cobbler",
     "Tinkerer",
     "Weaver",

     "Potter",
     "Mason",
     "Jeweler",
     "Smith",
     "Miner",
     "Cook",
     */
    PlayerEntity player = MinecraftClient.getInstance().player;
    NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
    int backgroundWidth = 412;
    int backgroundHeight = 256;
    int line = backgroundHeight/30;
    int collum = backgroundWidth/30;

    public StatScreen4() {
        super(Text.literal("Stat2"));
    }

    @Override
    protected void init() {
        this.addDrawableChild(new ModButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> {
            this.client.setScreen(new StatScreen3());}));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //texture drawing
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
        renderBackground(context);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        context.drawTexture(new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"), x, y, 0, 0, backgroundWidth, backgroundHeight,412,256);
        //drawTexture(matrices, LocationX, LocationY, Z?... 1 , u is 0, v is 0,ActualImageWidth,ActualImageHeight,texturewidthScaled,textureheightScaled);
        //text drawing
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        DrawContext textRendererMatrixStack = new DrawContext(MinecraftClient.getInstance(), context.getVertexConsumers());
        textRendererMatrixStack.getMatrices().scale(1.0F, 1.0F, 1.0F);
        ArrayList<Text> texts = new ArrayList<>();
        texts.add(Text.literal("Status"));
        texts.add(Text.literal("  Tool Proficiencies  ").styled(style -> style.withUnderline(true)));
        ArrayList<Integer> locsX = new ArrayList<>();
        locsX.add(width/2-18);
        locsX.add(x+collum*12-4);
        List<Integer> locsY = new ArrayList<>();
        locsY.add(y+20+line*2/3);
        locsY.add(y+20+line*2+line);
        int[] tools = nbt.getIntArray("tools_prof");
        for(int i=0;i<tools.length;i++){
            if(tools[i]>=0){
                String[] skill_p = {"U","T","P","E","M"};
                String skill_lvl = tools[i]==4?"Max":""+skill_p[tools[i]];
                texts.add(Text.literal(tools[i]+" ["+skill_lvl+"]"));
                locsX.add((x*100/72)+collum*30);
                locsY.add((y*100/72)+line*(10+(i+1)*2)+line*(i+1)/10);
            }
        }
        for(int i=0;i<texts.stream().count();i++){
            textRendererMatrixStack.drawText(textRenderer, texts.get(i), locsX.get(i), locsY.get(i), 	15859709,false);
        }
        super.render(context, mouseX, mouseY, delta);
    }
}
