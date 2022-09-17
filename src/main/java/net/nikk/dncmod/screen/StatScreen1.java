package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.util.IEntityDataSaver;

public class StatScreen1 extends Screen {
    PlayerEntity player = MinecraftClient.getInstance().player;
    IEntityDataSaver playerD = (IEntityDataSaver)player;
    int backgroundWidth = 412;
    int backgroundHeight = 256;
    public StatScreen1() {
        super(Text.literal("Stat"));
    }

    @Override
    protected void init() {
        this.addDrawableChild(new ButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> {
            this.client.setScreen(new StatScreen2());}));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //texture drawing
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
        int lvlsum = 0;
        for(int z=0;z<6;z++) lvlsum += Math.max(playerD.getCharacterData().getCompound("dncmod.chart").getIntArray("classes")[z], 0);
        Text[] texts = {Text.literal("Status"),Text.literal("Information").styled(style -> style.withUnderline(true)),Text.literal("[").append(Text.literal("Name")).append(": "+playerD.getCharacterData().getCompound("dncmod.chart").getString("first_name")+" "+playerD.getCharacterData().getString("last_name")+"]"),
                Text.literal("[Health: "+(double) Math.round(player.getHealth()*10)/10+"/"+player.getMaxHealth()+"]"),Text.literal("[Level: "+lvlsum+"]"),
                Text.literal("[Hit Dice :"+playerD.getCharacterData().getCompound("dncmod.chart").getInt("hit_dice")+"]"),Text.literal("[Proficiency Modifier: "+playerD.getCharacterData().getCompound("dncmod.chart").getString("proficiency_modifier")+"]"),Text.literal("[Ki Points: "+playerD.getCharacterData().getCompound("dncmod.chart").getInt("ki")+"]"),
                Text.literal("Class List").styled(style -> style.withUnderline(true)),Text.literal("Stats").styled(style -> style.withUnderline(true)),Text.literal("Str"+"- "+playerD.getCharacterData().getCompound("dncmod.chart").getIntArray("stats")[0]),Text.literal("Dex"+"- "+playerD.getCharacterData().getCompound("dncmod.chart").getIntArray("stats")[1]),
                Text.literal("Con"+"- "+playerD.getCharacterData().getCompound("dncmod.chart").getIntArray("stats")[2]),Text.literal("Int"+"- "+playerD.getCharacterData().getCompound("dncmod.chart").getIntArray("stats")[3]),Text.literal("Wis"+"- "+playerD.getCharacterData().getCompound("dncmod.chart").getIntArray("stats")[4]),Text.literal("Cha"+"- "+playerD.getCharacterData().getCompound("dncmod.chart").getIntArray("stats")[5])};
        int[] locsX = {width/2-18,x+collum*4,x+collum*4,x+collum*4,x+collum*4,x+collum*4,x+collum*4,x+collum*4,x+collum*13+collum/2,x+collum*24,x+collum*22,x+collum*22,x+collum*22,x+collum*22,x+collum*22,x+collum*22};
        int[] locsY = {y+20+line*2/3, y+20+line*2+line,y+20+line*4+line,y+20+line*6+line,y+20+line*8+line,y+20+line*10+line,y+20+line*12+line,y+20+line*14+line,y+20+line*2+line,y+20+line*2+line,y+20+line*4+line,y+20+line*6+line,y+20+line*8+line,y+20+line*10+line,y+20+line*12+line,y+20+line*14+line,};
        for(int i=0;i<16;i++) textRenderer.draw(textRendererMatrixStack, texts[i], locsX[i], locsY[i], 	15859709);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
